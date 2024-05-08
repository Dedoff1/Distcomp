﻿using DC_Lab1.Models;
using Microsoft.EntityFrameworkCore;

namespace DC_Lab1.DB.BaseDBContext
{
    public partial class BaseContext : DbContext
    {
        public BaseContext()
        {

            Database.EnsureCreated();
        }

        public BaseContext(DbContextOptions<BaseContext> options)
            : base(options)
        {
            Database.EnsureCreated();
        }

        public virtual DbSet<Post> Posts { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Post>(entity =>
            {
                entity.ToTable("tbl_post");
                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.tweetId).HasColumnName("tweetId");
            });


            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
