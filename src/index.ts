import { registerPlugin } from '@capacitor/core';

import type { MediaSessionPlugin } from './definitions';

const MediaSession = registerPlugin<MediaSessionPlugin>('MediaSession', {
  web: () => import('./web').then(m => new m.MediaSessionWeb()),
  ios: () => import('./web').then(m => new m.MediaSessionWeb())
});

export * from './definitions';
export { MediaSession };
