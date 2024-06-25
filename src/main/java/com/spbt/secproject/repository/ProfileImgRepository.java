package com.spbt.secproject.repository;

import com.spbt.secproject.entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

    ProfileImg findByAccountId(Long id);

}
