package by.bsuir.egor.repositories;

import by.bsuir.egor.Entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue,Long> {

    Issue findById(long id);

    List<Issue> findAll();

    Issue save(Issue issue);

    void deleteById(long id);

}
