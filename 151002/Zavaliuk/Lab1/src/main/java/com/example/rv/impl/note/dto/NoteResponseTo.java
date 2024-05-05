package com.example.rv.impl.note.dto;

import java.math.BigInteger;

public record NoteResponseTo(
        BigInteger id,
        BigInteger newsId,
        String content
) {
}
