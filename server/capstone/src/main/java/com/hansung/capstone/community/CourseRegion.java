package com.hansung.capstone.community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CourseRegion {

    서울특별시,
    부산광역시,
    대구광역시,
    인천광역시,
    광주광역시,
    대전광역시,
    울산광역시,
    세종특별자치시,
    경기도,
    강원도,
    충청북도,
    충청남도,
    전라북도,
    전라남도,
    경상북도,
    경상남도,
    제주특별자치도;

}
