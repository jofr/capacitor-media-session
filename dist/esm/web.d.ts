import { WebPlugin } from '@capacitor/core';
import type { MetadataOptions, PlaybackStateOptions, ActionHandlerOptions, ActionHandler, PositionStateOptions, MediaSessionPlugin } from './definitions';
export declare class MediaSessionWeb extends WebPlugin implements MediaSessionPlugin {
    setMetadata(options: MetadataOptions): Promise<void>;
    setPlaybackState(options: PlaybackStateOptions): Promise<void>;
    setActionHandler(options: ActionHandlerOptions, handler: ActionHandler | null): Promise<void>;
    setPositionState(options: PositionStateOptions): Promise<void>;
}
