package map;

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
        return mapInfo;
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
        mapGenerator.generate(50);

        display(mapInfo);

    }
}
