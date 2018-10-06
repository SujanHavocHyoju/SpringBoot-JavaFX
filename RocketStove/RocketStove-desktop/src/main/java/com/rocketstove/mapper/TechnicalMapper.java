/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.Technical;
import com.rocketstove.entity.TechnicalEntity;
import com.rocketstove.repository.TechnicalRepository;
import com.rocketstove.utils.JsonUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class TechnicalMapper implements Mapper<Technical, TechnicalEntity> {

    @Autowired
    private TechnicalRepository technicalRepository;

    @Override
    public Optional<TechnicalEntity> mapFormToEntity(Optional<Technical> tehnical) {
        if (tehnical.isPresent()) {
            TechnicalEntity technicalEntity = JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(tehnical.get()), TechnicalEntity.class);
            TechnicalEntity retrieveEntity = this.technicalRepository.findByRocketId(technicalEntity.getRocketId());
            if (retrieveEntity != null) {
                technicalEntity.setId(retrieveEntity.getId());
            }
            return Optional.of(technicalEntity);
        }
        return Optional.empty();
    }

}
