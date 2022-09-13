package com.example.security.service.impl;

import com.example.security.model.Comment;
import com.example.security.repository.CommentRespository;
import com.example.security.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements ICommentService {
    private CommentRespository comments;

    private CommentService (CommentRespository comments) {
        super();
        this.comments = comments;
    }
    @Override
    public Comment findCommentById(Long id) {
        try{
            return this.comments.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Comment> findAllComments() {
        return this.comments.findAll();
    }

    @Override
    public Comment createComment(Comment comment) {
        return this.comments.save(comment);
    }

    @Override
    public boolean deleteComment(long id) {
        Comment comment = this.findCommentById(id);

        try {
            this.comments.delete(comment);
        }
        catch (Exception e){
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public Comment updateComment(long id, Comment comment) {
       Comment oldComment = this.findCommentById(id);
        if (oldComment == null) {
            return null;
        } else {
            if (comment.getComment() != null) {
                oldComment.setComment(comment.getComment());
            }
            return this.comments.save(oldComment);
        }
    }
}
