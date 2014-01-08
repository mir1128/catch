package map;

import junit.framework.TestCase;
import junit.framework.Assert;

public class TextMapLoaderGeneratorTest extends TestCase {
    public void testGenerate() throws Exception {
        MapGenerator mapGenerator = new TextMapLoaderGenerator("src/main/resources/m1", new MapInfo());

        MapInfo mapInfo = mapGenerator.generate(0);
        Assert.assertEquals(mapInfo.getMapSize(), 11);
        Assert.assertEquals(mapInfo.getCell(0,0).getCharacter(),  0);
        Assert.assertEquals(mapInfo.getCell(1,1).getCharacter(),  1);
    }
}
