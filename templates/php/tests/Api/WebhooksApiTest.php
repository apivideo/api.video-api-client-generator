<?php declare(strict_types = 1);

namespace ApiVideo\SDK\Tests\Api;

use ApiVideo\SDK\Model\WebhooksCreationPayload;

class WebhooksApiTest extends AbstractApiTest
{
    /**
     * Removes existing videos
     */
    public function setUp(): void
    {
        parent::setUp();

        $webhooks = $this->client->webhooks()->list([]);
        foreach ($webhooks->getData() as $webhook) {
            $this->client->webhooks()->delete($webhook->getWebhookId());
        }
    }

    public function testList()
    {
        $webhooks = $this->client->webhooks()->list();
        $this->assertCount(0, $webhooks->getData());

        for ($i = 0; $i < 3; $i++) {
            $payload = (new WebhooksCreationPayload())
                ->setEvents(['video.encoding.quality.completed'])
                ->setUrl('https://example.com/webhooks')
            ;

            $this->client->webhooks()->create($payload);
        }

        $webhooks = $this->client->webhooks()->list();
        $this->assertCount(3, $webhooks->getData());
    }

    /**
     * @covers \ApiVideo\SDK\Api\WebhooksApi::create
     * @covers \ApiVideo\SDK\Api\WebhooksApi::get
     * @covers \ApiVideo\SDK\Api\WebhooksApi::delete
     */
    public function testCreate()
    {
        $payload = (new WebhooksCreationPayload())
            ->setEvents(['video.encoding.quality.completed'])
            ->setUrl('https://example.com/webhooks')
        ;

        $webhook = $this->client->webhooks()->create($payload);
        $webhookId = $webhook->getWebhookId();

        $this->assertNotNull($webhookId);

        // Get

        $webhook = $this->client->webhooks()->get($webhookId);
        $this->assertEquals($webhookId, $webhook->getWebhookId());

        // Delete

        $webhooks = $this->client->webhooks()->list();
        $this->assertCount(1, $webhooks->getData());

        $this->client->webhooks()->delete($webhookId);

        $webhooks = $this->client->webhooks()->list();
        $this->assertCount(0, $webhooks->getData());
    }
}
