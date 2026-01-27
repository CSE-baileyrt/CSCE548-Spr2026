using Common;
using System;
using System.Collections.Generic;
using System.Net.Http.Json;
using System.Text;

namespace LessonTrackerTier1
{
    public class ApiClient
    {
        private readonly HttpClient _http;

        public ApiClient(string baseUrl)
        {
            _http = new HttpClient
            {
                BaseAddress = new Uri(baseUrl)
            };
        }

        // ----------------------
        // VIDEOS
        // ----------------------
        public async Task<Video> GetVideoAsync(int id)
            => await _http.GetFromJsonAsync<Video>($"api/videos/{id}");

        public async Task<List<Video>> GetPostedVideosAsync(bool value)
            => await _http.GetFromJsonAsync<List<Video>>($"api/videos/posted/{value}");

        public async Task<List<Video>> GetAllVideosAsync()
            => await _http.GetFromJsonAsync<List<Video>>("api/videos");

        public async Task SaveVideoAsync(Video video)
        {
            var response = await _http.PostAsJsonAsync("api/videos", video);
            response.EnsureSuccessStatusCode();
        }

        public async Task DeleteVideoAsync(int id)
        {
            var response = await _http.DeleteAsync($"api/videos/{id}");
            response.EnsureSuccessStatusCode();
        }

        // ----------------------
        // LESSONS
        // ----------------------
        public async Task<Lesson> GetLessonByVideoTitleAsync(string title)
            => await _http.GetFromJsonAsync<Lesson>($"api/lessons/by-video-title/{title}");
        public async Task<List<Lesson>> GetAllLessonsAsync()
            => await _http.GetFromJsonAsync<List<Lesson>>("api/lessons");

        public async Task SaveLessonAsync(Lesson lesson)
        {
            var response = await _http.PostAsJsonAsync("api/lessons", lesson);
            response.EnsureSuccessStatusCode();
        }

        // ----------------------
        // SLIDES
        // ----------------------
        public async Task<List<Slide>> GetSlidesForVideoAsync(int videoId)
            => await _http.GetFromJsonAsync<List<Slide>>($"api/slides/by-video/{videoId}");

        public async Task<List<Slide>> GetAllSlidesAsync()
            => await _http.GetFromJsonAsync<List<Slide>>("api/slides");

        public async Task SaveSlideAsync(Slide slide)
        {
            var response = await _http.PostAsJsonAsync("api/slides", slide);
            response.EnsureSuccessStatusCode();
        }
    }
}
