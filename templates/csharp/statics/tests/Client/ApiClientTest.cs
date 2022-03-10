using System;
using System.Collections.Generic;
using System.IO;
using FluentAssertions;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ApiVideo.Client;
using ApiVideo.Model;
using ApiVideo.Upload;


namespace ApiVideoTests.Client
{
    [TestClass]
    public class Instanciation
    {
        [TestMethod]
        public void setApplicationName()
        {
            ApiVideoClient apiClient = new ApiVideoClient("test");

            apiClient.Invoking(x => x.setApplicationName("bad application name"))
                                .Should()
                                .Throw<Exception>()
                                .WithMessage("Invalid applicationName value. Allowed characters: A-Z, a-z, 0-9, '-', '_'. Max length: 50.");
        }
    }
}
