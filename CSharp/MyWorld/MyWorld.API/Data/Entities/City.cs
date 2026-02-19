using System.Text.Json.Serialization;

namespace MyWorld.Data.Entities
{
    public class City
    {
        public int Id { get; set; }                // PK
        public string Name { get; set; }
        public string CountryCode { get; set; }   // FK -> Country.Code
        [JsonIgnore]
        public Country Country { get; set; }
        public string District { get; set; }
        public int? Population { get; set; }
    }
}
