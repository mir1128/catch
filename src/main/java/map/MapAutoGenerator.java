package map;

import java.util.Map;
import java.util.Random;

import static view.MapViewer.display;

public class MapAutoGenerator implements MapGenerator{
    private MapInfo mapInfo;

    private boolean[][] visited;
    private boolean done = false;

    @Override
    public MapInfo generate(int n) {
        mapInfo.setMapSize(n);
        init();
        Random random = new Random();
        generate(1, 1);

        generatePoliceStationPosition();
        generateBankPosition();
        generateThiefPosition();
        return mapInfo;
    }

    private void generateThiefPosition() {
        Random random = new Random();
        int pos = random.nextInt(4);

        if (pos == 0){
            mapInfo.setCellType(0,0,MapInfo.THIEF_START);
            mapInfo.setThiefPosition("(0,0)");
        }else if (pos == 1){
            mapInfo.setCellType(mapInfo.getMapSize()-1, 0, MapInfo.THIEF_START);
            mapInfo.setThiefPosition("("+(mapInfo.getMapSize()-1) +","+"0)");
        }else if (pos==2){
            mapInfo.setCellType(0, mapInfo.getMapSize()-1, MapInfo.THIEF_START);
            mapInfo.setThiefPosition("(0,"+(mapInfo.getMapSize()-1)+")");
        }else {
            mapInfo.setCellType(mapInfo.getMapSize()-1, mapInfo.getMapSize()-1, MapInfo.THIEF_START);
            mapInfo.setThiefPosition("("+(mapInfo.getMapSize()-1)+","+(mapInfo.getMapSize()-1)+")");
        }
    }

    private void generateBankPosition() {
        int n = mapInfo.getMapSize();
        Map<String, Integer> bankInfo = mapInfo.getBankInfo();
        int rowStep = n/4;
        int columnStep = n/6;

        for (int x = 0; x < 2; ++x){
            for (int y = 0; y < 3; ++y){
                int row = rowStep + x*(n/2);
                int column = columnStep+y*(n/3);
                mapInfo.setCellType(row, column, MapInfo.BANK);
                bankInfo.put("("+ row + column +")", 1000);
            }
        }
    }

    private void generatePoliceStationPosition() {
        int center = mapInfo.getMapSize()/2;
        mapInfo.setCellType(center, center, MapInfo.POLICE_STATION);
        mapInfo.setPoliceStationPosition("("+center+","+center+")");
    }

    @Override
    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    private void init() {
        int n = mapInfo.getMapSize();
        visited = new boolean[n][n];
        for (int column = 0; column < n; column++) visited[0][column] = visited[n-1][column] = true;
        for (int row = 0; row < n; row++) visited[row][0] = visited[row][n-1] = true;

        for (int column = 0; column < n ; column++)
            for (int row = 0; row < n ; row++)
                mapInfo.setCell(row, column, new Cell(true, true, true, true));
    }

    private void generate(int row, int column) {
        //System.out.println("row : " + row + "--------column: " + column );
        visited[row][column] = true;

        while (!visited[row][column+1] || !visited[row+1][column] || !visited[row][column-1] || !visited[row-1][column]) {
            while (true) {
                double r = Math.random();
                if (r < 0.25 && !visited[row][column+1]) {
                    mapInfo.getCell(row, column).setEast(false);
                    mapInfo.getCell(row, column+1).setWest(false);
                    generate(row, column + 1);
                    break;
                }
                else if (r >= 0.25 && r < 0.50 && !visited[row+1][column]) {
                    mapInfo.getCell(row, column).setSouth(false);
                    mapInfo.getCell(row+1, column).setNorth(false);
                    generate(row + 1, column);
                    break;
                }
                else if (r >= 0.5 && r < 0.75 && !visited[row][column-1]) {
                    mapInfo.getCell(row, column).setWest(false);
                    mapInfo.getCell(row, column-1).setEast(false);
                    generate(row, column - 1);
                    break;
                }
                else if (r >= 0.75 && r < 1.00 && !visited[row-1][column]) {
                    mapInfo.getCell(row,column).setNorth(false);
                    mapInfo.getCell(row-1, column).setSouth(false);
                    generate(row - 1, column);
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        MapInfo mapInfo = new MapInfo();
        MapGenerator mapGenerator = new MapAutoGenerator();
        mapGenerator.setMapInfo(mapInfo);
        mapGenerator.generate(25);

        display(mapInfo);

    }
}
