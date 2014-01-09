package map;

public class MapHolder {
    private MapInfo mapInfo = null;
    private static MapHolder instance = null;

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    public static MapHolder getInstance(){
        if (instance == null){
            instance = new MapHolder();
        }
        return instance;
    }

    private MapHolder() {
    }
}
