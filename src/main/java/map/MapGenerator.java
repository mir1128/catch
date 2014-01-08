package map;

public interface MapGenerator {
    MapInfo     generate( int n );
    void        setMapInfo(MapInfo mapInfo);
}
