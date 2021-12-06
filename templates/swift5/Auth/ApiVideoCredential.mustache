//  ApiVideoCredential.swift
//

import Foundation
import Alamofire

struct ApiVideoCredential: AuthenticationCredential {
    let accessToken: String
    let refreshToken: String
    let tokenType: String
    
    let expiration: Date

    // Require refresh if within 5 minutes of expiration
    var requiresRefresh: Bool { Date(timeIntervalSinceNow: 60 * 5) > expiration }
    
    
    public init(accessToken: AccessToken) {
        self.accessToken = accessToken.accessToken!
        self.refreshToken = accessToken.refreshToken!
        self.tokenType = accessToken.tokenType!
        self.expiration = Date(timeIntervalSinceNow: Double(accessToken.expiresIn!))
    }
    
    public init() {
        self.accessToken = ""
        self.refreshToken = ""
        self.tokenType = ""
        self.expiration = Date()
    }
}
