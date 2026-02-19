using MyWorld.Data;
using MyWorld.Data.Entities;

namespace MyWorld.Business
{
    public class CountryService : ICountryService
    {
        private readonly ICountryRepository _repo;
        public CountryService(ICountryRepository repo) => _repo = repo;

        public async Task<IEnumerable<Country>> GetAllCountriesAsync()
        {
            return await _repo.GetAllAsync();
        }

        public async Task<Country> GetCountryByCodeAsync(string code)
        {
            if (string.IsNullOrWhiteSpace(code)) throw new ArgumentException(nameof(code));
            return await _repo.GetByCodeAsync(code);
        }

        public async Task<IEnumerable<Country>> GetCountriesByContinentAsync(string continent)
        {
            return await _repo.GetByContinentAsync(continent);
        }
    }

}
