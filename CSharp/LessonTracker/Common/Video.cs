using System;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    public class Video
    {
        public int Id { get; set; }
        public bool Planned { get; set; }
        public bool Recorded { get; set; }
        public bool Posted { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public byte[] Thumbnail { get; set; } // stored as VARBINARY in DB
    }
}
