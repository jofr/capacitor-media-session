import { WebPlugin } from '@capacitor/core';

import type { MetadataOptions, PlaybackStateOptions, ActionHandlerOptions, ActionHandler, PositionStateOptions, MediaSessionPlugin } from './definitions';

export class MediaSessionWeb extends WebPlugin implements MediaSessionPlugin {
    async setMetadata(options: MetadataOptions): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.metadata = new MediaMetadata(options);
        } else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    }

    async setPlaybackState(options: PlaybackStateOptions): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.playbackState = options.playbackState;
        } else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    };

    async setActionHandler(options: ActionHandlerOptions, handler: ActionHandler | null): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setActionHandler(options.action, handler);
        } else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    };

    async setPositionState(options: PositionStateOptions): Promise<void> {
        if ('mediaSession' in navigator) {
            navigator.mediaSession.setPositionState(options);
        } else {
            throw this.unavailable('Media Session API not available in this browser.');
        }
    };
}
