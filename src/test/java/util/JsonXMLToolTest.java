package util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JsonXMLToolTest extends TestCase{
    public void testXml2JSON() throws Exception {
        String xml = "<account><name>tom</name></account>";
        String json = JsonXMLTool.xml2JSON(xml);
        Assert.assertEquals(json, "{\"name\":\"tom\"}");

    }

    public void testJson2XML() throws Exception {
        String json = "{\"name\":\"tom\"}";
        String xml = JsonXMLTool.json2XML(json);
        Assert.assertTrue(xml.contains("tom"));
        Assert.assertTrue(xml.contains("name"));
    }
}
