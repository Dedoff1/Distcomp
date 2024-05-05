package com.example.rv.impl.note.mapper;

import com.example.rv.impl.note.Note;
import com.example.rv.impl.note.dto.NoteRequestTo;
import com.example.rv.impl.note.dto.NoteResponseTo;
import com.example.rv.impl.news.News;

import java.util.List;

public interface NoteMapper {
    NoteRequestTo noteToRequestTo(Note note);

    List<NoteRequestTo> noteToRequestTo(Iterable<Note> notes);

    Note dtoToEntity(NoteRequestTo noteRequestTo, News news);

    NoteResponseTo noteToResponseTo(Note note);

    List<NoteResponseTo> noteToResponseTo(Iterable<Note> notes);
}
