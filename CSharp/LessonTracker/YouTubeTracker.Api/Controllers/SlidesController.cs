using Common;
using Microsoft.AspNetCore.Mvc;

namespace YouTubeTracker.Api.Controllers
{
    [ApiController]
    [Route("api/slides")]
    public class SlidesController : ControllerBase
    {
        private readonly BusinessManager _business;

        public SlidesController(BusinessManager business)
        {
            _business = business;
        }

        [HttpGet("{id}")]
        public ActionResult<Slide> Get(int id)
        {
            var slide = _business.GetSlideById(id);
            return slide == null ? NotFound() : Ok(slide);
        }

        [HttpGet("by-video/{videoId}")]
        public List<Slide> GetByVideo(int videoId)
        {
            return _business.GetSlidesForVideo(videoId);
        }

        [HttpGet]
        public List<Slide> GetAll()
        {
            return _business.GetAllSlides();
        }

        [HttpPost]
        public IActionResult Save(Slide slide)
        {
            _business.SaveSlide(slide);
            return Ok(slide);
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            _business.DeleteSlide(id);
            return NoContent();
        }
    }
}
