namespace MyWorld.Client.Models
{
    public class CountryDto
    {
        public string Code { get; set; } = default!;
        public string Name { get; set; } = default!;
        public string Continent { get; set; } = default!;
        public int? Population { get; set; }
        public List<CityDto>? Cities { get; set; }
    }
}
