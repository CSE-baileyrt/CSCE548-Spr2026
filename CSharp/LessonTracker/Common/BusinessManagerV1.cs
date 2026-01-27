using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    /*
     * There are errors (simple to fix)
     * There are null reference warnings (I don't want any nullable return types)
     */

    public class BusinessManagerV1
    {
        private readonly DataProvider _dataManager;

        public BusinessManagerV1(DataProvider dataManager)
        {
            _dataManager = dataManager;
        }

        // ------------------------------------
        // VIDEO BUSINESS METHODS
        // ------------------------------------
        public void SaveVideo(Video video)
        {
            if (video.Id == 0)
            {
                video.Id = _dataManager.CreateVideo(video);
            }
            else
            {
                _dataManager.UpdateVideo(video);
            }
        }

        public void DeleteVideo(int videoId)
        {
            _dataManager.DeleteVideo(videoId);
        }

        public Video GetVideoById(int id)
        {
            return _dataManager.GetVideoById(id);
        }

        public List<Video> GetPlannedVideos(bool planned)
        {
            return _dataManager.GetPlannedVideos(planned);
        }

        public List<Video> GetRecordedVideos(bool recorded)
        {
            return _dataManager.GetRecordedVideos(recorded);
        }

        public List<Video> GetPostedVideos(bool posted)
        {
            return _dataManager.GetPostedVideos(posted);
        }

        // ------------------------------------
        // LESSON BUSINESS METHODS
        // ------------------------------------
        //public void SaveLesson(Lesson lesson)
        //{
        //    if (lesson.Video == null)
        //        throw new System.Exception("Lesson must have an associated Video.");

        //    if (lesson.Id == 0)
        //    {
        //        lesson.Id = _dataManager.CreateLesson(lesson, lesson.Video.Id);
        //    }
        //    else
        //    {
        //        _dataManager.UpdateLesson(lesson);
        //    }
        //}

        public void DeleteLesson(int lessonId)
        {
            _dataManager.DeleteLesson(lessonId);
        }

        public Lesson GetLessonById(int id)
        {
            return _dataManager.GetLessonById(id);
        }

        public Lesson GetLessonByVideoTitle(string videoTitle)
        {
            return _dataManager.GetLessonByVideoTitle(videoTitle);
        }

        // ------------------------------------
        // SLIDE BUSINESS METHODS
        // ------------------------------------
        //public void SaveSlide(Slide slide)
        //{
        //    if (slide.Id == 0)
        //    {
        //        slide.Id = _dataManager.CreateSlide(slide);
        //    }
        //    else
        //    {
        //        _dataManager.UpdateSlide(slide);
        //    }
        //}

        //public void DeleteSlide(int slideId)
        //{
        //    _dataManager.DeleteSlide(slideId);
        //}

        public Slide GetSlideById(int id)
        {
            return _dataManager.GetSlideById(id);
        }

        public List<Slide> GetSlidesForVideo(int videoId)
        {
            return _dataManager.GetSlidesForVideo(videoId);
        }
    }
}
