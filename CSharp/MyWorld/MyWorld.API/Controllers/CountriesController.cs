using Microsoft.AspNetCore.Mvc;
using MyWorld.Business;

namespace MyWorld.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CountriesController : ControllerBase
    {
        private readonly ICountryService _countryService;
        public CountriesController(ICountryService countryService) => _countryService = countryService;

        [HttpGet]
        public async Task<IActionResult> GetAll() =>
            Ok(await _countryService.GetAllCountriesAsync());

        [HttpGet("{code}")]
        public async Task<IActionResult> GetByCode(string code)
        {
            var c = await _countryService.GetCountryByCodeAsync(code);
            if (c == null) return NotFound();
            return Ok(c);
        }

        [HttpGet("continent/{continent}")]
        public async Task<IActionResult> GetByContinent(string continent) =>
            Ok(await _countryService.GetCountriesByContinentAsync(continent));
    }

}
