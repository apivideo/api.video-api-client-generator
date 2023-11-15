import ApiVideoUploader
import Foundation

/// A wrapper around ``RequestTask`` to make it cancellable
class CancellableTask<T> {
    internal var task: RequestTask?

    /// Execute the task
    func execute() async throws -> T {
        fatalError("Not implemented")
    }

    /// Cancel the task
    func cancel() {
        task?.cancel()
    }
}

/// An async/await wrapper around ``VideosAPI.upload`` to make it cancellable
class CancellableUploadWithUploadTokenVideoTask: CancellableTask<Video> {
    private let uploadToken: String
    private let url: URL
    private let progress: ((Progress) -> Void)?

    init(uploadToken: String, url: URL, progress: ((Progress) -> Void)? = nil) {
        self.uploadToken = uploadToken
        self.url = url
        self.progress = progress
    }

    override func execute() async throws -> Video {
        try await withCheckedThrowingContinuation { continuation in
            do {
                task = try VideosAPI.uploadWithUploadToken(
                    token: uploadToken,
                    file: url,
                    onProgressReady: progress
                ) { video, error in
                    if let error = error {
                        continuation.resume(throwing: error)
                        return
                    }
                    guard let video = video else {
                        continuation.resume(throwing: ClientAppError.invalidResponse)
                        return
                    }
                    continuation.resume(returning: video)
                }
            } catch {
                continuation.resume(throwing: error)
            }
        }
    }
}
