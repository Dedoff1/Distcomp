package by.bsuir.repository;

import by.bsuir.entities.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT i FROM Issue i " +
            "LEFT JOIN i.tags l " +
            "WHERE (:tagNames IS NULL OR l.name IN :tagNames) " +
            "AND (:tagIds IS NULL OR l.id IN :tagIds) " +
            "AND (:editorLogin IS NULL OR i.editor.login = :editorLogin) " +
            "AND (:title IS NULL OR i.title LIKE %:title%) " +
            "AND (:content IS NULL OR i.content LIKE %:content%)")
    List<Issue> findIssuesByParams(@Param("tagNames") List<String> tagNames,
                                   @Param("tagIds") List<Long> tagIds,
                                   @Param("editorLogin") String editorLogin,
                                   @Param("title") String title,
                                   @Param("content") String content);

    Page<Issue> findAll(Pageable pageable);
}
