﻿using AutoMapper;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;

namespace Publisher.Entity.Common
{
    public class AutoMapper : Profile
    {
        public AutoMapper()
        {
            CreateMap<AuthorRequestTO, Author>();
            CreateMap<Author, AuthorResponseTO>();

            CreateMap<LabelRequestTO, Label>();
            CreateMap<Label, LabelResponseTO>();

            CreateMap<NewsRequestTO, News>();
            CreateMap<News, NewsResponseTO>();
        }
    }
}
