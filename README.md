# capacitor-media-session

Unified media session for Web and Android

## Install

```bash
npm install capacitor-media-session
npx cap sync
```

## API

<docgen-index>

* [`setMetadata(...)`](#setmetadata)
* [`setPlaybackState(...)`](#setplaybackstate)
* [`setActionHandler(...)`](#setactionhandler)
* [`setPositionState(...)`](#setpositionstate)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setMetadata(...)

```typescript
setMetadata(options: any) => Promise<void>
```

Sets metadata of the currently playing media. Analogue to setting the [metadata property of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/metadata) when using the Media Session API directly.

| Param         | Type             |
| ------------- | ---------------- |
| **`options`** | <code>any</code> |

--------------------


### setPlaybackState(...)

```typescript
setPlaybackState(options: PlaybackStateOptions) => Promise<void>
```

Indicate whether media is playing or not. Analogue to setting the [playbackState property of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/playbackState) when using the Media Session API directly.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#playbackstateoptions">PlaybackStateOptions</a></code> |

--------------------


### setActionHandler(...)

```typescript
setActionHandler(options: ActionHandlerOptions, handler: MediaSessionActionHandler | null) => Promise<void>
```

Sets handler for media session actions (e.g. initiated via onscreen media controls or physical buttons). Analogue to calling [setActionHandler() of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setActionHandler) when using the Media Session API directly.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#actionhandleroptions">ActionHandlerOptions</a></code> |
| **`handler`** | <code>any</code>                                                      |

--------------------


### setPositionState(...)

```typescript
setPositionState(options: any) => Promise<void>
```

Update current media playback position, duration and speed. Analogue to calling [setPositionState() of the MediaSession interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setPositionState) when using the Media Session API directly.

| Param         | Type             |
| ------------- | ---------------- |
| **`options`** | <code>any</code> |

--------------------


### Interfaces


#### PlaybackStateOptions

| Prop                | Type                                   |
| ------------------- | -------------------------------------- |
| **`playbackState`** | <code>MediaSessionPlaybackState</code> |


#### ActionHandlerOptions

| Prop         | Type                            |
| ------------ | ------------------------------- |
| **`action`** | <code>MediaSessionAction</code> |

</docgen-api>
