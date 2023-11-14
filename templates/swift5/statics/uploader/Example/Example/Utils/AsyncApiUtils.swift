import ApiVideoUploader
import Foundation

/// A utils class around the ApiVideoClient to make it easier to use with async/await
public enum AsyncApiUtils {
    public static func uploadVideoWithUploadToken(uploadToken: String, url: URL, progress: ((Progress) -> Void)? = nil) async throws -> Video {
        let task = CancellableUploadWithUploadTokenVideoTask(uploadToken: uploadToken, url: url, progress: progress)
        return try await withTaskCancellationHandler {
            try await task.execute()
        } onCancel: {
            task.cancel()
        }
    }
}
