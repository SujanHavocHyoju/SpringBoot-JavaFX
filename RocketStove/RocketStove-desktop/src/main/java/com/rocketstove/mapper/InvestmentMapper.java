/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.Investment;
import com.rocketstove.entity.InvestmentEntity;
import com.rocketstove.repository.InvestmentRepository;
import com.rocketstove.utils.JsonUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class InvestmentMapper implements Mapper<Investment, InvestmentEntity> {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Override
    public Optional<InvestmentEntity> mapFormToEntity(Optional<Investment> investment) {
        if (investment.isPresent()) {
            InvestmentEntity investmentEntity = JsonUtils
                    .fromJsonToObj(
                            JsonUtils.toJsonObj(investment.get()), InvestmentEntity.class);
            InvestmentEntity retrieveEntity = this.investmentRepository.findByRocketId(investmentEntity.getRocketId());
            if(retrieveEntity!=null){
                investmentEntity.setId(retrieveEntity.getId());
            }
            return Optional
                    .of(investmentEntity);
        }
        return Optional.empty();
    }

}
