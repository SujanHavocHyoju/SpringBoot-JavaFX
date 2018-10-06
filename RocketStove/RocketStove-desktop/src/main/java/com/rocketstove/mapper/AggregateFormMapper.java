/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.AggregateForm;
import com.rocketstove.entity.AggregateFormEntity;
import com.rocketstove.repository.AggregateFormRespository;
import com.rocketstove.utils.JsonUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class AggregateFormMapper implements Mapper<AggregateForm, AggregateFormEntity> {

    @Autowired
    private AggregateFormRespository aggregateFormRespository;

    public Optional<AggregateFormEntity> mapFormToEntityFromFile(File file) {
        try {
            String aggregateFormJSON = FileUtils.readFileToString(file, Charset.defaultCharset());
            AggregateForm aggregateForm = JsonUtils.fromJsonToObj(aggregateFormJSON, AggregateForm.class);
            return mapFormToEntity(Optional.of(aggregateForm));
        } catch (IOException ex) {
            Logger.getLogger(AggregateFormMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<AggregateFormEntity> mapFormToEntity(Optional<AggregateForm> aggregateForm) {
        if (aggregateForm.isPresent()) {
            String aggregateFormJson = JsonUtils.toJsonObj(aggregateForm.get());
            AggregateFormEntity aggregateFormEntity = JsonUtils.fromJsonToObj(aggregateFormJson, AggregateFormEntity.class);
            AggregateFormEntity retrieveEntity = this.aggregateFormRespository.findByRocketId(aggregateForm.get().getRocketId());
            if (retrieveEntity != null) {
                aggregateFormEntity.setId(retrieveEntity.getId());
            }
            return Optional.of(aggregateFormEntity);
        }
        return Optional.empty();

    }

    @Override
    public Optional<AggregateForm> mapEntityToForm(Optional<AggregateFormEntity> e) {
        if(e.isPresent()){
            String jsonStr = JsonUtils.toJsonObj(e.isPresent());
            return Optional.ofNullable(JsonUtils.fromJsonToObj(jsonStr, AggregateForm.class));
        }
        return Optional.empty();
    }
    

}
