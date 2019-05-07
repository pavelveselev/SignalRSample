using System;
using System.Linq;
using System.Reactive.Linq;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.SignalR.Client;
using SignalRServer.Hubs;

namespace SignalRServer.Services
{
    public class TestStreamingService
    {
        private readonly ILogger<TestStreamingService> _logger;
        private readonly IHubContext<SimpleHub> _hubContext;
        private readonly IDisposable _subscription;
        public TestStreamingService(
            ILogger<TestStreamingService> logger,
            IHubContext<SimpleHub> hubContext)
        {
            _logger = logger;
            _hubContext = hubContext;

            _subscription = Observable.Interval(TimeSpan.FromSeconds(1))
                .Select(x => DateTime.Now.ToString())
                .Subscribe(async x =>
                {
                    await _hubContext.Clients.All.SendAsync("SendTest", x);
                });
        }

        public void Dispose()
        {
            _subscription.Dispose();
        }
    }
}
