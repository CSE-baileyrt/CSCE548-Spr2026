namespace MyWorld.Client.Models
{
    public class CityDto
    {
        public int Id { get; set; }
        public string Name { get; set; } = default!;
        public string District { get; set; } = default!;
        public int? Population { get; set; }
    }
}
