package json;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class DataModel {
    private Date createTime;

    private String id;

    private Map<String, String> extInfo;

    private InfoModel infoModel;
}
