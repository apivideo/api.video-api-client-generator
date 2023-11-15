import ApiVideoUploader
import Foundation

/// A static class to retrieve configuration from UserDefaults
enum SettingsManager {
    static var uploadToken: String {
        UserDefaults.standard.string(forKey: "UploadToken") ?? "YOUR_UPLOAD_TOKEN"
    }

    static var environment: Environment {
        let key = UserDefaults.standard.integer(forKey: "Environment")
        if key == 1 {
            return Environment.production
        } else {
            return Environment.sandbox
        }
    }
}
