package org.education.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.education.bean.Creator;
import org.education.bean.DTO.CreatorRequestTo;
import org.education.bean.DTO.CreatorResponseTo;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchCreator;
import org.education.service.CreatorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/creators")
@Tag(name = "Авторы")
public class CreatorController {
    private final CreatorService creatorService;
    private final ModelMapper modelMapper;

    public CreatorController(CreatorService creatorService, ModelMapper modelMapper) {
        this.creatorService = creatorService;
        this.modelMapper = modelMapper;
    }


    @Operation(summary = "Получение всех авторов")
    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getAllCreators(){
        return new ResponseEntity<>(creatorService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создание автора")
    public ResponseEntity<CreatorResponseTo> createCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        Creator newCreator = modelMapper.map(creatorRequestTo, Creator.class);
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(creatorService.create(newCreator), HttpStatus.valueOf(201));
    }


    @PutMapping
    @Operation(summary = "Обновление автора")
    public ResponseEntity<CreatorResponseTo> updateCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(creatorService.update(modelMapper.map(creatorRequestTo, Creator.class)), HttpStatus.valueOf(200));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удаления автора")
    public ResponseEntity<HttpStatus> deleteCreator(@PathVariable int id){
        creatorService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получение автора по id")
    public ResponseEntity<CreatorResponseTo> getCreator(@PathVariable int id){
        return new ResponseEntity<>(creatorService.getById(id), HttpStatus.valueOf(200));
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchCreator exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(AlreadyExists exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 403),HttpStatus.valueOf(403));
    }
}
