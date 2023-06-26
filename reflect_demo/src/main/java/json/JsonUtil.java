package json;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class JsonUtil {

    public static Object getValueFromJson(JSONObject jsonObject, String key) {
        JSONObject tempJson = jsonObject;
        String[] parts = StringUtils.split(key, ".");
        for(int i=0; i<parts.length-1;i++){
            tempJson = tempJson.getJSONObject(parts[i]);
            if(tempJson ==null){
                return null;
            }
        }

        return tempJson.get(parts[parts.length-1]);
    }

    public static void main(String[] args) throws ParseException {
        DataModel dataModel = buildDataModel();

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(dataModel));
        Object value = getValueFromJson(jsonObject, "extInfo.schedule_time");
        System.out.println(convertDate(value));

        value = getValueFromJson(jsonObject, "createTime");
        System.out.println(convertDate(value));

        value = getValueFromJson(jsonObject, "infoModel.infoDate");
        System.out.println(convertDate(value));

        value = getValueFromJson(jsonObject, "infoModel.extInfo.info_time");
        System.out.println(convertDate(value));
    }

    public static Date convertDate(Object dateValue) throws ParseException {
        Date date = null;
        if(dateValue instanceof String) {
            date = DateUtils.parseDate((String)dateValue, "yyyy-MM-dd HH:mm:ss");
        } else if(dateValue instanceof Long) {
            date = new Date((Long)dateValue);
        }
        return date;
    }

    public static DataModel buildDataModel(){
        DataModel dataModel = new DataModel();
        dataModel.setId("001");
        dataModel.setCreateTime(new Date());

        HashMap<String, String> extInfoMap = new HashMap<>();
        extInfoMap.put("schedule_time", "2023-06-19 20:30:00");
        dataModel.setExtInfo(extInfoMap);

        InfoModel infoModel = new InfoModel();
        infoModel.setName("info");
        infoModel.setInfoDate(new Date());
        HashMap<String, String> infoModelExtMap = new HashMap<>();
        infoModelExtMap.put("info_time", "2023-06-19 21:30:00");
        infoModel.setExtInfo(infoModelExtMap);
        dataModel.setInfoModel(infoModel);

        return dataModel;
    }
}
