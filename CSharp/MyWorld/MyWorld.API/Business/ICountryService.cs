using MyWorld.Data.Entities;

namespace MyWorld.Business
{
    public interface ICountryService
    {
        Task<IEnumerable<Country>> GetAllCountriesAsync();
        Task<Country> GetCountryByCodeAsync(string code);
        Task<IEnumerable<Country>> GetCountriesByContinentAsync(string continent);
    }

}
