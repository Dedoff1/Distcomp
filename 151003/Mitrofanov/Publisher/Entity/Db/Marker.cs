﻿using Publisher.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Entity.Db
{
    public class Marker(string name) : AbstractEntity
    {
        [MinLength(2)]
        public string Name { get; set; } = name;
        public ICollection<Story> Storys { get; set; } = [];

        public Marker() : this(string.Empty) { }
        public Marker(int id, string name) : this(name)
        {
            Id = id;
        }
    }
}
