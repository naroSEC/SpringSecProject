package com.spbt.secproject.entity;

import com.spbt.secproject.constant.AccountRole;
import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.dto.CommentFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Table(name="comment")
@Entity
@Getter
@Setter
public class Comment {

    @jakarta.persistence.Id
    @Column(name="comment_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String commentText;

    private String saveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    public static Comment createComment(CommentFormDto commentFormDto, Board board, Account account) {
        Comment comment = new Comment();
        comment.setCommentText(commentFormDto.getCommentText());
        comment.setBoard(board);
        comment.setAccount(account);
        comment.setSaveTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        return comment;
    }

    public void updateComment(CommentFormDto commentFormDto) {
        this.commentText = commentFormDto.getCommentText();
        this.saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
