package by.bsuir.publisher.services;

import by.bsuir.publisher.dto.requests.CommentRequestDto;
import by.bsuir.publisher.dto.responses.CommentResponseDto;
import by.bsuir.publisher.exceptions.ServiceException;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentResponseDto create(@Valid @NonNull CommentRequestDto dto) throws ServiceException;
    Optional<CommentResponseDto> read(@NonNull Long uuid);
    List<CommentResponseDto> readAll();
    CommentResponseDto update(@Valid @NonNull CommentRequestDto dto) throws ServiceException;
    Long delete(@NonNull Long uuid) throws ServiceException;
}
