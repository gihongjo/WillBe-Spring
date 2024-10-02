package com.aidis.teama.record.service;

import com.aidis.teama.record.db.RecordEntity;
import com.aidis.teama.record.model.GraphDailyDTO;
import com.aidis.teama.record.model.RecordLogsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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


    public GraphDailyDTO createGraphDailyDTO(String behaviorId, String behaviorName, List<Integer> graph){

        return GraphDailyDTO.builder()
                .behaviorId(behaviorId)
                .behaviorName(behaviorName)
                .graph(graph)
                .build();

    }
}
