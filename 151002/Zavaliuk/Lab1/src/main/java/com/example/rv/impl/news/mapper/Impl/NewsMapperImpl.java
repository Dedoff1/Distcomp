package com.example.rv.impl.news.mapper.Impl;

import com.example.rv.impl.editor.Editor;
import com.example.rv.impl.news.News;
import com.example.rv.impl.news.dto.NewsRequestTo;
import com.example.rv.impl.news.dto.NewsResponseTo;
import com.example.rv.impl.news.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NewsMapperImpl implements NewsMapper {
    @Override
    public NewsRequestTo newsToRequestTo(News news) {
        return new NewsRequestTo(
                news.getId(),
                news.getEditor().getId(),
                news.getTitle(),
                news.getContent(),
                news.getCreated(),
                news.getModified()
        );
    }

    @Override
    public List<NewsRequestTo> newsToRequestTo(Iterable<News> news) {
        return StreamSupport.stream(news.spliterator(), false)
                .map(this::newsToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public News dtoToEntity(NewsRequestTo newsRequestTo, Editor editor) {
        return new News(
                newsRequestTo.getId(),
                editor,
                newsRequestTo.getTitle(),
                newsRequestTo.getContent(),
                newsRequestTo.getModified(),
                newsRequestTo.getCreated());
    }

    @Override
    public NewsResponseTo newsToResponseTo(News news) {
        return new NewsResponseTo(
                news.getId(),
                news.getEditor().getId(),
                news.getTitle(),
                news.getContent(),
                news.getCreated(),
                news.getModified()
        );
    }

    @Override
    public List<NewsResponseTo> newsToResponseTo(Iterable<News> news) {
        return StreamSupport.stream(news.spliterator(), false)
                .map(this::newsToResponseTo)
                .collect(Collectors.toList());
    }
}
