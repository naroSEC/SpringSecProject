package com.spbt.secproject.dto;

import com.spbt.secproject.entity.ProfileImg;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ProfileImgFormDto {

    private Long Id;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProfileImgFormDto createProfileDto(ProfileImg profileImg) {
        return modelMapper.map(profileImg, ProfileImgFormDto.class);
    }
}
