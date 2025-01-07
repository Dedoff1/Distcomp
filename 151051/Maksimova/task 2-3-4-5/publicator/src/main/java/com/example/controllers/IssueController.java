package com.example.controllers;

import com.example.dto.IssueRequestTo;
import com.example.dto.IssueResponseTo;
import com.example.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueResponseTo>> getIssues(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssues(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseTo> getIssue(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueById((long) id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<IssueResponseTo> saveIssue(@RequestBody IssueRequestTo issue) {
        IssueResponseTo savedIssue = issueService.saveIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    @PutMapping
    public ResponseEntity<IssueResponseTo> updateIssue(@RequestBody IssueRequestTo issue) {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issue));
    }

    @GetMapping("/byCriteria")
    public ResponseEntity<List<IssueResponseTo>> getIssueByCriteria(
            @RequestParam(required = false) List<String> stickerName,
            @RequestParam(required = false) List<Long> stickerId,
            @RequestParam(required = false) String editorLogin,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content) {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueByCriteria(stickerName, stickerId, editorLogin, title, content));
    }
}
