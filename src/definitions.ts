export interface PlaybackStateOptions {
    playbackState: MediaSessionPlaybackState;
}

export interface ActionHandlerOptions {
    action: MediaSessionAction
}

export interface MediaSessionPlugin {
    setMetadata(options: MediaMetadataInit): Promise<void>;
    setPlaybackState(options: PlaybackStateOptions): Promise<void>;
    setActionHandler(options: ActionHandlerOptions, handler: MediaSessionActionHandler | null): Promise<void>;
    setPositionState(options: MediaPositionState): Promise<void>;
}