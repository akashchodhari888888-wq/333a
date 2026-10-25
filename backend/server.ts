import express, { Request, Response } from 'express';
import cors from 'cors';
import rateLimit from 'express-rate-limit';
import { fetchMediaData } from './adapter';

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100, // Limit each IP to 100 requests per windowMs
  message: { error: 'Too many requests from this IP, please try again after 15 minutes.' }
});

app.use('/api/', limiter);

app.post('/api/fetch', async (req: Request, res: Response) => {
  const { url } = req.body;

  if (!url || typeof url !== 'string') {
    return res.status(400).json({ error: 'Invalid Instagram URL provided.' });
  }

  try {
    const data = await fetchMediaData(url);
    if (!data) {
      return res.status(404).json({ error: 'Could not find media or account is private.' });
    }
    res.json(data);
  } catch (error) {
    console.error('Fetch error:', error);
    res.status(500).json({ error: 'Internal server error while fetching media.' });
  }
});

app.get('/api/proxy-download', async (req: Request, res: Response) => {
  const { url, filename } = req.query;

  if (!url || typeof url !== 'string') {
    return res.status(400).json({ error: 'Download URL is required.' });
  }

  try {
    const response = await fetch(url);
    if (!response.ok) throw new Error('Failed to fetch file from source.');

    const contentType = response.headers.get('content-type') || 'application/octet-stream';
    const contentDisposition = `attachment; filename="${filename || 'download'}"`;

    res.setHeader('Content-Type', contentType);
    res.setHeader('Content-Disposition', contentDisposition);

    const body = response.body;
    if (body) {
      // @ts-ignore
      body.pipeTo(new WritableStream({
        write(chunk) {
          res.write(chunk);
        },
        close() {
          res.end();
        }
      }));
    } else {
      res.status(500).send('Empty response from source.');
    }
  } catch (error) {
    console.error('Proxy error:', error);
    res.status(500).json({ error: 'Failed to proxy download.' });
  }
});

app.listen(port, () => {
  console.log(`SaveGram API server running on port ${port}`);
});
