package com.bailuyiting.commons.until;

import com.bailuyiting.commons.core.entity.camera.AlarmInfoPlate;

import java.util.HashMap;
import java.util.Map;

/**
 * 摄像头自动识别工具类
 */
public class CameraUntils {
    /**
     * 解析json
     * @param alarmInfoPlate
     * @return
     */
    public static AlarmInfoPlate getAlarmInfoPlate(String alarmInfoPlate){
        //解析成MAP
        Map<String, Object> map = JSONUtils.jsonToMap(alarmInfoPlate);
        return JSONUtils.jsonToObject(JSONUtils.objectToJson(map.get("AlarmInfoPlate")), AlarmInfoPlate.class);
    }

    /**
     * 确认车辆进场，抬杆
     * @return
     */
    public  static Map<String,Object> resultSuccess(){
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> seData = new HashMap<>();
        seData.put("data","");
        seData.put("datalen",0);
        response.put("Open",0);
        response.put("SerialData",seData);
        result.put("Response",response);
        return result;
    }

}
