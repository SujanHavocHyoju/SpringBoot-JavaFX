/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.CurrentAddress;
import com.rocketstove.entity.CurrentAddressEntity;
import com.rocketstove.repository.CurrentAddressRespository;
import com.rocketstove.utils.JsonUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class CurrentAddressMapper implements Mapper<CurrentAddress, CurrentAddressEntity> {

    @Autowired
    private CurrentAddressRespository currentAddressRespository;

    @Override
    public Optional<CurrentAddressEntity> mapFormToEntity(Optional<CurrentAddress> currentAddress) {
        if (currentAddress.isPresent()) {
            CurrentAddressEntity currentAddressEntity = JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(currentAddress.get()), CurrentAddressEntity.class);
            CurrentAddressEntity retrieveEntity = this.currentAddressRespository.findByRocketId(currentAddressEntity.getRocketId());
            if (retrieveEntity != null) {
                currentAddressEntity.setId(retrieveEntity.getId());
            }
            return Optional.of(currentAddressEntity);
        }
        return Optional.empty();
    }
    
}
