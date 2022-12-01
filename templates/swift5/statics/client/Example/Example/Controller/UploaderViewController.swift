//
//  UploaderViewController.swift
//  Example
//

import UIKit
import AVKit
import ApiVideoClient
import SwiftUI

class UploaderViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UITextFieldDelegate {
    let imagePickerController = UIImagePickerController()
    
    let paramBackgroundView: UIView = {
        let paramView = UIView()
        return paramView
    }()
    let environmentTitleLabel: UILabel = {
        let title = UILabel()
        title.text = "Environment"
        title.font = UIFont.systemFont(ofSize: 22, weight: .bold)
        return title
    }()
    
    let selectedEnvironmentLabel: UILabel = {
        let title = UILabel()
        title.text = "Production"
        return title
    }()
    
    let selectEnvironmentSwitch: UISwitch = {
        let selectSwitch = UISwitch()
        return selectSwitch
    }()
    
    let uplaodButton: UIButton = {
        let btn = UIButton(type: .system)
        btn.setTitle("Upload", for: .normal)
        return btn
    }()
    
    let apiKeyLabel: UILabel = {
        let api = UILabel()
        api.text = "Your Api key :"
        return api
    }()
    
    let apiKeyTextField: UITextField = {
        let api = UITextField()
        return api
    }()
    
    let titleSelectVideoLabel: UILabel = {
        let titleLabel = UILabel()
        titleLabel.text = "Choose your video :"
        return titleLabel
    }()
    
    let selectVideoButton: UIButton = {
        let btn = UIButton(type: .system)
        btn.setTitle("Select Video", for: .normal)
        return btn
    }()
    
    let thumbnailImageView: UIImageView = {
        let thumbnail = UIImageView()
        thumbnail.backgroundColor = .gray
        return thumbnail
    }()
    
    private var videoUrl: URL?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Uploader"
        view.addSubview(paramBackgroundView)
        paramBackgroundView.backgroundColor = .darkGray
        paramBackgroundView.layer.cornerRadius = 10
        paramBackgroundView.addSubview(environmentTitleLabel)
        paramBackgroundView.addSubview(selectedEnvironmentLabel)
        paramBackgroundView.addSubview(selectEnvironmentSwitch)
        paramBackgroundView.addSubview(apiKeyLabel)
        
        paramBackgroundView.addSubview(apiKeyTextField)
        apiKeyLabel.frame = CGRect(x: 0, y: 0, width: 105, height: 30)
        view.addSubview(titleSelectVideoLabel)
        view.addSubview(thumbnailImageView)
        view.addSubview(selectVideoButton)
        view.addSubview(uplaodButton)
        
