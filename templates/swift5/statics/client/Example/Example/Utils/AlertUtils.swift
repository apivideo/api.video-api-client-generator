import Foundation
import UIKit

/// A class that displays alerts
enum AlertUtils {
    static func show(_ message: String, title: String = "Error", vc: UIViewController, action: @escaping () -> Void = {}) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "OK", style: .default) { _ in
            action()
        }

        alert.addAction(okAction)
        DispatchQueue.main.async {
            vc.present(alert, animated: true, completion: nil)
        }
    }
}
