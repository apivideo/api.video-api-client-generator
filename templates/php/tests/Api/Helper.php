<?php

namespace ApiVideo\SDK\Tests\Api;

use ApiVideo\SDK\Client;
use ApiVideo\SDK\Model\LiveStream;
use ApiVideo\SDK\Model\LiveStreamCreationPayload;
use ApiVideo\SDK\Model\TokenCreationPayload;
use ApiVideo\SDK\Model\UploadToken;
use ApiVideo\SDK\Model\Video;
use ApiVideo\SDK\Model\VideoCreationPayload;

class Helper
{
    /**
     * @var Client
     */
    private $client;

    /**
     * @param Client $client
     */
    public function __construct(Client $client)
    {
        $this->client = $client;
    }

    public function createUploadToken(int $ttl = 0): UploadToken
    {
        $payload = (new TokenCreationPayload())
            ->setTtl($ttl);

        return $this->client->uploadTokens()->createToken($payload);
    }

    public function createLiveStream(): LiveStream
    {
        $payload = (new LiveStreamCreationPayload())
            ->setName('Test live stream');

        return $this->client->liveStreams()->create($payload);
    }

    public function createVideo(): Video
    {
        $payload = (new VideoCreationPayload())
            ->setTitle('Test video creation');

        return $this->client->videos()->create($payload);
    }
}
