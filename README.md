# capacitor-media-session

Capacitor plugin to provide Media Sessions for Web and Android (iOS should work using the web implementation) as well as background audio playback using web standards (meaning e.g. an `<audio>` element) on Android.
Just like the [Media Session Web API](https://w3c.github.io/mediasession/) this enables customized media notifications and media control using platform media keys (e.g. hardware keys on headsets or software keys in the notification area).

This plugin does not handle audio or media playback in any way, you can do this using a plain `<audio>` element or the Web Audio API.
Just like the Media Session Web API this plugin only handles the display of media notifications, providing media metadata to the system and the ability to react to platform media controls through action handlers ([you can read more about the general concept of the Media Session API on MDN](https://developer.mozilla.org/en-US/docs/Web/API/Media_Session_API)).

This plugin is necessary for Capacitor apps because the Android WebView does not support the Media Session Web API.
An additional disadvantage of this behaviour an Android is that audio playback in the background is not (reliably) possible in a Capacitor app.
Android might and will force your app to go to sleep even if audio is currently playing in your WebView.
This plugin also tries to solve this problem by starting a [foreground service](https://developer.android.com/guide/components/foreground-services) for active Media Sessions enabling background playback.

The API of this plugin tries to stay as close as possible to the [already widely supported](https://developer.mozilla.org/en-US/docs/Web/API/Media_Session_API#browser_compatibility) Media Session Web API.
So most available documentation for this web standard should be easily adaptable to this Capacitor plugin.

## Install

```bash
npm install @jofr/capacitor-media-session
npx cap sync
```

## API

<docgen-index>

* [`setMetadata(...)`](#setmetadata)
* [`setPlaybackState(...)`](#setplaybackstate)
* [`setActionHandler(...)`](#setactionhandler)
* [`setPositionState(...)`](#setpositionstate)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setMetadata(...)

```typescript
setMetadata(options: MetadataOptions) => Promise<void>
```

Sets metadata of the currently playing media. Analogue to setting the
[metadata property of the MediaSession
interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/metadata)
when using the Media Session API directly.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#metadataoptions">MetadataOptions</a></code> |

--------------------


### setPlaybackState(...)

```typescript
setPlaybackState(options: PlaybackStateOptions) => Promise<void>
```

Indicate whether media is playing or not. Analogue to setting the
[playbackState property of the MediaSession
interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/playbackState)
when using the Media Session API directly.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#playbackstateoptions">PlaybackStateOptions</a></code> |

--------------------


### setActionHandler(...)

```typescript
setActionHandler(options: ActionHandlerOptions, handler: ActionHandler | null) => Promise<void>
```

Sets handler for media session actions (e.g. initiated via onscreen media
controls or physical buttons). Analogue to calling [setActionHandler() of
the MediaSession
interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setActionHandler)
when using the Media Session API directly.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#actionhandleroptions">ActionHandlerOptions</a></code> |
| **`handler`** | <code><a href="#actionhandler">ActionHandler</a> \| null</code>       |

--------------------


### setPositionState(...)

```typescript
setPositionState(options: PositionStateOptions) => Promise<void>
```

Update current media playback position, duration and speed. Analogue to
calling [setPositionState() of the MediaSession
interface](https://developer.mozilla.org/en-US/docs/Web/API/MediaSession/setPositionState)
when using the Media Session API directly.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#positionstateoptions">PositionStateOptions</a></code> |

--------------------


### Interfaces


#### MetadataOptions

| Prop          | Type                |
| ------------- | ------------------- |
| **`album`**   | <code>string</code> |
| **`artist`**  | <code>string</code> |
| **`artwork`** | <code>any[]</code>  |
| **`title`**   | <code>string</code> |


#### PlaybackStateOptions

| Prop                | Type                                   |
| ------------------- | -------------------------------------- |
| **`playbackState`** | <code>MediaSessionPlaybackState</code> |


#### ActionHandlerOptions

| Prop         | Type                            |
| ------------ | ------------------------------- |
| **`action`** | <code>MediaSessionAction</code> |


#### ActionDetails

| Prop           | Type                            |
| -------------- | ------------------------------- |
| **`action`**   | <code>MediaSessionAction</code> |
| **`seekTime`** | <code>number \| null</code>     |


#### PositionStateOptions

| Prop               | Type                |
| ------------------ | ------------------- |
| **`duration`**     | <code>number</code> |
| **`playbackRate`** | <code>number</code> |
| **`position`**     | <code>number</code> |


### Type Aliases


#### ActionHandler

<code>(details: <a href="#actiondetails">ActionDetails</a>): void</code>

</docgen-api>
