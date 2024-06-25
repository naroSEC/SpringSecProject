package com.spbt.secproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentFormDto {
    private Long id;

    @NotBlank(message = "댓글 작성 시 내용은 필수로 입력 값 입니다.")
    @Length(min=2, max=255, message = "댓글 작성 시 내용 입력 길이는 최소 2자 최대 255자를 초과 할 수 없습니다.")
    private String commentText;

    @NotNull(message = "게시판 번호는 필수 입력 값 입니다.")
    private Long boardId;

}
