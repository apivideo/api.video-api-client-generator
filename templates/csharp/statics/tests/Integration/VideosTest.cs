using System;
using System.Collections.Generic;
using System.IO;
using FluentAssertions;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ApiVideo.Client;
using ApiVideo.Model;
using ApiVideo.Upload;

namespace VideoApiTests.Integration
{
    public class UploadProgressListener : IUploadProgressListener
    {
        public long totalUploaded = 0;
        public long totalBytes = 0;
        public int chunkCount = 0;
        public List<int> seenChunkNums = new List<int>();

        public void onProgress(long bytesWritten, long totalBytes, int chunkCount, int chunkNum)
        {
            this.totalUploaded = bytesWritten;
            this.totalBytes = totalBytes;
            this.chunkCount = chunkCount;
            this.seenChunkNums.Add(chunkNum);

        }
    }
    [TestClass]
    public class VideosTest
    {
        ApiVideoClient apiClient;
        Video testVideo;

        [TestInitialize]
        public void init()
        {
            this.apiClient = new ApiVideoClient(System.Environment.GetEnvironmentVariable("API_KEY"), ApiVideo.Client.Environment.SANDBOX);
            this.testVideo = apiClient.Videos()
                    .create(new VideoCreationPayload() { title = "[Java-SDK-tests] sdk tests", _public = false});
            Console.WriteLine("Video "+this.testVideo.videoid+" created");
        }
        [TestCleanup]
        public void cleanup()
        {
            this.apiClient.Videos().delete(this.testVideo.videoid);
            Console.WriteLine("Video " + this.testVideo.videoid + " deleted");
        }

        [TestMethod]
        public void UploadVideoByChunk()
        {
            var mp4File = File.OpenRead("../../../resources/assets/10m.mp4");

            long fileSize = mp4File.Length;
            int chunkSize = 1024 * 1024 * 5;

            apiClient.SetUploadChunkSize(chunkSize);
            var upl = new UploadProgressListener();

            apiClient.Videos().upload(testVideo.videoid, mp4File, upl);

            upl.totalBytes.Should().Be(fileSize);
            upl.totalUploaded.Should().Be(fileSize);
            upl.chunkCount.Should().Be((int)Math.Ceiling((float)fileSize / chunkSize));
            upl.seenChunkNums.Should().BeEquivalentTo(1, 2, 3);

            Console.WriteLine(testVideo);
        }

        [TestMethod]
        public void UploadVideoWithoutChunk()
        {
            var mp4File = File.OpenRead("../../../resources/assets/558k.mp4");

            long fileSize = mp4File.Length;
            int chunkSize = (int)fileSize + 1;

            var upl = new UploadProgressListener();

            apiClient.Videos().upload(testVideo.videoid, mp4File, upl);

            upl.totalBytes.Should().Be(fileSize);
            upl.totalUploaded.Should().Be(fileSize);
            upl.chunkCount.Should().Be(1);
            upl.seenChunkNums.Should().BeEquivalentTo(1);

            Console.WriteLine(testVideo);
        }

        [TestMethod]
        public void UpdateAddMetaDataAndTitle()
        {
            Video updated = apiClient.Videos().update(testVideo.videoid,
                    new VideoUpdatePayload()
                    {
                        metadata = new List<Metadata>(){
                            new Metadata(){key="firstKey", value="firstValue" },
                            new Metadata(){key="secondKey", value="secondValue"}
                        }
                    });

            updated.metadata.Should()
                            .BeEquivalentTo(
                                new Metadata(){key="firstKey", value="firstValue" },
                                new Metadata(){key="secondKey", value="secondValue"}
                             );

            Video updated2 = apiClient.Videos().update(testVideo.videoid,
                    new VideoUpdatePayload()
                    {
                        metadata = new List<Metadata>(){
                            new Metadata(){key="firstKey", value="firstValue" },
                            new Metadata(){key="secondKey", value="secondValue"},
                            new Metadata(){key="thirdKey", value="thirdValue"}
                        }
                    });

            updated2.metadata.Should()
                            .BeEquivalentTo(
                                new Metadata() { key = "firstKey", value = "firstValue" },
                                new Metadata() { key = "secondKey", value = "secondValue" },
                                new Metadata() { key = "thirdKey", value = "thirdValue" }
                            );


            Video updated3 = apiClient.Videos().update(testVideo.videoid,
                    new VideoUpdatePayload()
                    {
                        metadata = new List<Metadata>()
                    });

            updated3.metadata.Should()
                            .BeEmpty();


        }

        [TestMethod]
        public void Get()
        {
            var video = apiClient.Videos().get(testVideo.videoid);
            video.videoid.Should().Be(this.testVideo.videoid);
            video.title.Should().Be(this.testVideo.title);
        }


        [TestMethod]
        public void uploadThumbnail()
        {
            var jpgFile = File.OpenRead("../../../resources/assets/cat.jpg");

            Video video = apiClient.Videos().uploadThumbnail(testVideo.videoid, jpgFile);

            video.assets.Should().NotBeNull();
            video.assets.thumbnail.Should().NotBeNull();
        }

        [TestMethod]
        public void pickThumbnail()
        {
            var video = apiClient.Videos().pickThumbnail(testVideo.videoid,
                    new VideoThumbnailPickPayload() { timecode = "00:00:02" });

            video.assets.Should().NotBeNull();
            video.assets.thumbnail.Should().NotBeNull();
        }


        // Api side bug I think we have an ingest returned "[]" instead of "{}" so serialization issue
        [TestMethod]
        public void getVideoStatus() 
        {
            VideoStatus videoStatus = apiClient.Videos().getStatus(this.testVideo.videoid);

            videoStatus.ingest.Should().BeNull();
            videoStatus.encoding.Should().NotBeNull();
            videoStatus.encoding.playable.Should().BeFalse();
        }
    }
}
