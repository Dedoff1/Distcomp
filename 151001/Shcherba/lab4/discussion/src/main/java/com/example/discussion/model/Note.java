package com.example.discussion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "tbl_note")
public class Note {
    @PrimaryKeyColumn(name = "country", type = PrimaryKeyType.PARTITIONED)
    String country;

    @PrimaryKeyColumn(name = "tweetid", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    int tweetId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    int id;

    String content;
}
