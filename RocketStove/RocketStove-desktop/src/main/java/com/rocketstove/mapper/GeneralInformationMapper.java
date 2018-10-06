/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.Ethnicity;
import com.rocketstove.domain.GeneralInformationI;
import com.rocketstove.entity.GeneralInformationEntity;
import com.rocketstove.repository.GeneralInformationRespository;
import com.rocketstove.utils.JsonUtils;
import com.rocketstove.utils.LocalJsonUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class GeneralInformationMapper implements Mapper<GeneralInformationI, GeneralInformationEntity> {

    @Value("${asset_dir}")
    private String assetDir;
    @Autowired
    private GeneralInformationRespository generalInformationRespository;

    @Override
    public Optional<GeneralInformationEntity> mapFormToEntity(Optional<GeneralInformationI> generalInformation) {
        if (generalInformation.isPresent()) {
            GeneralInformationEntity generalInformationEntity = JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(generalInformation.get()),
                    GeneralInformationEntity.class);
            GeneralInformationEntity retrieveInformation = this.generalInformationRespository.findByRocketId(generalInformationEntity.getRocketId());
            if (retrieveInformation != null) {
                generalInformationEntity.setId(retrieveInformation.getId());
            }
            List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
            Ethnicity ethnicity = ethnicityList.stream().filter(e
                    -> e.getEthnicityName().equalsIgnoreCase(generalInformation.get().getEthinicity())
            )
                    .findAny().orElse(ethnicityList.get(0));
            generalInformationEntity.setEthinicity(ethnicity.getEthnicityId());
            return Optional.
                    ofNullable(generalInformationEntity);
        }
        return Optional.empty();
    }

}
