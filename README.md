# SaveGram - Instagram Downloader

A professional, high-performance Instagram downloader for videos, photos, reels, and stories.

## Features
- **General Downloader**: Support for Video, Photo, Reels, IGTV, and Carousels.
- **Story Saver**: Download stories anonymously from public accounts.
- **Profile Viewer**: View profiles and stories without logging in.
- **History**: Local download history (stored on-device).
- **Multi-language**: Support for English, Hindi, Spanish, Arabic, Portuguese, and Indonesian.
- **Dark Mode**: Smooth theme switching.
- **Responsive**: Works on phones, tablets, and desktop.

## Project Structure
- `/app`: Native Android source code (Kotlin + Jetpack Compose).
- `/backend`: Node.js Express API source code.
- `/public/tutorials`: Place your tutorial screenshots and screen recordings here.

## Local Development (Android)
1. Open the project in Android Studio or use the AI Studio environment.
2. Set `API_BASE_URL` in your `.env` file via the Secrets panel.
3. Run `compile_applet` to build the APK.

## Local Development (Backend)
1. `cd backend`
2. `npm install`
3. `npm run dev`

## Deployment
1. Deploy the Node.js backend to a provider like Heroku, Vercel, or Render.
2. Update the `API_BASE_URL` in the Android project.
3. Build the production APK for distribution.

## Tutorial Media
Place these files in `/app/src/main/res/drawable/` or equivalent for web:
- `img_hero_banner`: Main banner image.
- `tutorials/home-step1.png`: Step 1 image for home tutorial.
- ... (and so on)

## Privacy & Legal
SaveGram does not store user data. All downloads are for personal use only. We are not affiliated with Instagram.
