package com.example.security.controller;


import com.example.security.model.Comment;
import com.example.security.service.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        super();
        this.commentService = commentService;
    }

    @GetMapping("/api/comments")
    public List<Comment> findAll () {
        List<Comment> comments = this.commentService.findAllComments();
        return comments;
    }

    @GetMapping("/api/comments/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        Comment comment = this.commentService.findCommentById(id);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment with id " + id + " not found");
        }
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result = this.commentService.deleteComment(id);
        if (result) {
            return ResponseEntity.ok("Comment with id " + id + " deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment with id " + id + " not found");
    }

    @PostMapping ("/api/comments")
    public ResponseEntity<Comment> create (@RequestBody Comment comment) {
        Comment newComment = this.commentService.createComment(comment);
        return ResponseEntity.ok(newComment);
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody Comment comment) {
        Comment updatedComment = this.commentService.updateComment(id, comment);
        if (updatedComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedComment);
    }
}