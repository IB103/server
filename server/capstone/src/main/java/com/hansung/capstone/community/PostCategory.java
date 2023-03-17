package com.hansung.capstone.community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostCategory {
    Free("1","자유 게시판");

    private final String key;
    private final String category;
}
