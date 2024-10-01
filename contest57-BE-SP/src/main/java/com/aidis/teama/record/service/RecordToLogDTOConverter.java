package com.aidis.teama.record.service;

import com.aidis.teama.record.db.RecordEntity;
import com.aidis.teama.record.model.RecordLogsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Service
@RequiredArgsConstructor
public class RecordToLogDTOConverter {



    public RecordLogsDTO recordEntityToDTO(RecordEntity recordEntity){



        return RecordLogsDTO.builder()
                .behaviorName(recordEntity.getBehaviorEntity().getBehaviorName())
                .recordId(recordEntity.getId())
                .StudentName(recordEntity.getBehaviorEntity().getStudentEntity().getStudent_name())
                .timestamp(Timestamp.valueOf(recordEntity.getTime()).toLocalDateTime())
                .build();

    }
}
