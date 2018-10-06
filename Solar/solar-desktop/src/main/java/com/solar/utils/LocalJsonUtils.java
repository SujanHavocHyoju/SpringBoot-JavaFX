/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.utils;

/**
 *
 * @author SUJAN
 */
import com.google.gson.reflect.TypeToken;
import com.solar.domain.AreaClass;
import com.solar.domain.AssId;
import com.solar.domain.AssIdType;
import com.solar.domain.BDAType;
import com.solar.domain.BDAid;
import com.solar.domain.BatteryManufacturer;
import com.solar.domain.BatteryModel;
import com.solar.domain.BatteryType;
import com.solar.domain.BulbType;
import com.solar.domain.ChargeControllerModel;
import com.solar.domain.ChargeManufacuturer;
import com.solar.domain.CitizenDistrict;
import com.solar.domain.District;
import com.solar.domain.Ethnicity;
import com.solar.domain.Gender;
import com.solar.domain.Installer;
import com.solar.domain.InstitutionType;
import com.solar.domain.LampManufacturer;
import com.solar.domain.LampModel;
import com.solar.domain.Manufacturer;
import com.solar.domain.MobileCharge;
import com.solar.domain.PanelManufacturer;
import com.solar.domain.PanelModel;
import com.solar.domain.Radio;
import com.solar.domain.RadioCharge;
import com.solar.domain.StarterForm;
import com.solar.domain.TargetGroup;
import com.solar.domain.Television;
import com.solar.domain.VPNP;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.log;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
public class LocalJsonUtils {

    //private static final String ASSEST_LOCATION = "C:\\Users\\SUJAN\\Documents\\NetBeansProjects\\RocketStove\\src\\main\\resources\\assets\\";
    public static List<District> loadDistrict(String location) {
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "district.json"), Charset.defaultCharset()), type);
        } catch (IOException E) {
            E.printStackTrace();
        }
        return null;
    }
    
    public static List<CitizenDistrict> loadCitizenshipDistrict(String location) {
        try {
            Type type = new TypeToken<List<CitizenDistrict>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "citizenDistrict.json"), Charset.defaultCharset()), type);
        } catch (IOException E) {
            E.printStackTrace();
        }
        return null;
    }

    public static List<VPNP> loadVDCnp(String location) {
        try {
            Type type = new TypeToken<List<VPNP>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "VDC_NPList.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<VPNP> loadvpnp(String location) {
        try {
            Type type = new TypeToken<List<VPNP>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "VP_NPList.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<Ethnicity> loadEthinicity(String location) {
        try {
            Type type = new TypeToken<List<Ethnicity>>() {
            }.getType();
            String content = FileUtils.readFileToString(new File(location, "ethnicity.json"), Charset.defaultCharset());
            return JsonUtils.fromJsonToList(content, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<TargetGroup> loadTargetGroup(String location) {
        try {
            Type type = new TypeToken<List<TargetGroup>>() {
            }.getType();
            String content = FileUtils.readFileToString(new File(location, "targetGroup.json"), Charset.defaultCharset());
            return JsonUtils.fromJsonToList(content, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Manufacturer> loadManufacturer(String location) {
        try {
            Type type = new TypeToken<List<Manufacturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "manufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<Gender> loadGender(String location) {
        try {
            Type type = new TypeToken<List<Gender>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "gender.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
            log.info("here is error : "+e.getMessage() );
        }
        return null;
    }

    public static List<AreaClass> loadAreaClass(String location) {
        try {
            Type type = new TypeToken<List<AreaClass>>() {
            }.getType();
            String json
                    = FileUtils.readFileToString(new File(location, "areaclass.json"), Charset.defaultCharset());
            return JsonUtils.fromJsonToList(json, type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BDAType> loadBDATypes(String location) {
        try {
            Type type = new TypeToken<List<BDAType>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "bdatype.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<AssIdType> loadASSTypes(String location) {
        try {
            Type type = new TypeToken<List<AssIdType>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "asstype.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<Installer> loadInstallers(String location) {
        try {
            Type type = new TypeToken<List<Installer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "installer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BDAid> loadBDAIds(String location) {
        try {
            Type type = new TypeToken<List<BDAid>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "bdaid.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<AssId> loadASSIds(String location) {
        try {
            Type type = new TypeToken<List<AssId>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "assid.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    public static List<PanelManufacturer> loadPanelMfgs(String location) {
        try {
            Type type = new TypeToken<List<PanelManufacturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "panelManufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<PanelModel> loadPanelModels(String location) {
        try {
            Type type = new TypeToken<List<PanelModel>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "panelModel.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BatteryManufacturer> loadBatteryMfgs(String location) {
        try {
            Type type = new TypeToken<List<BatteryManufacturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "batteryManufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BatteryModel> loadBatteryModels(String location) {
        try {
            Type type = new TypeToken<List<BatteryModel>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "batteryModel.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BatteryType> loadBatteryTypes(String location) {
        try {
            Type type = new TypeToken<List<BatteryType>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "batterytype.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<ChargeManufacuturer> loadChargeMfgs(String location) {
        try {
            Type type = new TypeToken<List<ChargeManufacuturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "chargeControllerManufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<ChargeControllerModel> loadChargeModels(String location) {
        try {
            Type type = new TypeToken<List<ChargeControllerModel>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "chargeControllerModel.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<LampManufacturer> loadLampMfgs(String location) {
        try {
            Type type = new TypeToken<List<LampManufacturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "lampManufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<LampModel> loadLampModels(String location) {
        try {
            Type type = new TypeToken<List<LampModel>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "lampmodel.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<InstitutionType> loadInstitutions(String location) {
        try {
            Type type = new TypeToken<List<InstitutionType>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "institutiontype.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<MobileCharge> loadMobileCharges(String location) {
        try {
            Type type = new TypeToken<List<MobileCharge>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "mobilecharge.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<Radio> loadRadios(String location) {
        try {
            Type type = new TypeToken<List<Radio>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "radio.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<BulbType> loadBulbTypes(String location) {
        try {
            Type type = new TypeToken<List<BulbType>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "bulbtype.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<RadioCharge> loadRadioCharges(String location) {
        try {
            Type type = new TypeToken<List<RadioCharge>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "radiocharge.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }
    
    public static List<Television> loadTelevisions(String location) {
        try {
            Type type = new TypeToken<List<Television>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "television.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static StarterForm loadStarterForm(File file) {
        try {
            return JsonUtils.fromJsonToObj(FileUtils.readFileToString(file, Charset.defaultCharset()), StarterForm.class);
        } catch (IOException ex) {
            log.info("FILE IS NOT DOWNLOADING");
            return null;
        }
    }

}