        self.selectVideoButton.addTarget(self, action: #selector(selectVideo), for: .touchUpInside)
        self.uplaodButton.addTarget(self, action: #selector(uploadVideo), for: .touchUpInside)
        self.selectEnvironmentSwitch.addTarget(self, action: #selector(toggleSwitch), for: .touchUpInside)
        
        selectEnvironmentSwitch.isOn = ClientManager.environmentToBool()
        if(!selectEnvironmentSwitch.isOn){
            selectedEnvironmentLabel.text = "Sandbox"
        }
        
        apiKeyTextField.addTarget(self, action: #selector(UploaderViewController.textFieldDidChange(_:)), for: .editingChanged)
        apiKeyTextField.delegate = self
        apiKeyTextField.text = ClientManager.apiKey
                
        constraints()
    }
    
    func constraints(){
        paramBackgroundView.translatesAutoresizingMaskIntoConstraints = false
        uplaodButton.translatesAutoresizingMaskIntoConstraints = false
        environmentTitleLabel.translatesAutoresizingMaskIntoConstraints = false
        selectedEnvironmentLabel.translatesAutoresizingMaskIntoConstraints = false
        selectEnvironmentSwitch.translatesAutoresizingMaskIntoConstraints = false
        apiKeyLabel.translatesAutoresizingMaskIntoConstraints = false
        apiKeyTextField.translatesAutoresizingMaskIntoConstraints = false
        titleSelectVideoLabel.translatesAutoresizingMaskIntoConstraints = false
        selectVideoButton.translatesAutoresizingMaskIntoConstraints = false
        thumbnailImageView.translatesAutoresizingMaskIntoConstraints = false

        paramBackgroundView.widthAnchor.constraint(equalToConstant: view.frame.width - 20).isActive = true
        paramBackgroundView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 30).isActive = true
        paramBackgroundView.heightAnchor.constraint(equalToConstant: 150).isActive = true
        paramBackgroundView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
        environmentTitleLabel.centerXAnchor.constraint(equalTo: paramBackgroundView.centerXAnchor).isActive = true
        environmentTitleLabel.topAnchor.constraint(equalTo: paramBackgroundView.topAnchor, constant: 10).isActive = true
        
        
        selectedEnvironmentLabel.topAnchor.constraint(equalTo: paramBackgroundView.topAnchor, constant: 50).isActive = true
        selectedEnvironmentLabel.leftAnchor.constraint(equalTo: paramBackgroundView.leftAnchor, constant: 20).isActive = true
        selectedEnvironmentLabel.widthAnchor.constraint(equalToConstant: (view.frame.width - 20 )/2).isActive = true
        
        selectEnvironmentSwitch.centerYAnchor.constraint(equalTo: selectedEnvironmentLabel.centerYAnchor).isActive = true
        selectEnvironmentSwitch.rightAnchor.constraint(equalTo: paramBackgroundView.rightAnchor, constant: -20).isActive = true
        
        
        apiKeyLabel.topAnchor.constraint(equalTo: paramBackgroundView.topAnchor, constant: 80).isActive = true
        apiKeyLabel.bottomAnchor.constraint(equalTo: paramBackgroundView.bottomAnchor).isActive = true
        apiKeyLabel.leftAnchor.constraint(equalTo: paramBackgroundView.leftAnchor, constant: 20).isActive = true
        
        apiKeyTextField.centerYAnchor.constraint(equalTo: apiKeyLabel.centerYAnchor).isActive = true
        apiKeyTextField.topAnchor.constraint(equalTo: paramBackgroundView.topAnchor, constant: 80).isActive = true
        apiKeyTextField.bottomAnchor.constraint(equalTo: paramBackgroundView.bottomAnchor).isActive = true
        apiKeyTextField.rightAnchor.constraint(equalTo: paramBackgroundView.rightAnchor, constant: -20).isActive = true
        apiKeyTextField.widthAnchor.constraint(equalToConstant: 250).isActive = true
        
        titleSelectVideoLabel.topAnchor.constraint(equalTo: paramBackgroundView.bottomAnchor, constant: 30).isActive = true
        titleSelectVideoLabel.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 30).isActive = true
        
        thumbnailImageView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        thumbnailImageView.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 20).isActive = true
        thumbnailImageView.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -20).isActive = true
        thumbnailImageView.heightAnchor.constraint(equalToConstant: 220).isActive = true
        thumbnailImageView.topAnchor.constraint(equalTo: titleSelectVideoLabel.bottomAnchor, constant: 20).isActive = true
        
        
        selectVideoButton.widthAnchor.constraint(equalToConstant: 110).isActive = true
        selectVideoButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        
        selectVideoButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        selectVideoButton.topAnchor.constraint(equalTo: thumbnailImageView.bottomAnchor, constant: 10).isActive = true
        
        uplaodButton.widthAnchor.constraint(equalToConstant: 70).isActive = true
        uplaodButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        uplaodButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        uplaodButton.topAnchor.constraint(equalTo: selectVideoButton.bottomAnchor, constant: 20).isActive = true

        // uplaodButton.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: 40).isActive = true
        
    }
    
    @objc func selectVideo() {
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
        videoUrl = url
        thumbnailImageView.image = getThumbnailImage(forUrl: url)
        imagePickerController.dismiss(animated: true, completion: nil)
    }
    
    func upload(url: URL) {
        VideosAPI.create(videoCreationPayload: VideoCreationPayload(title: "my video")) { video, error in
            if let video = video {
                do {
                    try VideosAPI.upload(
                            videoId: video.videoId,
                            file: url,
                            onProgressReady: { progress in
                                print("Progress: \(progress)")
                            }) { video, error in
                        if video != nil {
                            self.thumbnailImageView.image = nil
                        }
                        if let error = error {
                            print("Upload error: \(error)")
                        }
                    }
                } catch {
                    print("Upload error: \(error)")
                }
            }
            if let error = error {
                print("Create error: \(error)")
            }
        }
    }
    
    @objc func uploadVideo(){
        if(videoUrl != nil){
            upload(url: videoUrl!)
        }else{
            print("error video")
        }
    
    }
    
    @objc func toggleSwitch(){
        if(selectEnvironmentSwitch.isOn){
            ClientManager.environment = Environment.production
            selectedEnvironmentLabel.text = "Production"
            
        }else{
            ClientManager.environment = Environment.sandbox
            selectedEnvironmentLabel.text = "Sandbox"
        }
    }
    
    
    func getThumbnailImage(forUrl url: URL) -> UIImage? {
        let asset: AVAsset = AVAsset(url: url)
        let imageGenerator = AVAssetImageGenerator(asset: asset)

        do {
            let thumbnailImage = try imageGenerator.copyCGImage(at: CMTimeMake(value: 1, timescale: 60), actualTime: nil)
            return UIImage(cgImage: thumbnailImage)
        } catch let error {
            print(error)
        }

        return nil
    }
    
    @objc func textFieldDidChange(_ textField: UITextField) {
        ClientManager.apiKey = textField.text!
    }

}
