//  VideosApiTests.swift
//

import Foundation
import XCTest
import ApiVideoClient

internal class UploadTestCase: XCTestCase {
    internal var videoId: String? = nil
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        try XCTSkipIf(Parameters.apiKey == "INTEGRATION_TESTS_API_TOKEN", "Can't get API key")
        ApiVideoClient.apiKey = Parameters.apiKey
        ApiVideoClient.basePath = Environment.sandbox.rawValue

        continueAfterFailure = false
    }

    override func tearDown() {
        super.tearDown()
        
        if let videoId = videoId {
            VideosAPI.delete(videoId: videoId) { data, error in }
        }
    }
    
    internal func createVideo() {
        let expectation = XCTestExpectation(description: "Create a video")
        
        VideosAPI.create(videoCreationPayload: VideoCreationPayload(title: "[iOS-API-client-tests] upload with chunk")) { video, error in
            XCTAssertNil(error, "Failed to create a video due to \(String(describing: error))")
            XCTAssertNotNil(video, "Failed to create a video")
            if let video = video {
                self.videoId = video.videoId
            }
            expectation.fulfill()
        }
        
        wait(for: [expectation], timeout: 5)
    }
    
    internal func uploadVideo(file: URL, timeout: TimeInterval = 30) {
        let expectation = XCTestExpectation(description: "Upload a video")
        var observedProgress: Progress? = nil

        VideosAPI.upload(videoId: self.videoId!, file: file, onProgressReady: { progress in
            observedProgress = progress
        }) { video, error in
            XCTAssertNotNil(video, "Failed to upload a video")
            XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: timeout)

        XCTAssertNotNil(observedProgress, "Failed to get progress")
        XCTAssertEqual(1.0, observedProgress?.fractionCompleted)
    }
}

class SingleUploadTests: UploadTestCase {
    func testUpload() {
        createVideo()

        uploadVideo(file: SharedResources.v558k!)
    }
}

class UploadChunkTests: UploadTestCase {
    func testUpload() throws {
        createVideo()

        try ApiVideoClient.setChunkSize(chunkSize: 1024 * 1024 * 5)
        uploadVideo(file: SharedResources.v10m!, timeout: 120)
    }
}

class ProgressiveUploadTests: UploadTestCase {
    private var progressiveUploadSession: VideosAPI.ProgressiveUploadSession? = nil
    
    internal func uploadPart(file: URL, timeout: TimeInterval = 60, isLastPart: Bool = false) {
        let expectation = XCTestExpectation(description: "Upload a video")
        var observedProgress: Progress? = nil
        
        if (isLastPart) {
            progressiveUploadSession!.uploadLastPart(file: file, onProgressReady: { progress in
                observedProgress = progress
            }) { video, error in
                XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
                XCTAssertNotNil(video, "Failed to upload a video")
                expectation.fulfill()
            }
        } else {
            progressiveUploadSession!.uploadPart(file: file, onProgressReady: { progress in
                observedProgress = progress
            }) { video, error in
                XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
                XCTAssertNotNil(video, "Failed to upload a video")
                expectation.fulfill()
            }
        }
        
        wait(for: [expectation], timeout: timeout)
        
  
        XCTAssertNotNil(observedProgress, "Failed to get progress")
        XCTAssertEqual(1.0, observedProgress?.fractionCompleted)
    }
    
    func testUpload() {
        createVideo()
        
        progressiveUploadSession = VideosAPI.buildProgressiveUploadSession(videoId: self.videoId!)
        uploadPart(file: SharedResources.v10m_parta!)
        uploadPart(file: SharedResources.v10m_partb!)
        uploadPart(file: SharedResources.v10m_partc!, isLastPart: true)
    }
}


internal class UploadWithTokenTestCase: XCTestCase {
    internal var token: String? = nil
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        try XCTSkipIf(Parameters.apiKey == "INTEGRATION_TESTS_API_TOKEN", "Can't get API key")
        ApiVideoClient.apiKey = Parameters.apiKey
        ApiVideoClient.basePath = Environment.sandbox.rawValue

        continueAfterFailure = false
    }

    override func tearDown() {
        super.tearDown()
        
        if let token = self.token {
            UploadTokensAPI.deleteToken(uploadToken: token) { data, error in }
        }
    }
    
    internal func createUploadToken() {
        let expectation = XCTestExpectation(description: "Create an upload token")
        
        UploadTokensAPI.createToken(tokenCreationPayload: TokenCreationPayload(ttl: 120)) { uploadToken, error in
            XCTAssertNil(error, "Failed to create a an upload token due to \(String(describing: error))")
            XCTAssertNotNil(uploadToken, "Failed to create an upload token")
            if let uploadToken = uploadToken {
                self.token = uploadToken.token
            }
            expectation.fulfill()
        }
        
        wait(for: [expectation], timeout: 5)
    }
    
    internal func uploadVideo(file: URL, timeout: TimeInterval = 30) {
        let expectation = XCTestExpectation(description: "Upload a video")
        var observedProgress: Progress? = nil

        VideosAPI.uploadWithUploadToken(token: self.token!, file: file, onProgressReady: { progress in
            observedProgress = progress
        }) { video, error in
            XCTAssertNotNil(video, "Failed to upload a video")
            XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: timeout)

        XCTAssertNotNil(observedProgress, "Failed to get progress")
        XCTAssertEqual(1.0, observedProgress?.fractionCompleted)
    }
}

class SingleUploadWithTokenTests: UploadWithTokenTestCase {
    func testUpload() {
        createUploadToken()

        uploadVideo(file: SharedResources.v558k!)
    }
}

class UploadWithTokenChunkTests: UploadWithTokenTestCase {
    func testUpload() throws {
        createUploadToken()

        try ApiVideoClient.setChunkSize(chunkSize: 1024 * 1024 * 5)
        uploadVideo(file: SharedResources.v10m!, timeout: 120)
    }
}

class ProgressiveUploadWithTokenTests: UploadWithTokenTestCase {
    private var progressiveUploadSession: VideosAPI.ProgressiveUploadWithUploadTokenSession? = nil
    
    internal func uploadPart(file: URL, timeout: TimeInterval = 60, isLastPart: Bool = false) {
        let expectation = XCTestExpectation(description: "Upload a video")
        var observedProgress: Progress? = nil
        
        if (isLastPart) {
            progressiveUploadSession!.uploadLastPart(file: file, onProgressReady: { progress in
                observedProgress = progress
            }) { video, error in
                XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
                XCTAssertNotNil(video, "Failed to upload a video")
                expectation.fulfill()
            }
        } else {
            progressiveUploadSession!.uploadPart(file: file, onProgressReady: { progress in
                observedProgress = progress
            }) { video, error in
                XCTAssertNil(error, "Failed to upload a video due to \(String(describing: error))")
                XCTAssertNotNil(video, "Failed to upload a video")
                expectation.fulfill()
            }
        }
        
        wait(for: [expectation], timeout: timeout)
        
  
        XCTAssertNotNil(observedProgress, "Failed to get progress")
        XCTAssertEqual(1.0, observedProgress?.fractionCompleted)
    }
    
    func testUpload() {
        createUploadToken()
        
        progressiveUploadSession = VideosAPI.buildProgressiveUploadWithUploadTokenSession(token: self.token!)
        uploadPart(file: SharedResources.v10m_parta!)
        uploadPart(file: SharedResources.v10m_partb!)
        uploadPart(file: SharedResources.v10m_partc!, isLastPart: true)
    }
}
