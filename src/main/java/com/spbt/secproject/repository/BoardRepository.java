package com.spbt.secproject.repository;

import com.spbt.secproject.dto.BoardCommentFormDto;
import com.spbt.secproject.dto.BoardPostsFormDto;
import com.spbt.secproject.entity.Board;
import com.spbt.secproject.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT new com.spbt.secproject.dto.BoardPostsFormDto(bd.Id, bd.postsTitle, acc.username, acc.role, bd.saveTime) " +
            "FROM Board bd " +
            "INNER JOIN bd.account acc")
    List<BoardPostsFormDto> findBoardPostsList();

    @Query("SELECT count(bd) " +
            "FROM Board bd " +
            "WHERE bd.account.id = :accountId")
    Long findUserWriteTotalBoard(@Param("accountId") Long accountId);

}
