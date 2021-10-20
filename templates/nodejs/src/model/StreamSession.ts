export default interface StreamSession<T> {
  uploadPart(file: string): Promise<T>;
  uploadLastPart(file: string): Promise<T>;
}