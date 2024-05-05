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


        public virtual DbSet<Author> Authors { get; set; }

        public virtual DbSet<Post> Posts { get; set; }

        public virtual DbSet<Label> Labels { get; set; }

        public virtual DbSet<Tweet> Tweets { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Author>(entity =>
            {
                entity.ToTable("tbl_author");
                entity.HasIndex(e => e.Login, "IX_Authors_login").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Firstname).HasColumnName("firstname");
                entity.Property(e => e.Lastname).HasColumnName("lastname");
                entity.Property(e => e.Login).HasColumnName("login");
                entity.Property(e => e.Password).HasColumnName("password");
            });

            modelBuilder.Entity<Post>(entity =>
            {
                entity.ToTable("tbl_post");
                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.tweetId).HasColumnName("tweetId");

                entity.HasOne(d => d.Tweet).WithMany(p => p.Posts).HasForeignKey(d => d.tweetId);
            });

            modelBuilder.Entity<Label>(entity =>
            {
                entity.ToTable("tbl_label");
                entity.HasIndex(e => e.Id, "IX_Labels_id").IsUnique();

                entity.HasIndex(e => e.name, "IX_Labels_name").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.name).HasColumnName("name");
            });

            modelBuilder.Entity<Tweet>(entity =>
            {
                entity.ToTable("tbl_tweet");
                entity.HasIndex(e => e.Title, "IX_Tweets_title").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Created).HasColumnName("created");
                entity.Property(e => e.Modified).HasColumnName("modified");
                entity.Property(e => e.Title).HasColumnName("title");
                entity.Property(e => e.authorId).HasColumnName("author_id");

                entity.HasOne(d => d.Author).WithMany(p => p.Tweets).HasForeignKey(d => d.authorId);
            });


            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
