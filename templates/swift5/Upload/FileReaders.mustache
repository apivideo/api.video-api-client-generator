//  FileReader.swift
//

import Foundation


protocol ChunksReader {
    var remainingNumberOfChunks: Int { get }
    var fileSize: Int64? { get }
    func getTotalNumberOfChunks() -> Int?
    func getNextChunk() -> (index: Int, chunk: ChunkInputStream)
}

/**
 * Represents one file split in multiple chunks
 */
class FileChunksReader: ChunksReader {
    private let totalNumberOfChunks: Int
    let fileSize: Int64?
    private var chunks: [ChunkInputStream]
    private var index = 1

    public init(fileURL: URL) throws {
        let fileChunksBuilder = try FileChunksBuilder(fileURL: fileURL)
        self.fileSize = fileChunksBuilder.fileSize
        self.chunks = fileChunksBuilder.build()
        self.totalNumberOfChunks = self.chunks.count
    }

    var remainingNumberOfChunks: Int {
        return chunks.count
    }

    func getTotalNumberOfChunks() -> Int? {
        return totalNumberOfChunks
    }
    
    func getNextChunk() -> (index: Int, chunk: ChunkInputStream) {
        let tuple = (index, chunks.removeFirst())
        index += 1
        return tuple
    }
}

/**
 * Represents multiple chunked files.
 */
class FilePartsReader: ChunksReader {
    let fileSize: Int64? = nil
    private let totalNumberOfChunks: Int = 0
    private var chunks: [ChunkInputStream] = []
    private var index = 1
    private var isLastPart = false
 
    var remainingNumberOfChunks: Int {
        return 0
    }
    
    func getTotalNumberOfChunks() -> Int? {
        if (isLastPart) {
            return index - 1
        } else {
            return nil
        }
    }
    
    func getNextChunk() -> (index: Int, chunk: ChunkInputStream) {
        let tuple = (index, chunks.removeFirst())
        index += 1
        return tuple
    }
    
    func append(fileURL: URL, isLastPart: Bool) throws {
        if (self.isLastPart) {
            throw ParameterError.IO
        }
        
        self.isLastPart = isLastPart
        let fileSize = try Int64(fileURL.resourceValues(forKeys: [URLResourceKey.fileSizeKey]).fileSize!)
        chunks.append(ChunkInputStream(fileURL: fileURL, maxSize: Int(fileSize)))
    }
}
