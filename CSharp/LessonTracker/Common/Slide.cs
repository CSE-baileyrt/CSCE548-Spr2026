using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    public class Slide
    {
        public int Id { get; set; }
        public int LessonId { get; set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public byte[] Graphic { get; set; }
        public string Script { get; set; }
    }
}
