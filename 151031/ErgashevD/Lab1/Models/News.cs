﻿namespace Lab1.Models
{
    public class News : BaseModel
    {
        public long CreatorId { get; set; }

        public Creator Creator { get; set; }

        public string Title { get; set; }

        public string Content { get; set; }

        public DateTime Created { get; set; }

        public DateTime Modified { get; set; }
    }
}
