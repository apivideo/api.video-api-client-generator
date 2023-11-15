import ApiVideoClient
import Foundation

/// A utils class around the ApiVideoClient to make it easier to use with async/await
public enum AsyncApiUtils {
    public static func createVideo(title: String = "my video") async throws -> Video {
        let task = CancellableCreateVideoTask(title: title)
        return try await withTaskCancellationHandler {
            try await task.execute()
        } onCancel: {
            task.cancel()
        }
    }

    public static func uploadVideo(videoId: String, url: URL, progress: ((Progress) -> Void)? = nil) async throws -> Video {
        let task = CancellableUploadVideoTask(videoId: videoId, url: url, progress: progress)
        return try await withTaskCancellationHandler {
            try await task.execute()
        } onCancel: {
            task.cancel()
        }
    }

    public static func getVideos() async throws -> [Video] {
        let task = CancellableGetVideosTask()
        return try await withTaskCancellationHandler {
            try await task.execute()
        } onCancel: {
            task.cancel()
        }
    }
}
