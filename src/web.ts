import { WebPlugin } from '@capacitor/core';

import type { ActionHandlerOptions, MediaSessionPlugin, PlaybackStateOptions } from './definitions';

export class MediaSessionWeb extends WebPlugin implements MediaSessionPlugin {
    async setMetadata(options: MediaMetadataInit): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.metadata = new MediaMetadata(options);
        } else {
            throw this.unimplemented('Media Session API not available in this browser.');
        }
    }

    async setPlaybackState(options: PlaybackStateOptions): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.playbackState = options.playbackState;
        } else {
            throw this.unimplemented('Media Session API not available in this browser.');
        }
    };

    async setActionHandler(options: ActionHandlerOptions, handler: MediaSessionActionHandler | null): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setActionHandler(options.action, handler);
        } else {
            throw this.unimplemented('Media Session API not available in this browser.');
        }
    };

    async setPositionState(options: MediaPositionState): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setPositionState(options);
        } else {
            throw this.unimplemented('Media Session API not available in this browser.');
        }
    };
}
