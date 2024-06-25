package com.spbt.secproject.entity;

import com.spbt.secproject.dto.PostsFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="board")
@Entity
@Getter
@Setter
public class Board {

    @Id
    @Column(name="board_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String postsTitle;

    @Column(length = 65535)
    private String postsContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    private Account account;

    private String saveTime;

    public void saveAccount(Account account) {
        this.account = account;
    }

    public void saveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public void updatePosts(PostsFormDto postsFormDto, Account account, String saveTime) {
        this.postsTitle = postsFormDto.getPostsTitle();
        this.postsContent = postsFormDto.getPostsContent();
        this.account = account;
        this.saveTime = saveTime;
    }
}
