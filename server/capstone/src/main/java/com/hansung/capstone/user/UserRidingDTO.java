package com.hansung.capstone.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

        private LocalDateTime createdDate;
        private Long ridingTime;
        private Float ridingDistance;
        private int calorie;
    }
}
