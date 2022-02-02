//
//  UploaderManager.swift
//  Example
//

import Foundation
import ApiVideoUploader

class UploaderManager{
    public static var token: String = ""
    public static var environment: Environment = Environment.sandbox
    
    public static func environmentToBool()-> Bool{
        var isOn = false
        if(environment == Environment.production){
            isOn = true
        }
        return isOn
    }
    
}
