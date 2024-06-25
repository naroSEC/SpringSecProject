package com.spbt.secproject.service;

import com.spbt.secproject.dto.BoardCommentFormDto;
import com.spbt.secproject.dto.BoardPostsFormDto;
import com.spbt.secproject.dto.CommentFormDto;
import com.spbt.secproject.dto.PostsFormDto;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.entity.Board;
import com.spbt.secproject.entity.Comment;
import com.spbt.secproject.repository.BoardRepository;
import com.spbt.secproject.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public List<BoardPostsFormDto> getAllBoard() {
        return boardRepository.findBoardPostsList();
    }

    public Long getUserWriteTotalBoard(Long accountId) {
        Long totalWriteBoard = boardRepository.findUserWriteTotalBoard(accountId);
        if (totalWriteBoard == null) {
            totalWriteBoard = 0L;
        }

        return totalWriteBoard;
    }

    public Board getBoard(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 정보를 가져오지 못 했습니다."));
    }

    public void saveBoard(PostsFormDto postsFormDto, Account account) {
        Board board = postsFormDto.createBoard();
        board.saveAccount(account);
        board.saveTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        boardRepository.save(board);
    }

    public void modifyBoard(PostsFormDto postsFormDto, Account account) {
        Board board = getBoard(postsFormDto.getId());
        String modifyTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        board.updatePosts(postsFormDto, account, modifyTime);
    }

    public void delBoard(Long boardId) {
        Board board = getBoard(boardId);
        boardRepository.delete(board);
    }

    public Long getUserWriteTotalComment(Long accounId) {
        Long totalWriteComment = commentRepository.findUserWriteTotalComment(accounId);
        if (totalWriteComment == null) {
            totalWriteComment = 0L;
        }

        return totalWriteComment;
    }

    public List<BoardCommentFormDto> getAllComment(Long boardId) {
        return commentRepository.findBoardAllCommentList(boardId);
    }

    public Comment getComment(CommentFormDto commentFormDto) {
        Comment comment = commentRepository.findBoardGetComment(commentFormDto.getBoardId(), commentFormDto.getId());
        if (comment == null) {
            throw new IllegalStateException("댓글 정보가 존재하지 않습니다.");
        }

        return comment;
    }

    public void saveComment(CommentFormDto commentFormDto, Board board, Account account) {
        Comment comment = Comment.createComment(commentFormDto, board, account);
        commentRepository.save(comment);
    }

    public void modifyComment(CommentFormDto commentFormDto) {
        Comment comment = getComment(commentFormDto);
        comment.updateComment(commentFormDto);
    }

    public void delComment(CommentFormDto commentFormDto) {
        Comment comment = getComment(commentFormDto);
        commentRepository.delete(comment);
    }
}
