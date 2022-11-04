'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const MediaSession = core.registerPlugin('MediaSession', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.MediaSessionWeb()),
    ios: () => Promise.resolve().then(function () { return web; }).then(m => new m.MediaSessionWeb())
});

class MediaSessionWeb extends core.WebPlugin {
    async setMetadata(options) {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.metadata = new MediaMetadata(options);
        }
        else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    }
    async setPlaybackState(options) {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.playbackState = options.playbackState;
        }
        else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    }
    ;
    async setActionHandler(options, handler) {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setActionHandler(options.action, handler);
        }
        else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    }
    ;
    async setPositionState(options) {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setPositionState(options);
        }
        else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    }
    ;
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    MediaSessionWeb: MediaSessionWeb
});

exports.MediaSession = MediaSession;
//# sourceMappingURL=plugin.cjs.js.map
