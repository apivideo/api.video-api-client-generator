//
//  ClientManager.swift
//  Example
//

import Foundation
import ApiVideoClient

class ClientManager{
    public static var apiKey: String = ""
    public static var environment: Environment = Environment.sandbox
    
    public static func environmentToBool()-> Bool{
        var isOn = false
        if(environment == Environment.production){
            isOn = true
        }
        return isOn
    }
    
}
