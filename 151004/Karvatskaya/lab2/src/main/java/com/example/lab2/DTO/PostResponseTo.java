package com.example.lab2.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostResponseTo {
    int id;
    int issueId;
    @Size(min = 2, max = 2048)
    String content;
}
