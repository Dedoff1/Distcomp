﻿using Publisher.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Entity.Db
{
    public class Story(string title, string content, DateTime created, DateTime modified) : AbstractEntity
    {
        [MinLength(2)]
        public string Title { get; set; } = title;
        [MinLength(4)]
        public string Content { get; set; } = content;
        public DateTime Created { get; set; } = created;
        public DateTime Modified { get; set; } = modified;
        public ICollection<Marker> Markers { get; set; } = [];
        public int EditorId { get; set; }
        public Editor Editor { get; set; } = null!;

        public Story() : this(string.Empty, string.Empty, DateTime.Now, DateTime.Now) { }
        public Story(int id, int editorId, string title, string content, DateTime created, DateTime modified)
            : this(title, content, created, modified)
        {
            Id = id;
            EditorId = editorId;
            Editor = new() { Id = editorId };
        }
    }
}
