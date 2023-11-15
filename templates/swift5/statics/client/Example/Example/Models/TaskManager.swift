import Foundation

/// A class that manages an array of tasks to easily cancel them all
/// It also ensures that a task is executed only after the previous one has finished.
class TaskManager {
    private var tasks: [Task<Any, Error>] = []
    private var previousTask: Task<Any, Error>? {
        return tasks.last
    }

    /// Adds a task
    /// - Parameter block: The block to execute
    func add(block: @escaping () async throws -> Any) {
        /**
         * Cheap implementation to execute task sequentially
         * It won't work as expected if it is called to often on different threads.
         * Using `AsyncChannel` would be a better solution.
         **/
        let oldTask = previousTask
        let task = Task {
            if let oldTask = oldTask {
                _ = try await oldTask.value
            }
            return try await block()
        }
        tasks.append(task)
    }

    /// Cancels all tasks and clear them
    func cancelAll() {
        tasks.forEach { task in
            task.cancel()
        }
        tasks.removeAll()
    }
}
