package map;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MapAutoGeneratorTest extends TestCase {
    public void testGenerate() throws Exception {
        MapGenerator mapGenerator = new MapAutoGenerator();
        mapGenerator.setMapInfo(new MapInfo());
        MapInfo mapInfo = mapGenerator.generate(20);

        Assert.assertEquals(mapInfo.getMapSize(), 20);
    }
}
