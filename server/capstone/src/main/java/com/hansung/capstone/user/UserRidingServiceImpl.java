package com.hansung.capstone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRidingServiceImpl implements UserRidingService{

    private final UserRepository userRepository;

    private final UserRidingRepository userRidingRepository;

    @Override
    public void record(UserRidingDTO.RecordDTO req) {
        User user = this.userRepository.findById(req.getUserId()).orElseThrow(
                () -> new RuntimeException("유저가 존재하지 않습니다.")
        );
        UserRiding userRiding = UserRiding.builder()
                .ridingTime(req.getRidingTime())
                .ridingDistance(req.getRidingDistance())
                .calorie(req.getCalorie())
                .user(user).build();

        this.userRidingRepository.save(userRiding);
    }

    @Override
    public List<UserRidingDTO.HistoryResponseDTO> getHistory(Long userId, Long period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowMinusPeriod = now.minusDays(period);
        List<UserRiding> db = this.userRidingRepository.findAllByPeriod(userId, now, nowMinusPeriod);
        List<UserRidingDTO.HistoryResponseDTO> res = new ArrayList<>();
        for(UserRiding inDB : db){
            UserRidingDTO.HistoryResponseDTO dto = UserRidingDTO.HistoryResponseDTO.builder()
                    .createdDate(inDB.getCreatedDate())
                    .ridingTime(inDB.getRidingTime())
                    .ridingDistance(inDB.getRidingDistance())
                    .calorie(inDB.getCalorie()).build();
            res.add(dto);
        }
        return res;
    }
}
