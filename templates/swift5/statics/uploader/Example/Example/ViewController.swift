//
//  ViewController.swift
//  Example
//

import UIKit
import AVKit
import ApiVideoUploader

class ViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    let imagePickerController = UIImagePickerController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        selectVideo()
    }

    func selectVideo() {
            imagePickerController.sourceType = .photoLibrary
            imagePickerController.delegate = self
            imagePickerController.mediaTypes = ["public.movie"]
            imagePickerController.allowsEditing = false
            imagePickerController.videoQuality = .typeHigh
            
            // for IOS 11 and more
            if #available(iOS 11.0, *) {
                //desable video compressing to get the highest video quality
                imagePickerController.videoExportPreset = AVAssetExportPresetPassthrough
            }
            
            present(imagePickerController, animated: true, completion: nil)
        }
 
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        let url = info[.mediaURL] as! URL
        upload(url: url)
        imagePickerController.dismiss(animated: true, completion: nil)
    }
    
    func upload(url: URL) {
        ApiVideoUploader.basePath = Environment.sandbox.rawValue
        VideosAPI.uploadWithUploadToken(token: "MY_VIDEO_TOKEN", file: url,
            onProgressReady: { progress in
                print("Progress: \(progress)")
            }) { video, error in
            if let video = video {
                print("Noice! Upload success! \(video)")
            }
            if let error = error {
                print("Upload error: \(error)")
            }
        }
    }
}

