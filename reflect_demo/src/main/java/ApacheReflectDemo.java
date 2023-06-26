import json.DataModel;
import json.JsonUtil;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Date;

/**
 * 使用apache工具获取属性值，只能从一级属性获取值。
 */
public class ApacheReflectDemo {
    public static void main(String[] args) throws IllegalAccessException {
        DataModel dataModel = JsonUtil.buildDataModel();
        Date dateValue = null;
        try {
            dateValue = (Date) FieldUtils.readField(dataModel, "createTime", true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(dateValue);

        // 下面这种方式无法获取值
        Object obj = FieldUtils.readField(dataModel, "infoModel.infoDate", true);
        System.out.println(obj);

    }
}
