using Common;
using Microsoft.AspNetCore.Mvc;

namespace YouTubeTracker.Api.Controllers
{
    [ApiController]
    [Route("api/lessons")]
    public class LessonsController : ControllerBase
    {
        private readonly BusinessManager _business;

        public LessonsController(BusinessManager business)
        {
            _business = business;
        }

        [HttpGet("{id}")]
        public ActionResult<Lesson> Get(int id)
        {
            var lesson = _business.GetLessonById(id);
            return lesson == null ? NotFound() : Ok(lesson);
        }

        [HttpGet]
        public List<Lesson> GetAll()
        {
            return _business.GetAllLessons();
        }

        [HttpGet("by-video-title/{title}")]
        public ActionResult<Lesson> GetByVideoTitle(string title)
        {
            var lesson = _business.GetLessonByVideoTitle(title);
            return lesson == null ? NotFound() : Ok(lesson);
        }

        [HttpPost]
        public IActionResult Save(Lesson lesson)
        {
            _business.SaveLesson(lesson);
            return Ok(lesson);
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            _business.DeleteLesson(id);
            return NoContent();
        }
    }
}
