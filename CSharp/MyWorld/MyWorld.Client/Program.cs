using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using MyWorld.Client;
using MyWorld.Client.Services;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

//original
//builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri(builder.HostEnvironment.BaseAddress) });
//replacement
builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri("https://localhost:7085/") });

builder.Services.AddScoped<CountryService>();

await builder.Build().RunAsync();
