//  ChunkInputStream.swift
// 

import Foundation

class ChunkInputStream: InputStream {
    private let inputStream: InputStream
    private let offset: UInt64
    let maxSize: Int
    private var remainingBytes: Int = 0
    let fileURL: URL
    
    public init(fileURL: URL, offset: UInt64 = 0, maxSize: Int) {
        self.inputStream = InputStream(url: fileURL)!
        self.offset = offset
        self.maxSize = maxSize
        self.remainingBytes = maxSize;
        self.fileURL = fileURL
        
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
        
    override var hasBytesAvailable: Bool {
        if (remainingBytes <= 0) {
            return false
        }
        
        return inputStream.hasBytesAvailable
    }
    
    override var streamError: Error? {
        return inputStream.streamError
    }
}
