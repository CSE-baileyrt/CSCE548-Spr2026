using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    /*
     * This is where the business rules go. For example, see SaveLesson below (added a todo)
     */

    public class BusinessManager
    {
        private readonly DataProvider _db;

        public BusinessManager(DataProvider dataManager)
        {
            _db = dataManager;
        }

        // ------------------------------------
        // VIDEO BUSINESS METHODS
        // ------------------------------------
        public void SaveVideo(Video video)
        {
            if (video.Id == 0)
            {
                video.Id = _db.CreateVideo(video);
            }
            else
            {
                _db.UpdateVideo(video);
            }
        }

        public void DeleteVideo(int videoId)
        {
            _db.DeleteVideo(videoId);
        }

        public Video GetVideoById(int id)
        {
            var record = _db.GetVideoById(id);
            return record ?? throw new KeyNotFoundException("No Video found with that Id");
        }

        public List<Video> GetPlannedVideos(bool planned)
        {
            return _db.GetPlannedVideos(planned);
        }

        public List<Video> GetRecordedVideos(bool recorded)
        {
            return _db.GetRecordedVideos(recorded);
        }

        public List<Video> GetPostedVideos(bool posted)
        {
            return _db.GetPostedVideos(posted);
        }
        public List<Video> GetAllVideos()
        {
            return _db.GetAllVideos();
        }

        // ------------------------------------
        // LESSON BUSINESS METHODS
        // ------------------------------------
        public void SaveLesson(Lesson lesson)
        {
            // TODO: validate module and sequence
            // inside the module, sequence is unique

            if (lesson.Video == null)
                throw new System.Exception("Lesson must have an associated Video.");

            if (lesson.Id == 0)
            {
                lesson.Id = _db.CreateLesson(lesson, lesson.Video.Id);
            }
            else
            {
                _db.UpdateLesson(lesson);
            }
        }

        public void DeleteLesson(int lessonId)
        {
            _db.DeleteLesson(lessonId);
        }

        public Lesson GetLessonById(int id)
        {
            var record = _db.GetLessonById(id);
            return record ?? throw new KeyNotFoundException("No Lesson found with that Id");
        }

        public Lesson GetLessonByVideoTitle(string videoTitle)
        {
            var record = _db.GetLessonByVideoTitle(videoTitle);
            return record ?? throw new KeyNotFoundException("No Lesson found with that Title");
        }

        public List<Lesson> GetAllLessons()
        {
            return _db.GetAllLessons();
        }

        // ------------------------------------
        // SLIDE BUSINESS METHODS
        // ------------------------------------
        public void SaveSlide(Slide slide)
        {
            if (slide.Id == 0)
            {
                slide.Id = _db.CreateSlide(slide);
            }
            else
            {
                _db.UpdateSlide(slide);
            }
        }

        public void DeleteSlide(int slideId)
        {
            _db.DeleteSlide(slideId);
        }

        public Slide GetSlideById(int id)
        {
            var record = (_db.GetSlideById(id));
            return record ?? throw new KeyNotFoundException("No Slide found with that Id");
        }

        public List<Slide> GetSlidesForVideo(int videoId)
        {
            return _db.GetSlidesForVideo(videoId);
        }

        public List<Slide> GetAllSlides()
        {
            return _db.GetAllSlides();
        }
    }
}
