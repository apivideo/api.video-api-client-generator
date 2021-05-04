const { expect } = require('chai');
const ApiVideoClient = require('../src').default;
// import Caption from '../models/caption';
// import Video from '../models/video';
// import Player from '../models/player';

const timeout = (ms = 100) => new Promise((resolve) => {
  setTimeout(() => {
    resolve();
  }, ms);
});

(async () => {
  try {
    if (!process.env.API_KEY) {
      console.error('You must provide `API_KEY` environment variable to test the sandbox.');
      console.log('API_KEY=xxx yarn test:sandbox');

      process.exit(1);
    }

    // Create client for Sandbox and authenticate
    const client = new ApiVideoClient({ apiKey: process.env.API_KEY });

    // Create
    const videoTitle = 'Course #4 - Part B';
    const { videoId, title } = await client.videos.create({ title: videoTitle });

    // upload a video resource
    await client.videos.upload(videoId,'test/data/small.webm').then((video) => {
      expect(title).to.equals(videoTitle);
    });


    // Update video properties
    const newDescription = 'Course #4 - Part C';
    await client.videos.update(videoId, { description: newDescription }).then((video) => {
      expect(video.videoId).to.equals(videoId);
    });

    // Search videos with paginated results
    await client.videos.search({ currentPage: 1, pageSize: 50 }).then((videos) => {
      expect(videos).to.be.an('array');
      videos.forEach(video => expect(video).to.have.keys(new Video()));
    });

    // Upload a video thumbnail
    await client.videos.uploadThumbnail('test/data/test.jpg', videoId).then((video) => {
      expect(video.videoId).to.equals(videoId);
    });

    // Update video thumbnail by picking image with video timecode
    await client.videos.updateThumbnailWithTimecode(videoId, '00:15:22.05').then((video) => {
      expect(video.videoId).to.equals(videoId);
    });

    // Upload video caption
    await client.captions.upload('test/data/en.vtt', { videoId, language: 'en' }).then((caption) => {
      expect(caption).to.have.keys(new Caption());
    });

    await timeout(1000);

    // Get video caption by language
    await client.captions.get(videoId, 'en').then((caption) => {
      expect(caption.src).to.be.a('string');
    });

    // Update the default caption language
    await client.captions.updateDefault(videoId, 'en', true).then((caption) => {
      expect(caption.srclang).to.equals('en');
      expect(caption.default).to.equals(true);
    });

    // Delete caption by language
    await client.captions.delete(videoId, 'en').then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Upload video chapter
    await client.chapters.upload('test/data/en.vtt', { videoId, language: 'en' }).then((chapter) => {
      expect(chapter.language).to.equals('en');
    });

    await timeout(1000);

    // Get video chapter by language
    await client.chapters.get(videoId, 'en').then((chapter) => {
      expect(chapter.language).to.equals('en');
    });

    // Delete chapter by language
    await client.chapters.delete(videoId, 'en').then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Get video Analytics Data for the current year
    await client.analyticsVideo.get(videoId, new Date().getFullYear()).then((analyticVideo) => {
      expect(analyticVideo.data).to.be.an('array');
    });

    // Delete video ressource
    await client.videos.delete(videoId).then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Create players with default values
    const { playerId } = await client.players.create();
    expect(playerId).to.be.a('string');

    // Get a player
    await client.players.get(playerId).then((player) => {
      expect(playerId).to.equals(player.playerId);
    });

    // Search a player with paginate results
    await client.players.search({ currentPage: 1, pageSize: 50 }).then((players) => {
      expect(players).to.be.an('array');
      players.forEach(player => expect(player).to.have.keys(new Player()));
    });

    const properties = {
      shapeMargin: 10,
      shapeRadius: 3,
      shapeAspect: 'flat',
      shapeBackgroundTop: 'rgba(50, 50, 50, .7)',
      shapeBackgroundBottom: 'rgba(50, 50, 50, .8)',
      text: 'rgba(255, 255, 255, .95)',
      link: 'rgba(255, 0, 0, .95)',
      linkHover: 'rgba(255, 255, 255, .75)',
      linkActive: 'rgba(255, 0, 0, .75)',
      trackPlayed: 'rgba(255, 255, 255, .95)',
      trackUnplayed: 'rgba(255, 255, 255, .1)',
      trackBackground: 'rgba(0, 0, 0, 0)',
      backgroundTop: 'rgba(72, 4, 45, 1)',
      backgroundBottom: 'rgba(94, 95, 89, 1)',
      backgroundText: 'rgba(255, 255, 255, .95)',
      enableApi: true,
      enableControls: true,
      forceAutoplay: false,
      hideTitle: true,
      forceLoop: true,
    };

    await client.players.update(playerId, properties).then((player) => {
      Object.keys(properties).forEach((property) => {
        expect(player).to.have.property(property, properties[property]);
      });
    });

    await client.players.uploadLogo('test/data/test.jpg', playerId, 'https://api.video').then((player) => {
      expect(player.logo.link).to.equals('https://api.video');
    });

    await client.players.delete(playerId).then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Create a live
    const name = 'This is a live';
    const { liveStreamId } = await client.lives.create(name);

    // Update live thumbnail
    await client.lives.uploadThumbnail('test/data/test.jpg', liveStreamId).then((live) => {
      expect(live.name).to.equals(name);
    });

    // Get live Analytics Data for the current year
    await client.analyticsLive.get(liveStreamId, new Date().getFullYear()).then((analyticLive) => {
      expect(analyticLive.data).to.be.an('array');
    });

    // Delete live ressource
    await client.lives.delete(liveStreamId).then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Create a private live
    const { liveStreamId: privateLiveStreamId, public: isPublic } = await client.lives.create('This is a private live', { public: false });
    expect(isPublic).to.equals(false);

    // Delete the private live
    await client.lives.delete(privateLiveStreamId).then((statusCode) => {
      expect(statusCode).to.equals(204);
    });

    // Generate a token for delegated upload
    await client.tokens.generate().then((token) => {
      expect(token).to.be.a('string');
    });
  } catch (e) {
    console.error(e);
    process.exit(1);
  }
})();
