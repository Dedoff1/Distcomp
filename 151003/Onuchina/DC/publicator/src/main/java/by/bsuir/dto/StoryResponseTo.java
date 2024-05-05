package by.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseTo {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Long tagId;
}
