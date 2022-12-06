import Foundation
import Alamofire

public class RequestTaskQueue<T>: RequestTask {
    private let operationQueue: OperationQueue
    private let operationLock = NSLock()
    private var requestBuilders: [RequestBuilder<T>] = []

    private let _downloadProgress = Progress(totalUnitCount: 0)
    private let _uploadProgress = Progress(totalUnitCount: 0)

    internal init(queueLabel: String) {
        operationQueue = OperationQueue()
        operationQueue.name = "video.api.RequestQueue.\(queueLabel)"
        operationQueue.maxConcurrentOperationCount = 1
        super.init()
    }

    override public var uploadProgress: Progress {
        var completedUnitCount: Int64 = 0
        var totalUnitCount: Int64 = 0
        requestBuilders.forEach {
            if let progress = $0.requestTask.uploadProgress {
                completedUnitCount += progress.completedUnitCount
                totalUnitCount += progress.totalUnitCount
            }
        }

        _uploadProgress.totalUnitCount = totalUnitCount
        _uploadProgress.completedUnitCount = completedUnitCount
        return _uploadProgress
    }

    override public var downloadProgress: Progress {
        var completedUnitCount: Int64 = 0
        var totalUnitCount: Int64 = 0
        requestBuilders.forEach {
            if let progress = $0.requestTask.downloadProgress {
                completedUnitCount += progress.completedUnitCount
                totalUnitCount += progress.totalUnitCount
            }
        }

        _downloadProgress.totalUnitCount = totalUnitCount
        _downloadProgress.completedUnitCount = completedUnitCount
        return _downloadProgress
    }

    internal func willExecuteRequestBuilder(requestBuilder: RequestBuilder<T>) -> Void {
    }

    internal func execute(_ requestBuilder: RequestBuilder<T>,
                          apiResponseQueue: DispatchQueue = ApiVideoClient.apiResponseQueue,
                          completion: @escaping (_ data: T?, _ error: Error?) -> Void) -> Void {
        requestBuilders.append(requestBuilder)
        return operationQueue.addOperation(RequestOperation(lock: operationLock, requestBuilder: requestBuilder, apiResponseQueue: apiResponseQueue, willExecuteRequestBuilder: willExecuteRequestBuilder, completion: completion))
    }
    

    override public func cancel() {
        requestBuilders.forEach {
            $0.requestTask.cancel()
        }
        operationQueue.cancelAllOperations()
    }
}

final class RequestOperation<T>: Operation {
    private let lock: NSLock
    private let requestBuilder: RequestBuilder<T>
    private let apiResponseQueue: DispatchQueue
    private let completion: (_ data: T?, _ error: Error?) -> Void
    private let willExecuteRequestBuilder: (_: RequestBuilder<T>) -> Void
    
    init(lock: NSLock, requestBuilder: RequestBuilder<T>, apiResponseQueue: DispatchQueue, willExecuteRequestBuilder: @escaping (_: RequestBuilder<T>) -> Void, completion: @escaping (_ data: T?, _ error: Error?) -> Void) {
        self.lock = lock
        self.requestBuilder = requestBuilder
        self.apiResponseQueue = apiResponseQueue
        self.willExecuteRequestBuilder = willExecuteRequestBuilder
        self.completion = completion
        super.init()
    }
    
    override func main() {
        self.lock.lock()
        guard !isCancelled else {
            self.lock.unlock()
            return
        }
        self.willExecuteRequestBuilder(requestBuilder)
        requestBuilder.execute(apiResponseQueue) { result in
            switch result {
            case let .success(response):
                self.completion(response.body, nil)
            case let .failure(error):
                self.completion(nil, error)
            }
            self.lock.unlock()
        }

    }
}
