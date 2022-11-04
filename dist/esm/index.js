import { registerPlugin } from '@capacitor/core';
const MediaSession = registerPlugin('MediaSession', {
    web: () => import('./web').then(m => new m.MediaSessionWeb()),
    ios: () => import('./web').then(m => new m.MediaSessionWeb())
});
export * from './definitions';
export { MediaSession };
//# sourceMappingURL=index.js.map