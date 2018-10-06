/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

import com.solar.entity.BatteryTechnical;
import com.solar.entity.ChargeControllerTechnical;
import com.solar.entity.GeneralTechnical;
import com.solar.entity.LampTechnical;
import com.solar.entity.PanelTechnical;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bibek
 */
@Data
@RequiredArgsConstructor
public class SecondForm {
     private GeneralTechnical generalTechnical;
     private PanelTechnical panelTechnical;
     private BatteryTechnical batteryTechnical;
     private ChargeControllerTechnical chargeControllerTechnical;
     private LampTechnical lampTechnical;
}
