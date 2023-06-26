package json;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class InfoModel {
    private String name;
    private Map<String, String> extInfo;
    private Date infoDate;
}
