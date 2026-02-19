using Microsoft.EntityFrameworkCore;
using MyWorld.Data.Entities;

namespace MyWorld.Data
{
    public interface ICountryRepository : IRepository<Country>
    {
        Task<Country> GetByCodeAsync(string code);
        Task<IEnumerable<Country>> GetByContinentAsync(string continent);
    }

    public class CountryRepository : ICountryRepository
    {
        private readonly WorldDbContext _db;
        public CountryRepository(WorldDbContext db) => _db = db;

        public async Task<IEnumerable<Country>> GetAllAsync() =>
            await _db.Countries.Include(c => c.Cities).ToListAsync();

        public async Task<Country> GetAsync(object id) => await _db.Countries.FindAsync(id);

        public async Task<Country> GetByCodeAsync(string code) =>
            await _db.Countries.Include(c => c.Cities).FirstOrDefaultAsync(c => c.Code == code);

        public async Task<IEnumerable<Country>> GetByContinentAsync(string continent) =>
            await _db.Countries.Where(c => c.Continent == continent).ToListAsync();

        public async Task AddAsync(Country entity) { await _db.Countries.AddAsync(entity); }

        public void Update(Country entity) { _db.Countries.Update(entity); }

        public void Delete(Country entity) { _db.Countries.Remove(entity); }

        public async Task SaveChangesAsync() => await _db.SaveChangesAsync();
    }

}
