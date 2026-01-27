using System;
using System.Collections.Generic;
using System.Data;
//using System.Data.SqlClient;
using MySql.Data.MySqlClient;

namespace Common
{
    /*
     * PROBLEMS:
     * #1: I prompted ChatGPT to replace the SQL Server code with MySQL
     * It only replaced a handful of methods below
     * 
     * #2: Within the handful of methods, there are errors
     * The unaltered methods all have errors
     */

    public class DataManagerV2
    {
        private readonly string _connectionString;

        public DataManagerV2(string connectionString)
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

        //public Video GetVideoById(int id)
        //{
        //    using var conn = new MySqlConnection(_connectionString);
        //    using var cmd = new MySqlCommand("SELECT * FROM Videos WHERE Id = @Id", conn);
        //    cmd.Parameters.AddWithValue("@Id", id);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    return reader.Read() ? MapVideo(reader) : null;
        //}

        //public void UpdateVideo(Video video)
        //{
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand(
        //        @"UPDATE Videos SET
        //        Planned = @Planned,
        //        Recorded = @Recorded,
        //        Posted = @Posted,
        //        Title = @Title,
        //        Description = @Description,
        //        Thumbnail = @Thumbnail
        //      WHERE Id = @Id", conn);

        //    cmd.Parameters.AddWithValue("@Id", video.Id);
        //    cmd.Parameters.AddWithValue("@Planned", video.Planned);
        //    cmd.Parameters.AddWithValue("@Recorded", video.Recorded);
        //    cmd.Parameters.AddWithValue("@Posted", video.Posted);
        //    cmd.Parameters.AddWithValue("@Title", video.Title);
        //    cmd.Parameters.AddWithValue("@Description", (object)video.Description ?? DBNull.Value);
        //    cmd.Parameters.AddWithValue("@Thumbnail", (object)video.Thumbnail ?? DBNull.Value);

        //    conn.Open();
        //    cmd.ExecuteNonQuery();
        //}

        //public void DeleteVideo(int id)
        //{
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand("DELETE FROM Videos WHERE Id = @Id", conn);
        //    cmd.Parameters.AddWithValue("@Id", id);

        //    conn.Open();
        //    cmd.ExecuteNonQuery();
        //}

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

        //public Lesson GetLessonById(int id)
        //{
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand("SELECT * FROM Lessons WHERE Id = @Id", conn);
        //    cmd.Parameters.AddWithValue("@Id", id);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    return reader.Read() ? MapLesson(reader) : null;
        //}

        //public void DeleteLesson(int id)
        //{
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand("DELETE FROM Lessons WHERE Id = @Id", conn);
        //    cmd.Parameters.AddWithValue("@Id", id);

        //    conn.Open();
        //    cmd.ExecuteNonQuery();
        //}

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

        //public Slide GetSlideById(int id)
        //{
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand("SELECT * FROM Slides WHERE Id = @Id", conn);
        //    cmd.Parameters.AddWithValue("@Id", id);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    return reader.Read() ? MapSlide(reader) : null;
        //}

        //public List<Slide> GetSlidesForVideo(int videoId)
        //{
        //    var slides = new List<Slide>();

        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand(
        //        @"SELECT s.*
        //      FROM Slides s
        //      JOIN Lessons l ON s.LessonId = l.Id
        //      WHERE l.VideoId = @VideoId", conn);

        //    cmd.Parameters.AddWithValue("@VideoId", videoId);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    while (reader.Read())
        //    {
        //        slides.Add(MapSlide(reader));
        //    }

        //    return slides;
        //}

        // -------------------------
        // COMPLEX QUERIES
        // -------------------------
        //public Lesson GetLessonByVideoTitle(string title)
        //{
        //    Lesson lesson = null;

        //    using var conn = new SqlConnection(_connectionString);
        //    conn.Open();

        //    // Lesson + Video
        //    using (var cmd = new SqlCommand(
        //        @"SELECT l.*, v.*
        //      FROM Lessons l
        //      JOIN Videos v ON l.VideoId = v.Id
        //      WHERE v.Title = @Title", conn))
        //    {
        //        cmd.Parameters.AddWithValue("@Title", title);

        //        using var reader = cmd.ExecuteReader();
        //        if (!reader.Read()) return null;

        //        lesson = MapLesson(reader);
        //        lesson.Video = MapVideo(reader);
        //    }

        //    // Slides
        //    lesson.Slides = GetSlidesForLesson(lesson.Id);
        //    return lesson;
        //}

        //public List<Video> GetPlannedVideos(bool value)
        //    => GetVideosByColumn("Planned", value);

        //public List<Video> GetRecordedVideos(bool value)
        //    => GetVideosByColumn("Recorded", value);

        //public List<Video> GetPostedVideos(bool value)
        //    => GetVideosByColumn("Posted", value);

        //private List<Video> GetVideosByColumn(string column, bool value)
        //{
        //    var videos = new List<Video>();

        //    using var conn = new MySqlConnection(_connectionString);
        //    using var cmd = new MySqlCommand(
        //        $"SELECT * FROM Videos WHERE {column} = @Value", conn);

        //    cmd.Parameters.AddWithValue("@Value", value);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    while (reader.Read())
        //    {
        //        videos.Add(MapVideo(reader));
        //    }

        //    return videos;
        //}


        // -------------------------
        // HELPERS
        // -------------------------
        //private List<Slide> GetSlidesForLesson(int lessonId)
        //{
        //    var slides = new List<Slide>();
        //    using var conn = new SqlConnection(_connectionString);
        //    using var cmd = new SqlCommand("SELECT * FROM Slides WHERE LessonId = @LessonId", conn);
        //    cmd.Parameters.AddWithValue("@LessonId", lessonId);

        //    conn.Open();
        //    using var reader = cmd.ExecuteReader();
        //    while (reader.Read())
        //    {
        //        slides.Add(MapSlide(reader));
        //    }

        //    return slides;
        //}

        //private Video MapVideo(SqlDataReader r) => new Video
        //{
        //    Id = (int)r["Id"],
        //    Planned = (bool)r["Planned"],
        //    Recorded = (bool)r["Recorded"],
        //    Posted = (bool)r["Posted"],
        //    Title = r["Title"].ToString(),
        //    Description = r["Description"] as string,
        //    Thumbnail = r["Thumbnail"] as byte[]
        //};

        //private Lesson MapLesson(SqlDataReader r) => new Lesson
        //{
        //    Id = (int)r["Id"],
        //    Topic = r["Topic"].ToString(),
        //    Summary = r["Summary"].ToString(),
        //    Intro = r["Intro"].ToString(),
        //    Module = (int)r["Module"],
        //    Sequence = (int)r["Sequence"]
        //};

        //private Slide MapSlide(SqlDataReader r) => new Slide
        //{
        //    Id = (int)r["Id"],
        //    LessonId = (int)r["LessonId"],
        //    Title = r["Title"].ToString(),
        //    Content = r["Content"].ToString(),
        //    Graphic = r["Graphic"] as byte[],
        //    Script = r["Script"].ToString()
        //};
    }

}
