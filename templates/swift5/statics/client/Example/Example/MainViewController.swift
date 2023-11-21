//
//  MainViewController.swift
//  Example
//

import ApiVideoClient
import AVKit
import InAppSettingsKit
import SwiftUI
import UIKit

class MainViewController: UIViewController {
    let imagePickerController = UIImagePickerController()

    /// Handle multiple upload tasks to cancel them easily
    /// It also runs them sequentially. We upload the file sequentially to simplify the upload progress bar.
    let taskManager = TaskManager()

    /// Manage configuration
    let iaskViewController: IASKAppSettingsViewController = {
        let iaskViewController = IASKAppSettingsViewController()
        iaskViewController.showCreditsFooter = false
        iaskViewController.view.tintColor = UIColor.orange
        return iaskViewController
    }()

    let videoPickerButton: UIButton = {
        let btn = UIButton(type: .system)
        btn.setTitle("Pick a video", for: .normal)
        btn.tintColor = UIColor.orange
        return btn
    }()

    let progressView: UIProgressView = {
        let view = UIProgressView()
        view.progressTintColor = UIColor.orange
        view.isHidden = true
        return view
    }()

    let cancelButton: UIButton = {
        let btn = UIButton(type: .system)
        btn.setTitle("Cancel all uploads", for: .normal)
        btn.tintColor = UIColor.orange
        btn.isHidden = true
        return btn
    }()

    override func viewDidLoad() {
        super.viewDidLoad()

        title = "Uploader"
        // Increasing timeout to 120s
        ApiVideoClient.timeout = 120

        addChild(iaskViewController)
        view.addSubview(iaskViewController.view)

        view.addSubview(videoPickerButton)
        view.addSubview(progressView)
        view.addSubview(cancelButton)

        videoPickerButton.addTarget(self, action: #selector(pickVideo), for: .touchUpInside)
        cancelButton.addTarget(self, action: #selector(cleanUploadQueue), for: .touchUpInside)

        constraints()
    }

    func constraints() {
        iaskViewController.view.translatesAutoresizingMaskIntoConstraints = false
        videoPickerButton.translatesAutoresizingMaskIntoConstraints = false
        progressView.translatesAutoresizingMaskIntoConstraints = false
        cancelButton.translatesAutoresizingMaskIntoConstraints = false

        iaskViewController.view.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        iaskViewController.view.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        iaskViewController.view.heightAnchor.constraint(equalToConstant: 300).isActive = true
        iaskViewController.view.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        iaskViewController.view.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 10).isActive = true

        videoPickerButton.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        videoPickerButton.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        videoPickerButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        videoPickerButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        videoPickerButton.topAnchor.constraint(equalTo: iaskViewController.view.bottomAnchor, constant: 20).isActive = true

        progressView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        progressView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        progressView.heightAnchor.constraint(equalToConstant: 10).isActive = true
        progressView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        progressView.topAnchor.constraint(equalTo: videoPickerButton.bottomAnchor, constant: 10).isActive = true

        cancelButton.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        cancelButton.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        cancelButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        cancelButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        cancelButton.topAnchor.constraint(equalTo: progressView.bottomAnchor, constant: 10).isActive = true
    }

    @objc func cleanUploadQueue() {
        taskManager.cancelAll()
    }

    @objc func pickVideo() {
        imagePickerController.sourceType = .photoLibrary
        imagePickerController.delegate = self
        imagePickerController.mediaTypes = ["public.movie"]
        imagePickerController.allowsEditing = false
        imagePickerController.videoQuality = .typeHigh

        // for IOS 11 and more
        if #available(iOS 11.0, *) {
            // disable video compressing to get the highest video quality
            imagePickerController.videoExportPreset = AVAssetExportPresetPassthrough
        }

        present(imagePickerController, animated: true, completion: nil)
    }

    /// Adds the upload task to the ``TaskManager``
    func addToUploadQueue(videoUrl: URL) {
        taskManager.add {
            do {
                return try await self.upload(fileUrl: videoUrl, progress: { progress in
                    print("Progress: \(progress.fractionCompleted)")
                    DispatchQueue.main.async {
                        self.progressView.progress = Float(progress.fractionCompleted)
                    }
                })
            } catch {
                print("Upload error: \(error)")
                AlertUtils.show(error.localizedDescription, title: "Error", vc: self)
                throw error
            }
        }
    }

    /// Creates and uploads a video with async/await
    private func upload(fileUrl: URL, progress: ((Progress) -> Void)? = nil) async throws {
        let video = try await AsyncApiUtils.createVideo()
        let uploadedVideo = try await AsyncApiUtils.uploadVideo(videoId: video.videoId, url: fileUrl, progress: progress)

        print("Video uploaded: \(uploadedVideo)")
        AlertUtils.show("File has been successfully uploaded at video ID: \(video.videoId)", title: "Success", vc: self)
    }
}

extension MainViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    func imagePickerController(_: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
        guard let url = info[.mediaURL] as? URL else {
            AlertUtils.show("No video selected", vc: self)
            return
        }

        imagePickerController.dismiss(animated: true, completion: nil)
        cancelButton.isHidden = false
        progressView.isHidden = false

        // Set client configuration
        ApiVideoClient.apiKey = SettingsManager.apiKey
        ApiVideoClient.basePath = SettingsManager.environment.rawValue

        // Upload
        addToUploadQueue(videoUrl: url)
    }
}
