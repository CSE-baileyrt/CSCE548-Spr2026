using Microsoft.AspNetCore.Mvc;
using PbjProject;
using System.Threading;

namespace PBJ.Api.Controllers
{
    [ApiController]
    [Route("api/bread")]
    public class BreadController : ControllerBase
    {
        private readonly BusinessManager _bm;
        public BreadController(BusinessManager bm) => _bm = bm;


        [HttpPost]
        public IActionResult Save(Bread bread)
        {
            _bm.SaveBread(bread);
            return Ok(bread);
        }


        [HttpGet("{id}")]
        public IActionResult Get(int id)
        => Ok(_bm.GetBreadById(id));


        [HttpGet]
        public IActionResult GetAll()
        => Ok(_bm.GetAllBread());
    }
}
