package com.example.rv.impl.news;

import com.example.rv.impl.editor.Editor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "tbl_news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigInteger id;

    @JoinColumn(name = "editor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Editor editor;

    @Column(name = "title", unique = true)
    @Length(min = 2, max = 64)
    String title;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "created")
    private Date created;
}
