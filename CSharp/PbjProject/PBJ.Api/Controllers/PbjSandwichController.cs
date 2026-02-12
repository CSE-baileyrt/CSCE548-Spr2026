using Microsoft.AspNetCore.Mvc;
using PbjProject;

namespace PBJ.Api.Controllers
{
    [ApiController]
    [Route("api/pbjsandwich")]
    public class PbjSandwichController : ControllerBase
    {
        private readonly BusinessManager _bm;
        public PbjSandwichController(BusinessManager bm) => _bm = bm;


        [HttpPost]
        public IActionResult Save(PbjSandwich pbj)
        {
            pbj = _bm.SavePbj(pbj);
            return Ok(pbj);
        }


        [HttpGet("{id}")]
        public IActionResult Get(int id)
        => Ok(_bm.GetPbjById(id));


        [HttpGet]
        public IActionResult GetAll()
        => Ok(_bm.GetAllPbj());
    }
}
