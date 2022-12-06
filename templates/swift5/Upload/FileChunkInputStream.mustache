//  FileChunkInputStream.swift
//

import Foundation

public class FileChunkInputStream: InputStream {
    private let inputStream: InputStream
    private let offset: Int64
    let capacity: Int
    private var remainingBytes: Int = 0
    let file: URL

    public init(file: URL, offset: Int64 = 0, capacity: Int) {
        self.inputStream = InputStream(url: file)!
        self.offset = offset
        self.capacity = capacity
        self.remainingBytes = capacity;
        self.file = file

        super.init()
    }

    override public func open() {
        inputStream.open()
        inputStream.setProperty(offset, forKey: Stream.PropertyKey.fileCurrentOffsetKey)
    }

    override public func close() {
        inputStream.close()
    }

    override public func read(_ buffer: UnsafeMutablePointer<UInt8>, maxLength len: Int) -> Int {
        let bytesRead = inputStream.read(buffer, maxLength: min(len, remainingBytes))
        remainingBytes -= bytesRead
        return bytesRead
    }

    override public var hasBytesAvailable: Bool {
        if (remainingBytes <= 0) {
            return false
        }

        return inputStream.hasBytesAvailable
    }

    override public var streamError: Error? {
        return inputStream.streamError
    }
}

class FileChunkInputStreamsBuilder {
    private let file: URL
    private let fileSize: Int64

    public init(file: URL) throws {
        self.file = file
        fileSize = try file.fileSize
    }

    public func build() -> [FileChunkInputStream] {
        let chunkSize = ApiVideoClient.getChunkSize()
        var chunkInputStreams: [FileChunkInputStream] = []
        for offset in stride(from: 0, through: fileSize, by: chunkSize) {
            chunkInputStreams.append(FileChunkInputStream(file: file, offset: offset, capacity: min(chunkSize, Int(fileSize - offset))))
        }

        return chunkInputStreams
    }
}