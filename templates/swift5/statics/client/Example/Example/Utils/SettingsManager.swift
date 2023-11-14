import ApiVideoClient
import Foundation

/// A static class to retrieve configuration from UserDefaults
enum SettingsManager {
    static var apiKey: String {
        UserDefaults.standard.string(forKey: "ApiKey") ?? "YOUR_API_KEY"
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
