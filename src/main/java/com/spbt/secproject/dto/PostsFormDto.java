package com.spbt.secproject.dto;

import com.spbt.secproject.entity.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

@Data
public class PostsFormDto {

    private Long Id;

    @NotBlank(message = "게시글 작성 시 제목은 필수로 입력 값 입니다.")
    @Length(min=2, max=50, message = "게시글 작성 시 제목 입력 길이는 최소 2자 최대 50자를 초과 할 수 없습니다.")
    private String postsTitle;

    @NotBlank(message = "게시글 작성 시 내용은 필수로 입력 값 입니다.")
    @Length(min=2, max=65535, message = "게시글 작성 시 내용 입력 길이는 최소 2자 이상 입력해주셔야 합니다.")
    private String postsContent;

    private String saveTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard() {
        return modelMapper.map(this, Board.class);
    }

    public static PostsFormDto createPosts(Board board) {
        return modelMapper.map(board, PostsFormDto.class);
    }

}
