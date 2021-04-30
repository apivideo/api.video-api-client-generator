using Moq;
using RestSharp;
using System.Net;
using System.IO;
using ApiVideo.Client;

namespace VideoApiTests.Client
{
    public abstract class AbstractApiTest
    {
        private static RestClient restClient = Mock.Of<RestClient>();
        protected static ApiVideoClient apiClientMock = new ApiVideoClient(restClient);

        protected string readResourceFile(string path)
        {
            return File.ReadAllText("../../../resources"+path);
        }
        protected void answerOnAnyRequest(int statusCode, string body)
        {
            var r = new RestResponse()
            {
                StatusCode = (HttpStatusCode)statusCode,
                Content = body,
                ResponseStatus = ResponseStatus.Completed
            };
            Mock.Get(restClient).Setup(m => m.Execute(It.IsAny<IRestRequest>())).Returns(r);
        }
    }

}
