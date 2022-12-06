import Foundation
import Alamofire


public class UploadChunkRequestTaskQueue: RequestTaskQueue<Video> {
    private let requestBuilders: [RequestBuilder<Video>]

    private let onProgressReady: ((Progress) -> Void)?
    private let apiResponseQueue: DispatchQueue
    private let completion: (_ data: Video?, _ error: Error?) -> Void

    private var videoId: String?
    private let _uploadProgress: Progress

    private var hasSentError = false

    internal init(requestBuilders: [RequestBuilder<Video>],
                  fileSize: Int64,
                  queueLabel: String,
                  onProgressReady: ((Progress) -> Void)? = nil,
                  apiResponseQueue: DispatchQueue = ApiVideoClient.apiResponseQueue,
                  completion: @escaping (_ data: Video?, _ error: Error?) -> Void) {
        _uploadProgress = Progress(totalUnitCount: fileSize)
        self.requestBuilders = requestBuilders
        self.onProgressReady = onProgressReady
        self.apiResponseQueue = apiResponseQueue
        self.completion = completion
        super.init(queueLabel: queueLabel)

        requestBuilders.forEach { requestBuilder in
            execute(requestBuilder,
                    apiResponseQueue: apiResponseQueue,
                    completion: completionHook)
        }
    }

    internal convenience init(videoId: String,
                              file: URL,
                              onProgressReady: ((Progress) -> Void)? = nil,
                              apiResponseQueue: DispatchQueue = ApiVideoClient.apiResponseQueue,
                              completion: @escaping (_ data: Video?, _ error: Error?) -> Void) throws {
        let chunkInputStreams = try FileChunkInputStreamsBuilder(file: file).build()
        let numOfChunks = chunkInputStreams.count

        var requestBuilders: [RequestBuilder<Video>] = []
        chunkInputStreams.enumerated().forEach { chunkId, chunkInputStream in
            let requestBuilder = VideosAPI.uploadWithRequestBuilder(videoId: videoId, file: chunkInputStream, chunkId: chunkId + 1, numOfChunks: numOfChunks, onProgressReady: nil)
            requestBuilders.append(requestBuilder)
        }
        try self.init(requestBuilders: requestBuilders, fileSize: file.fileSize, queueLabel: videoId, onProgressReady: onProgressReady, apiResponseQueue: apiResponseQueue, completion: completion)
        self.videoId = videoId
    }

    internal convenience init(token: String,
                              file: URL,
                              onProgressReady: ((Progress) -> Void)? = nil,
                              apiResponseQueue: DispatchQueue = ApiVideoClient.apiResponseQueue,
                              completion: @escaping (_ data: Video?, _ error: Error?) -> Void) throws {
        let chunkInputStreams = try FileChunkInputStreamsBuilder(file: file).build()
        let numOfChunks = chunkInputStreams.count

        var requestBuilders: [RequestBuilder<Video>] = []
        chunkInputStreams.enumerated().forEach { chunkId, chunkInputStream in
            let requestBuilder = VideosAPI.uploadWithUploadTokenWithRequestBuilder(token: token, file: chunkInputStream, chunkId: chunkId + 1, numOfChunks: numOfChunks, onProgressReady: nil)
            requestBuilders.append(requestBuilder)
        }
        try self.init(requestBuilders: requestBuilders, fileSize: file.fileSize, queueLabel: token, onProgressReady: onProgressReady, apiResponseQueue: apiResponseQueue, completion: completion)
    }

    override func willExecuteRequestBuilder(requestBuilder: RequestBuilder<Video>) -> Void {
        if let videoId = videoId {
            uploadAddVideoIdParameterWithRequestBuilder(requestBuilder: requestBuilder, videoId: videoId)
        }
        requestBuilder.onProgressReady = progressReadyHook
    }

    override public var uploadProgress: Progress {
        _uploadProgress.completedUnitCount = min(super.uploadProgress.completedUnitCount, _uploadProgress.totalUnitCount)
        return _uploadProgress
    }

    private func progressReadyHook(progress: Progress) -> Void {
        if let onProgressReady = onProgressReady {
            onProgressReady(uploadProgress)
        }
    }

    private func completionHook(_ data: Video?, _ error: Error?) -> Void {
        if let error = error {
            if !hasSentError {
                hasSentError = true
                cancel()
                completion(nil, error)
            }
        } else {
            videoId = data?.videoId
            if (requestBuilders.allSatisfy {
                $0.requestTask.state == .finished
            }) {
                completion(data, nil)
            }
        }
    }

    /**
    * Add a videoId to the request builder if it does not exist already.
    - parameter requestBuilder: the request builder
    - parameter videoId: the videoId to add to the request
    */
    private func uploadAddVideoIdParameterWithRequestBuilder(requestBuilder: RequestBuilder<Video>, videoId: String) {
        guard let parameters = requestBuilder.parameters else {
            return
        }
        if (!parameters.keys.contains("videoId")) {
            requestBuilder.parameters!["videoId"] = videoId
        }
    }
}
