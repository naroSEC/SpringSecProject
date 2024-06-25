package com.spbt.secproject.repository;

import com.spbt.secproject.dto.BoardCommentFormDto;
import com.spbt.secproject.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long boardId);

    @Query("SELECT new com.spbt.secproject.dto.BoardCommentFormDto(cmt.Id, acc.userid, acc.username, cmt.commentText, cmt.saveTime) " +
            "FROM Comment cmt " +
            "LEFT JOIN cmt.account acc " +
            "WHERE cmt.board.id = :boardId")
    List<BoardCommentFormDto> findBoardAllCommentList(@Param("boardId") Long boardId);

    @Query("SELECT cmt " +
            "FROM Comment cmt " +
            "WHERE cmt.board.Id = :boardId AND cmt.Id = :commentId")
    Comment findBoardGetComment(@Param("boardId") Long boardId, @Param("commentId") Long commentId);

    @Query("SELECT count(cmt) " +
            "FROM Comment cmt " +
            "WHERE cmt.account.id = :accountId")
    Long findUserWriteTotalComment(@Param("accountId") Long accountId);
}
