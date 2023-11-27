//
//  ViewController.swift
//  Example
//

import UIKit
import ApiVideoClient

struct VideosOption{
    let title:String
    let videoId: String
    let thumbnail: String?
    let handler: (()->Void)
}

class VideosViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    var models = [VideosOption]()
    
    private func configure(){
        
        ApiVideoClient.setApiKey(ClientManager.apiKey)
        ApiVideoClient.basePath = ClientManager.environment.rawValue
        
        VideosAPI.list(title: nil, tags: nil, metadata: nil, description: nil, liveStreamId: nil, sortBy: nil, sortOrder: nil, currentPage: nil, pageSize: nil) { (response, error) in
            guard error == nil else {
                print(error ?? "error")
                return
            }
            
            if ((response) != nil) {
                for item in response!.data {
                    self.models.append(VideosOption(title: item.title ?? "error title", videoId: item.videoId, thumbnail: item.assets?.thumbnail){
                        
                    })
                    self.tableView.reloadData()
                }
            }
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView()
        headerView.backgroundColor = UIColor.clear
        return headerView
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return models.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let model = models[indexPath.row]

        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: VideoTableViewCell.identifier, for: indexPath) as? VideoTableViewCell else{
            return UITableViewCell()
        }
        cell.layer.cornerRadius = 8
        cell.configure(with: model)
        return cell
    }
    
    
    private let tableView: UITableView = {
        let table = UITableView(frame: .zero, style: .grouped)
        table.register(VideoTableViewCell.self, forCellReuseIdentifier: VideoTableViewCell.identifier)
        return table
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configure()
        title = "Videos"
        view.addSubview(tableView)
        tableView.delegate = self
        tableView.dataSource = self
        tableView.frame = view.bounds
        tableView.rowHeight = 310.0
    }
    
    
    
    
}

