﻿using Publisher.Entity.Db;
using Publisher.Repository.Interface.Common;

namespace Publisher.Repository.Interface
{
    public interface IAuthorRepository : ICrudRepository<Author>
    {
        News GetByNewsId(int newsId);
        Task<News> GetByNewsIdAsync(int newsId);
    }
}
