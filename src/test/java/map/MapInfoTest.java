package map;

import junit.framework.TestCase;

public class MapInfoTest extends TestCase{
    public void testMazeMap() throws Exception {
        MapGenerator mapGenerator = new MapAutoGenerator();

        mapGenerator.setMapInfo(new MapInfo());

        MapInfo mapInfo = mapGenerator.generate(5);
        System.out.println(mapInfo.toMazeMap());
    }

    public void testEasyMap(){
        MapGenerator mapGenerator = new TextMapLoaderGenerator("src/main/resources/m1", new MapInfo());

        MapInfo mapInfo = mapGenerator.generate(11);

        System.out.println(mapInfo.toEasyMap());
    }
}
