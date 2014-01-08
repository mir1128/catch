package view;

import map.MapAutoGenerator;
import map.MapGenerator;
import map.MapInfo;
import stdlib.StdDraw;

public class MapViewer {
    public static void display(MapInfo mapInfo) {
        StdDraw.setXscale(0, mapInfo.getMapSize());
        StdDraw.setYscale(0, mapInfo.getMapSize());

        StdDraw.setPenColor(StdDraw.RED);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int row = 1; row < mapInfo.getMapSize()-1; row++) {
            for (int column = 1; column < mapInfo.getMapSize()-1; column++) {
                if (mapInfo.getCell(row, column).isSouth()) StdDraw.line(row+1, column, row + 1, column+1);
                if (mapInfo.getCell(row, column).isNorth()) StdDraw.line(row, column , row , column + 1);
                if (mapInfo.getCell(row, column).isWest())  StdDraw.line(row, column, row +1, column );
                if (mapInfo.getCell(row, column).isEast())  StdDraw.line(row , column + 1, row + 1, column + 1);
            }
        }
        StdDraw.show(1000);
    }
}
