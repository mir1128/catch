package map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static view.MapViewer.display;

public class TextMapLoaderGenerator implements MapGenerator {
    private MapInfo mapInfo;

    public TextMapLoaderGenerator() {}

    private String fileName;

    public TextMapLoaderGenerator(String fileName, MapInfo mapInfo) {
        this.fileName = fileName;
        this.mapInfo = mapInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    private MapInfo loadFromFile(){
        File file = new File(getFileName());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String oneLine = reader.readLine();
            mapInfo.setMapSize(Integer.valueOf(oneLine.trim()));

            int line = 0;
            while ((oneLine = reader.readLine()) != null) {
                String arr[] = oneLine.split(",");
                setCells(line, arr);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        buildMapInfo();
        return mapInfo;
    }

    private void buildMapInfo() {
        for (int row = 1; row < mapInfo.getMapSize()-1; ++row){
            for (int column = 1; column < mapInfo.getMapSize()-1; ++column){
                if (mapInfo.getCell(row, column).getCharacter() >= 1 && mapInfo.getCell(row, column+1).getCharacter() >= 1)
                {
                    //east
                    mapInfo.getCell(row, column).setEast(false);
                    mapInfo.getCell(row, column+1).setWest(false);
                }

                if (mapInfo.getCell(row, column).getCharacter() >=1 && mapInfo.getCell(row+1, column).getCharacter() >= 1){
                    //south
                    mapInfo.getCell(row, column).setSouth(false);
                    mapInfo.getCell(row+1, column).setNorth(false);

                }
                if (mapInfo.getCell(row, column).getCharacter() >=1 && mapInfo.getCell(row, column-1).getCharacter() >= 1){
                    //west
                    mapInfo.getCell(row, column).setWest(false);
                    mapInfo.getCell(row, column-1).setEast(false);
                }


                if (mapInfo.getCell(row, column).getCharacter() >=1 && mapInfo.getCell(row-1, column).getCharacter() >= 1){
                    //north
                    mapInfo.getCell(row, column).setNorth(false);
                    mapInfo.getCell(row-1, column).setSouth(false);
                }
            }
        }

    }

    private void setCells(int line, String[] arr) {
        for (int i = 0; i < arr.length; ++i){
            mapInfo.setCell(line, i, new Cell(true, true, true, true, Integer.valueOf(arr[i])));
        }
    }

    @Override
    public MapInfo generate(int n) {
        return loadFromFile();
    }

    public static void main(String[] args) {
        MapGenerator mapGenerator = new TextMapLoaderGenerator("src/main/resources/m1", new MapInfo());

        MapInfo mapInfo = mapGenerator.generate(0);

        display(mapInfo);
    }
}


