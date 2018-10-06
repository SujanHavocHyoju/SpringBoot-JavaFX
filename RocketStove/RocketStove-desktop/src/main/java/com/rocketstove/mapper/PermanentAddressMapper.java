/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.PermanentAddress;
import com.rocketstove.entity.PermanentAddressEntity;
import com.rocketstove.repository.PermanentAddressRepository;
import com.rocketstove.utils.JsonUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class PermanentAddressMapper implements Mapper<PermanentAddress, PermanentAddressEntity> {

    @Autowired
    private PermanentAddressRepository permanentAddressRepository;

    @Override
    public Optional<PermanentAddressEntity> mapFormToEntity(Optional<PermanentAddress> permanentAddress) {
        if (permanentAddress.isPresent()) {
            PermanentAddressEntity permanentAddressEntity = JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(permanentAddress.get()), PermanentAddressEntity.class);
            PermanentAddressEntity retrieveEntity = this.permanentAddressRepository.findByRocketId(permanentAddressEntity.getRocketId());
            if (retrieveEntity != null) {
                permanentAddressEntity.setId(retrieveEntity.getId());
            }
            return Optional.ofNullable(permanentAddressEntity);
        }
        return Optional.empty();
    }
}
