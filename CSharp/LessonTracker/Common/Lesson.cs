using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    public class Lesson
    {
        public int Id { get; set; }
        public string Topic { get; set; }
        public string Summary { get; set; }
        public string Intro { get; set; }
        public int Module { get; set; }
        public int Sequence { get; set; }

        public Video Video { get; set; }
        public List<Slide> Slides { get; set; } = new List<Slide>();
    }
}
