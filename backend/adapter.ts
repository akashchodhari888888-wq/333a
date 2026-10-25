import fetch from 'node-fetch';

export interface DownloadItem {
  type: 'video' | 'photo';
  previewUrl: String;
  downloadUrl: String;
  qualityLabel?: String;
  sizeLabel?: String;
}

export interface MediaData {
  type: 'video' | 'photo' | 'reel' | 'story' | 'igtv' | 'carousel' | 'profile';
  thumbnail: String;
  caption: String | null;
  owner: String | null;
  items: DownloadItem[];
}

export async function fetchMediaData(url: string): Promise<MediaData | null> {
  // 1. Try Direct Parsing (Implementation placeholder for complexity)
  try {
    const directData = await directParsingAdapter(url);
    if (directData) return directData;
  } catch (e) {
    console.warn('Direct parsing failed, falling back to third-party API.');
  }

  // 2. Fallback to Third-Party API (Recommended for production reliability)
  const apiUrl = process.env.API_URL;
  const apiKey = process.env.API_KEY;

  if (apiUrl && apiKey) {
    return await thirdPartyApiAdapter(url, apiUrl, apiKey);
  }

  return null;
}

async function directParsingAdapter(url: string): Promise<MediaData | null> {
  // This would contain complex regex/scraping logic.
  // Returning null here to trigger fallback in this template.
  return null;
}

async function thirdPartyApiAdapter(url: string, apiUrl: string, apiKey: string): Promise<MediaData | null> {
  try {
    const response = await fetch(`${apiUrl}?url=${encodeURIComponent(url)}`, {
      headers: { 'X-API-Key': apiKey }
    });
    if (!response.ok) return null;
    return await response.json() as MediaData;
  } catch (error) {
    console.error('Third-party API error:', error);
    return null;
  }
}
