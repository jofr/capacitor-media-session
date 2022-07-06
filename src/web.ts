import { WebPlugin } from '@capacitor/core';

import type { MediaSessionPlugin } from './definitions';

export class MediaSessionWeb extends WebPlugin implements MediaSessionPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
