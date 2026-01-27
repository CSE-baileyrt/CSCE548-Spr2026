using Common;
using Microsoft.AspNetCore.Mvc;

namespace YouTubeTracker.Api.Controllers
{
    [ApiController]
    [Route("api/videos")]
    public class VideosController : ControllerBase
    {
        private readonly BusinessManager _business;

        public VideosController(BusinessManager business)
        {
            _business = business;
        }

        [HttpGet("{id}")]
        public ActionResult<Video> Get(int id)
        {
            var video = _business.GetVideoById(id);
            return video == null ? NotFound() : Ok(video);
        }

        [HttpGet]
        public List<Video> GetAll()
        {
            return _business.GetAllVideos();
        }

        [HttpPost]
        public IActionResult Save(Video video)
        {
            _business.SaveVideo(video);
            return Ok(video);
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            _business.DeleteVideo(id);
            return NoContent();
        }

        [HttpGet("planned/{value}")]
        public List<Video> GetPlanned(bool value)
            => _business.GetPlannedVideos(value);

        [HttpGet("recorded/{value}")]
        public List<Video> GetRecorded(bool value)
            => _business.GetRecordedVideos(value);

        [HttpGet("posted/{value}")]
        public List<Video> GetPosted(bool value)
            => _business.GetPostedVideos(value);
    }
}
