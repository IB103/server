package com.hansung.capstone.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRidingDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordDTO {
        private Long ridingTime;
        private Float ridingDistance;
        private int calorie;
        private Long userId;
    }

    @Builder
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class HistoryResponseDTO{

        private Long ridingTime;
        private Float ridingDistance;
        private int calorie;
    }
}
