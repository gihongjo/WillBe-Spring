package org.willbemsa.willbelogging.record.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.willbemsa.willbelogging.record.db.RecordEntity;
import org.willbemsa.willbelogging.record.model.GraphDailyDTO;
import org.willbemsa.willbelogging.record.model.RecordLogsDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

}
