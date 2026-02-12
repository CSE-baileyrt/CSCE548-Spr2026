using Microsoft.AspNetCore.Mvc;
using PbjProject;

namespace PBJ.Api.Controllers
{
    [ApiController]
    [Route("api/peanutbutter")]
    public class PeanutButterController : ControllerBase
    {
        private readonly BusinessManager _bm;
        public PeanutButterController(BusinessManager bm) => _bm = bm;


        [HttpPost]
        public IActionResult Save(PeanutButter pb)
        {
            _bm.SavePeanutButter(pb);
            return Ok(pb);
        }


        [HttpGet("{id}")]
        public IActionResult Get(int id)
        => Ok(_bm.GetPeanutButterById(id));


        [HttpGet]
        public IActionResult GetAll()
        => Ok(_bm.GetAllPeanutButter());
    }
}
