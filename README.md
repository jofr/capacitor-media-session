# capacitor-media-session

Capacitor plugin for Media Sessions on Web, Android and iOS. Just like the [Media Session Web API](https://w3c.github.io/mediasession/) this enables
- customizable media playback notifications (including controls) on iOS and Android (and some browsers)
- media control using hardware media keys (e.g. on headsets, remote controls, etc.)
- setting media metadata that can be used by the platform UI

This plugin is necessary for Capacitor apps because the Android WebView does not support the Media Session Web API (if you don't need Android support you could just use the Web API directly on Web and iOS). On Web and iOS this plugin is actually just a very thin wrapper around the Web API and uses it directly, only Android needs a native implementation.

Another problem with audio playback (using web standards, e.g. an `<audio>` element) in Capacitor apps on Android is that it does not work reliably in the background. If your app is in the background Android will force your app to go to sleep even if audio is currently playing in the WebView. This plugin also tries to solve this problem by starting a [foreground service](https://developer.android.com/guide/components/foreground-services) for active Media Sessions enabling background playback.

## Install

```bash
npm install @jofr/capacitor-media-session
npx cap sync
```

## API

The API of this plugin is modeled after the [already widely supported](https://developer.mozilla.org/en-US/docs/Web/API/Media_Session_API#browser_compatibility) Media Session Web API. That way most available documentation for this web standard should be easily adaptable to this Capacitor plugin and it should be easy to use if you are already familiar with it. If your are not yet familiar with the concepts you can [read more about that on MDN](https://developer.mozilla.org/en-US/docs/Web/API/Media_Session_API) or on [the Chrome Developers Blog](https://developer.chrome.com/blog/media-session/).

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
