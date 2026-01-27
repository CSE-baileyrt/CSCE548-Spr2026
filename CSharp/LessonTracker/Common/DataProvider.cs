using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    /*
     * This is the final, debugged and corrected version
     * SqlConnection replaced by MySqlConnection, SqlCommand replaced by MySqlCommand for all methods
     * Null reference code handled
     * 
     * Added a GetAllLessons method (I didn't think of it when I submitted the prompt)
     * Added UpdateLesson method (missing, not generated)
     * Added UpdateSlide method (missing, not generated)
     * Added DeleteSlide method (missing, not generated)
     */

    public class DataProvider
    {
        private readonly string _connectionString;

        public DataProvider(string connectionString)
        {
            _connectionString = connectionString;
        }

        // -------------------------
        // VIDEO CRUD
        // -------------------------
        public int CreateVideo(Video video)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"INSERT INTO Videos (Planned, Recorded, Posted, Title, Description, Thumbnail)
                  VALUES (@Planned, @Recorded, @Posted, @Title, @Description, @Thumbnail);
                  SELECT LAST_INSERT_ID();", conn);

            cmd.Parameters.AddWithValue("@Planned", video.Planned);
            cmd.Parameters.AddWithValue("@Recorded", video.Recorded);
            cmd.Parameters.AddWithValue("@Posted", video.Posted);
            cmd.Parameters.AddWithValue("@Title", video.Title);
            cmd.Parameters.AddWithValue("@Description", (object)video.Description ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@Thumbnail", (object)video.Thumbnail ?? DBNull.Value);

            conn.Open();
            return Convert.ToInt32(cmd.ExecuteScalar());
        }

        public Video? GetVideoById(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Videos WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapVideo(reader) : null;
        }
        public List<Video> GetAllVideos()
        {
            var videos = new List<Video>();
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Videos", conn);
            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                videos.Add(MapVideo(reader));
            }
            return videos;
        }
        public void UpdateVideo(Video video)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"UPDATE Videos SET
                Planned = @Planned,
                Recorded = @Recorded,
                Posted = @Posted,
                Title = @Title,
                Description = @Description,
                Thumbnail = @Thumbnail
                WHERE Id = @Id", conn);

            cmd.Parameters.AddWithValue("@Id", video.Id);
            cmd.Parameters.AddWithValue("@Planned", video.Planned);
            cmd.Parameters.AddWithValue("@Recorded", video.Recorded);
            cmd.Parameters.AddWithValue("@Posted", video.Posted);
            cmd.Parameters.AddWithValue("@Title", video.Title);
            cmd.Parameters.AddWithValue("@Description", (object)video.Description ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@Thumbnail", (object)video.Thumbnail ?? DBNull.Value);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        public void DeleteVideo(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("DELETE FROM Videos WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        // -------------------------
        // LESSON CRUD
        // -------------------------
        public int CreateLesson(Lesson lesson, int videoId)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"INSERT INTO Lessons (VideoId, Topic, Summary, Intro, Module, Sequence)
                  VALUES (@VideoId, @Topic, @Summary, @Intro, @Module, @Sequence);
                  SELECT LAST_INSERT_ID();", conn);

            cmd.Parameters.AddWithValue("@VideoId", videoId);
            cmd.Parameters.AddWithValue("@Topic", lesson.Topic);
            cmd.Parameters.AddWithValue("@Summary", lesson.Summary);
            cmd.Parameters.AddWithValue("@Intro", lesson.Intro);
            cmd.Parameters.AddWithValue("@Module", lesson.Module);
            cmd.Parameters.AddWithValue("@Sequence", lesson.Sequence);

            conn.Open();
            return Convert.ToInt32(cmd.ExecuteScalar());
        }

        public Lesson? GetLessonById(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Lessons WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapLesson(reader) : null;
        }

        public List<Lesson> GetAllLessons()
        {
            var lessons = new List<Lesson>();
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Lessons", conn);
            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                lessons.Add(MapLesson(reader));
            }
            return lessons;
        }

        public void UpdateLesson(Lesson lesson)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"UPDATE Lessons SET
                VideoId = @VideoId,
                Topic = @Topic, 
                Summary = @Summary,
                Intro = @Intro,
                Module = @Module,
                Sequence = @Sequence
                WHERE Id = @Id", conn);

            cmd.Parameters.AddWithValue("@Id", lesson.Id);
            //if the video is null, then put in default video Id of -1
            cmd.Parameters.AddWithValue("@VideoId", lesson.Video?.Id ?? -1);
            cmd.Parameters.AddWithValue("@Topic", lesson.Topic);
            cmd.Parameters.AddWithValue("@Summary", lesson.Summary);
            cmd.Parameters.AddWithValue("@Intro", lesson.Intro);
            cmd.Parameters.AddWithValue("@Module", lesson.Module);
            cmd.Parameters.AddWithValue("@Sequence", lesson.Sequence);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        public void DeleteLesson(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("DELETE FROM Lessons WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        // -------------------------
        // SLIDE CRUD
        // -------------------------
        public int CreateSlide(Slide slide)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"INSERT INTO Slides (LessonId, Title, Content, Graphic, Script)
                  VALUES (@LessonId, @Title, @Content, @Graphic, @Script);
                  SELECT LAST_INSERT_ID();", conn);

            cmd.Parameters.AddWithValue("@LessonId", slide.LessonId);
            cmd.Parameters.AddWithValue("@Title", slide.Title);
            cmd.Parameters.AddWithValue("@Content", slide.Content);
            cmd.Parameters.AddWithValue("@Graphic", (object)slide.Graphic ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@Script", slide.Script);

            conn.Open();
            return Convert.ToInt32(cmd.ExecuteScalar());
        }

        public Slide? GetSlideById(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Slides WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapSlide(reader) : null;
        }

        public List<Slide> GetSlidesForVideo(int videoId)
        {
            var slides = new List<Slide>();

            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"SELECT s.*
                  FROM Slides s
                  JOIN Lessons l ON s.LessonId = l.Id
                  WHERE l.VideoId = @VideoId", conn);

            cmd.Parameters.AddWithValue("@VideoId", videoId);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                slides.Add(MapSlide(reader));
            }

            return slides;
        }
        public List<Slide> GetAllSlides()
        {
            var slides = new List<Slide>();
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Slides", conn);
            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                slides.Add(MapSlide(reader));
            }
            return slides;
        }
        public void UpdateSlide(Slide slide)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                @"UPDATE Videos SET
                LessonId = @LessonId,
                Title = @Title,
                Content = @Content,
                Graphic = @Graphic,
                Script = @Script
                WHERE Id = @Id", conn);

            cmd.Parameters.AddWithValue("@Id", slide.Id);
            cmd.Parameters.AddWithValue("@LessonId", slide.LessonId);
            cmd.Parameters.AddWithValue("@Title", slide.Title);
            cmd.Parameters.AddWithValue("@Content", slide.Content);
            cmd.Parameters.AddWithValue("@Graphic", (object)slide.Graphic ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@Script", slide.Script);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        public void DeleteSlide(int id)
        {
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("DELETE FROM Slides WHERE Id = @Id", conn);
            cmd.Parameters.AddWithValue("@Id", id);

            conn.Open();
            cmd.ExecuteNonQuery();
        }

        // -------------------------
        // COMPLEX QUERIES
        // -------------------------
        public Lesson? GetLessonByVideoTitle(string title)
        {
            Lesson? lesson = null;

            using var conn = new MySqlConnection(_connectionString);
            conn.Open();

            // Lesson + Video
            using (var cmd = new MySqlCommand(
                @"SELECT l.*, v.*
                  FROM Lessons l
                  JOIN Videos v ON l.VideoId = v.Id
                  WHERE v.Title = @Title", conn))
            {
                cmd.Parameters.AddWithValue("@Title", title);

                using var reader = cmd.ExecuteReader();
                if (!reader.Read()) return null;

                lesson = MapLesson(reader);
                lesson.Video = MapVideo(reader);
            }

            // Slides
            lesson.Slides = GetSlidesForLesson(lesson.Id);
            return lesson;
        }

        public List<Video> GetPlannedVideos(bool value) => GetVideosByColumn("Planned", value);

        public List<Video> GetRecordedVideos(bool value) => GetVideosByColumn("Recorded", value);

        public List<Video> GetPostedVideos(bool value) => GetVideosByColumn("Posted", value);

        private List<Video> GetVideosByColumn(string column, bool value)
        {
            var videos = new List<Video>();

            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand(
                $"SELECT * FROM Videos WHERE {column} = @Value", conn);

            cmd.Parameters.AddWithValue("@Value", value);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                videos.Add(MapVideo(reader));
            }

            return videos;
        }


        // -------------------------
        // HELPERS
        // -------------------------
        private List<Slide> GetSlidesForLesson(int lessonId)
        {
            var slides = new List<Slide>();
            using var conn = new MySqlConnection(_connectionString);
            using var cmd = new MySqlCommand("SELECT * FROM Slides WHERE LessonId = @LessonId", conn);
            cmd.Parameters.AddWithValue("@LessonId", lessonId);

            conn.Open();
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                slides.Add(MapSlide(reader));
            }

            return slides;
        }

        private static Video MapVideo(MySqlDataReader r)
        {
            return new Video
            {
                Id = (int)r["Id"],
                Planned = (bool)r["Planned"],
                Recorded = (bool)r["Recorded"],
                Posted = (bool)r["Posted"],
                Title = r["Title"].ToString() ?? String.Empty,
                Description = r["Description"] as string ?? String.Empty,
                Thumbnail = r["Thumbnail"] as byte[] ?? [],
            };
        }

        private static Lesson MapLesson(MySqlDataReader r)
        {
            return new Lesson
            {
                Id = (int)r["Id"],
                Topic = r["Topic"].ToString() ?? String.Empty,
                Summary = r["Summary"].ToString() ?? String.Empty,
                Intro = r["Intro"].ToString() ?? String.Empty,
                Module = (int)r["Module"],
                Sequence = (int)r["Sequence"]
            };
        }

        private static Slide MapSlide(MySqlDataReader r)
        {
            return new Slide
            {
                Id = (int)r["Id"],
                LessonId = (int)r["LessonId"],
                Title = r["Title"].ToString() ?? String.Empty,
                Content = r["Content"].ToString() ?? String.Empty,
                Graphic = r["Graphic"] as byte[] ?? [],
                Script = r["Script"].ToString() ?? String.Empty
            };
        }
    }
}
