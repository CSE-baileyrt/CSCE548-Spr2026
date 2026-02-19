namespace MyWorld.Data.Entities
{
    public class Country
    {
        public string Code { get; set; }           // PK (e.g. "USA")
        public string Name { get; set; }
        public string Continent { get; set; }
        public string Region { get; set; }
        public double? SurfaceArea { get; set; }
        public int? Population { get; set; }
        public ICollection<City> Cities { get; set; }
        public ICollection<CountryLanguage> Languages { get; set; }
    }
}
