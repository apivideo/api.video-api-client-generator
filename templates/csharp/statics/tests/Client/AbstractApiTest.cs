using Moq;
using RestSharp;
using System.Net;
using System.IO;
using ApiVideo.Client;
using ApiVideo.Api;
using System.Collections.Generic;
using System.Threading;

namespace VideoApiTests.Client
{
    public abstract class AbstractApiTest
    {
        protected static Mock<IRestClient> restClient = new Mock<IRestClient>();
        protected static ApiVideoClient apiClientMock = new ApiVideoClient(restClient.Object);

        protected string readResourceFile(string path)
        {
            return File.ReadAllText("../../../resources"+path);
        }
        protected void answerOnAnyRequest(int statusCode, string body)
        {
            var responseHeaders = new List<HeaderParameter>
            {
                new HeaderParameter("Content-Type", "application/json")
            };

            var response1 = new RestResponse
            {
                StatusCode = (HttpStatusCode)statusCode,
                Content = body,
                ResponseStatus = ResponseStatus.Completed,
                Headers = responseHeaders
            };

            restClient.Setup(m => m.ExecuteAsync(It.IsAny<RestRequest>(), It.IsAny<CancellationToken>()))
                          .ReturnsAsync(response1);
        }
    }
}