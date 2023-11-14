import ApiVideoClient
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
class CancellableUploadVideoTask: CancellableTask<Video> {
    private let videoId: String
    private let url: URL
    private let progress: ((Progress) -> Void)?

    init(videoId: String, url: URL, progress: ((Progress) -> Void)? = nil) {
        self.videoId = videoId
        self.url = url
        self.progress = progress
    }

    override func execute() async throws -> Video {
        try await withCheckedThrowingContinuation { continuation in
            do {
                task = try VideosAPI.upload(
                    videoId: videoId,
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

/// An async/await wrapper around ``VideosAPI.create`` to make it cancellable
class CancellableCreateVideoTask: CancellableTask<Video> {
    private let title: String

    init(title: String) {
        self.title = title
    }

    override func execute() async throws -> Video {
        try await withCheckedThrowingContinuation { continuation in
            task = VideosAPI.create(videoCreationPayload: VideoCreationPayload(title: title)) { video, error in
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
        }
    }
}

/// An async/await wrapper around ``VideosAPI.list`` to make it cancellable
class CancellableGetVideosTask: CancellableTask<[Video]> {
    override func execute() async throws -> [Video] {
        try await withCheckedThrowingContinuation { continuation in
            task = VideosAPI.list(title: nil, tags: nil, metadata: nil, description: nil, liveStreamId: nil, sortBy: nil, sortOrder: nil, currentPage: nil, pageSize: nil) { response, error in
                if let error = error {
                    continuation.resume(throwing: error)
                    return
                }
                guard let response = response else {
                    continuation.resume(throwing: ClientAppError.invalidResponse)
                    return
                }

                continuation.resume(returning: response.data)
            }
        }
    }
}
