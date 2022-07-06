export interface MediaSessionPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
