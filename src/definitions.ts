export interface PlaybackStateOptions {
    playbackState: MediaSessionPlaybackState;
}

export interface ActionHandlerOptions {
    action: MediaSessionAction
}

export interface MediaSessionPlugin {
    /**
     * Sets metadata of the currently playing media. Analogue to setting the [metadata property of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/metadata) when using the Media Session API directly.
     */
    setMetadata(options: MediaMetadataInit): Promise<void>;
    /**
     * Indicate whether media is playing or not. Analogue to setting the [playbackState property of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/playbackState) when using the Media Session API directly.
     */
    setPlaybackState(options: PlaybackStateOptions): Promise<void>;
    /**
     * Sets handler for media session actions (e.g. initiated via onscreen media controls or physical buttons). Analogue to calling [setActionHandler() of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setActionHandler) when using the Media Session API directly.
     */
    setActionHandler(options: ActionHandlerOptions, handler: MediaSessionActionHandler | null): Promise<void>;
    /**
     * Update current media playback position, duration and speed. Analogue to calling [setPositionState() of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setPositionState) when using the Media Session API directly.
     */
    setPositionState(options: MediaPositionState): Promise<void>;
}