package util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JsonXMLTool {
    public static String xml2JSON(String xml){
        return new XMLSerializer().read(xml).toString();
    }

    public static String json2XML(String json){
        JSONObject jsonObject = JSONObject.fromObject(json);
        return new XMLSerializer().write(jsonObject);
    }
}
