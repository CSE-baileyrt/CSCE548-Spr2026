using System.Net.Http.Json;
using MyWorld.Client.Models;

namespace MyWorld.Client.Services
{
    public class CountryService
    {
        private readonly HttpClient _http;
        public CountryService(HttpClient http) => _http = http;

        public async Task<List<CountryDto>?> GetAllAsync(CancellationToken ct = default) =>
            await _http.GetFromJsonAsync<List<CountryDto>>("api/countries", ct);

        public async Task<CountryDto?> GetByCodeAsync(string code, CancellationToken ct = default) =>
            await _http.GetFromJsonAsync<CountryDto>($"api/countries/{Uri.EscapeDataString(code)}", ct);

        public async Task<List<CountryDto>?> GetByContinentAsync(string continent, CancellationToken ct = default) =>
            await _http.GetFromJsonAsync<List<CountryDto>>($"api/countries/continent/{Uri.EscapeDataString(continent)}", ct);
    }
}
