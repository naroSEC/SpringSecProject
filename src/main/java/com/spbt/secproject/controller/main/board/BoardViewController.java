package com.spbt.secproject.controller.main.board;

import com.spbt.secproject.dto.*;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.entity.Board;
import com.spbt.secproject.entity.Comment;
import com.spbt.secproject.service.BoardService;
import com.spbt.secproject.service.CustomUserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping(value = "/main/board/list")
    public String boardList(Model model) {
        List<BoardPostsFormDto> boardPostsFormDtos = boardService.getAllBoard();

        model.addAttribute("boardPostsFormDtos", boardPostsFormDtos);

        return "/main/board/list";
    }

    @GetMapping(value = "/main/board/write")
    public String boardWrite(Model model) {
        model.addAttribute("postsFormDto", new PostsFormDto());

        return "/main/board/write";
    }

    @PostMapping(value = "/main/board/write")
    public ResponseEntity<Object> boardSave(@AuthenticationPrincipal User user, @Valid PostsFormDto postsFormDto, BindingResult bindingResult) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            hashMap.put("fail", errorMessage.toString());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        try {
            Account account = customUserDetailService.getAccount(user);
            boardService.saveBoard(postsFormDto, account);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "게시글이 등록되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    @GetMapping(value = "/main/board/view/{boardId}")
    public String boardView(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId, Model model) {
        try {
            ModelMapper modelMapper = new ModelMapper();

            Account account = customUserDetailService.getAccount(user);
            AccountFormDto currentUser = modelMapper.map(account, AccountFormDto.class);

            Board board = boardService.getBoard(boardId);
            PostsFormDto postsFormDto = PostsFormDto.createPosts(board);
            AccountFormDto accountFormDto = modelMapper.map(board.getAccount(), AccountFormDto.class);

            List<BoardCommentFormDto> boardCommentFormDtos = boardService.getAllComment(boardId);
            if (boardCommentFormDtos != null) {
                model.addAttribute("boardCommentFormDtos", boardCommentFormDtos);
            }

            model.addAttribute("postsFormDto", postsFormDto);
            model.addAttribute("accountFormDto", accountFormDto);
            model.addAttribute("currentUser", currentUser);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return "redirect:/main/board/list";
        }

        return "/main/board/view";
    }

    @GetMapping(value = "/main/board/modify/{boardId}")
    public String boardModify(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.getBoard(boardId);
        PostsFormDto postsFormDto = PostsFormDto.createPosts(board);
        model.addAttribute("postsFormDto", postsFormDto);

        return "/main/board/write";
    }

    @PostMapping(value = "/main/board/modify")
    public ResponseEntity<Object> boardModify(@AuthenticationPrincipal User user, @Valid PostsFormDto postsFormDto, BindingResult bindingResult) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            hashMap.put("fail", errorMessage.toString());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        try {
            Account account = customUserDetailService.getAccount(user);
            boardService.modifyBoard(postsFormDto, account);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "게시글이 수정되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }



    @GetMapping(value = "/main/board/del/{boardId}")
    public ResponseEntity<Object> boardDel(@PathVariable("boardId") Long boardId) {
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            boardService.delBoard(boardId);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "게시글이 삭제되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    @PostMapping(value = "/main/board/comment")
    public ResponseEntity<Object> commentWrite(@AuthenticationPrincipal User user, @Valid CommentFormDto commentFormDto, BindingResult bindingResult) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            hashMap.put("fail", errorMessage.toString());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        try {
            Account account = customUserDetailService.getAccount(user);
            Board board = boardService.getBoard(commentFormDto.getBoardId());
            boardService.saveComment(commentFormDto, board, account);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "댓글이 등록되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    @PostMapping(value = "/main/board/comment/modify")
    public ResponseEntity<Object> commentModify(@AuthenticationPrincipal User user,
                                                @Valid CommentFormDto commentFormDto,
                                                BindingResult bindingResult)
    {
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            Comment comment = boardService.getComment(commentFormDto);
            customUserDetailService.validateAllowUser(comment.getAccount().getUserid(), user);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            hashMap.put("fail", errorMessage.toString());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        try {
            boardService.modifyComment(commentFormDto);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "댓글이 수정되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    @PostMapping(value = "/main/board/comment/del")
    public ResponseEntity<Object> commentDel(CommentFormDto commentFormDto) {
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            boardService.delComment(commentFormDto);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "댓글이 삭제되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }
}
