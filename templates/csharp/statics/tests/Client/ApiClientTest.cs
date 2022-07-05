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

            apiClient.Invoking(x => x.setApplicationName("bad application name", "0.0.1"))
                                .Should()
                                .Throw<Exception>()
                                .WithMessage("Invalid name value. Allowed characters: A-Z, a-z, 0-9, '-', '_'. Max length: 50.");

            apiClient.Invoking(x => x.setApplicationName("application", "test"))
                                .Should()
                                .Throw<Exception>()
                                .WithMessage("Invalid version value. The version should match the xxx[.yyy][.zzz] pattern.");

            apiClient.Invoking(x => x.setApplicationName("bad application name"))
                                .Should()
                                .Throw<Exception>()
                                .WithMessage("Version cannot be null");

            apiClient.Invoking(x => x.setApplicationName("application", "0.0.1"))
                                .Should()
                                .NotThrow();
        }
    }
}
