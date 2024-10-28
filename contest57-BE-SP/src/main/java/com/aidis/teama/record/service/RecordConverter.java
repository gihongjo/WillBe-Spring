package com.aidis.teama.record.service;

import com.aidis.teama.record.db.RecordEntity;
import com.aidis.teama.record.model.GraphDailyDTO;
import com.aidis.teama.record.model.RecordLogsDTO;
import com.aidis.teama.record.model.TimeStampsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecordConverter {



    public RecordLogsDTO recordEntityToDTO(RecordEntity recordEntity){



        return RecordLogsDTO.builder()
                .behaviorName(recordEntity.getBehaviorEntity().getBehaviorName())
                .recordId(recordEntity.getId())
                .StudentName(recordEntity.getBehaviorEntity().getStudentEntity().getStudent_name())
                .timestamp(Timestamp.valueOf(recordEntity.getTime()).toLocalDateTime())
                .build();

    }


    public static GraphDailyDTO convertToGraphDailyDTO(List<RecordEntity> recordEntities) {
        // 24개의 0으로 초기화된 그래프 리스트 생성
        List<Integer> graph = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            graph.add(0);
        }

        // 각 기록 엔터티의 시간(time)을 기준으로 해당 시간대에 몇 개의 기록이 있는지 계산
        for (RecordEntity record : recordEntities) {
            LocalDateTime time = record.getTime();
            int hour = time.getHour(); // 기록의 시간대 (0~23)
            graph.set(hour, graph.get(hour) + 1);
        }

        // GraphDailyDTO 생성 및 반환
        GraphDailyDTO graphDailyDTO = GraphDailyDTO.builder()
                .graph(graph)
                .build();
        return graphDailyDTO;
    }

    public TimeStampsDTO recordEntityToTimestamps(List<RecordEntity> recordEntityList) {
        // RecordEntity 리스트에서 LocalDateTime 리스트로 변환
        List<LocalDateTime> timestamps = recordEntityList.stream()
                .map(RecordEntity::getTime) // 각 RecordEntity의 time 필드 추출
                .collect(Collectors.toList());

        // TimeStampsDTO에 변환된 timestamps를 설정하여 반환
        return TimeStampsDTO.builder()
                .timestampes(timestamps)
                .build();
    }


}
