using Microsoft.AspNetCore.Mvc;
using PbjProject;

namespace PBJ.Api.Controllers
{
    [ApiController]
    [Route("api/jelly")]
    public class JellyController : ControllerBase
    {
        private readonly BusinessManager _bm;
        public JellyController(BusinessManager bm) => _bm = bm;


        [HttpPost]
        public IActionResult Save(Jelly jelly)
        {
            _bm.SaveJelly(jelly);
            return Ok(jelly);
        }


        [HttpGet("{id}")]
        public IActionResult Get(int id)
        => Ok(_bm.GetJellyById(id));


        [HttpGet]
        public IActionResult GetAll()
        => Ok(_bm.GetAllJelly());
    }
}
