namespace MyWorld.Data.Entities
{
    public class CountryLanguage
    {
        public string CountryCode { get; set; }   // composite PK
        public string Language { get; set; }      // composite PK
        public bool IsOfficial { get; set; }
        public double Percentage { get; set; }

        public Country Country { get; set; }
    }
}
