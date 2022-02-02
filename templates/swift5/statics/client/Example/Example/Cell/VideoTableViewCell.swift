//
//  VideoTableViewCell.swift
//  Example
//

import UIKit

class VideoTableViewCell: UITableViewCell {

    static let identifier = "VideoTableViewCell"
    
    private let title: UILabel = {
        let label = UILabel()
        label.numberOfLines = 1
        return label
    }()
    private let videoId: UILabel = {
        let label = UILabel()
        label.numberOfLines = 1
        return label
    }()
    
    private let myImageView: UIImageView = {
        let imageView = UIImageView()
        return imageView
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        contentView.addSubview(title)
        contentView.addSubview(videoId)
        contentView.addSubview(myImageView)
        myImageView.contentMode = .scaleAspectFit
        myImageView.backgroundColor = .black
        contentView.clipsToBounds = true
        accessoryType = .none
    }
    
    required init?(coder: NSCoder){
        fatalError()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        myImageView.frame = CGRect(
            x: 10,
            y: 5,
            width: contentView.frame.size.width - 20,
            height: contentView.frame.size.height * 0.7
        )
        
        title.frame = CGRect(
            x: 20,
            y: (myImageView.frame.height * 0.6) + 55,
            width: contentView.frame.size.width,
            height: contentView.frame.size.height * 0.4
        )
        videoId.frame = CGRect(
            x: 20,
            y: (myImageView.frame.height * 0.6) + 80,
            width: contentView.frame.size.width,
            height: contentView.frame.size.height * 0.4
        )
        videoId.textColor = .lightGray
        videoId.font = videoId.font.withSize(10)
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        title.text = nil
        videoId.text = nil
    }
    
    public func configure(with model: VideosOption){
        title.text = model.title
        videoId.text = model.videoId
        
        let myUrl = URL(string: model.thumbnail!)
        let myData = try? Data(contentsOf: myUrl!)
        myImageView.image = UIImage(data: myData!)
        
    }

}
