package map;

public class MapInfo {
    public final static int POLICE_STATION = 1;
    public final static int BANK = 2;
    public final static int THIEF_START = 3;

    private Cell    cells[][];
    private int N;

    public String getPoliceStationPosition() {
        return policeStationPosition;
    }

    public void setPoliceStationPosition(String policeStationPosition) {
        this.policeStationPosition = policeStationPosition;
    }

    public String getThiefPosition() {
        return thiefPosition;
    }

    public void setThiefPosition(String thiefPosition) {
        this.thiefPosition = thiefPosition;
    }

    private String policeStationPosition;
    private String thiefPosition;

    public void setCellType(int row, int column, int character){
        cells[row][column].setCharacter(character);
    }


    public MapInfo() {
    }

    public MapInfo(int n) {
        N = n;
        cells = new Cell[n][n];
    }

    public void setCell(int row, int column, Cell cell){
        cells[row][column] = cell;
    }

    public Cell getCell(int row, int column){
        //System.out.println("getCell: row: " + row + "******column: " + column );
        return cells[row][column];
    }

    public void setMapSize(int n){
        N = n;
        cells = new Cell[n][n];
    }

    public int getMapSize(){
        return N;
    }

    //(0,0),true,true,true,true,0"
    public String toMazeMap(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int row = 0; row < getMapSize(); ++row){
            for (int column = 0; column < getMapSize(); ++column){
                stringBuffer.append("(" + row + "," + column + "),");

                stringBuffer.append(Boolean.toString(getCell(row,column).isEast())+",");
                stringBuffer.append(Boolean.toString(getCell(row,column).isSouth())+",");
                stringBuffer.append(Boolean.toString(getCell(row,column).isWest())+",");
                stringBuffer.append(Boolean.toString(getCell(row,column).isNorth())+",");

                stringBuffer.append(""+ getCell(row, column).getCharacter());
                stringBuffer.append(" ");
            }
        }
        stringBuffer.append("],");
        return stringBuffer.toString();
    }

    public String toEasyMap(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int row = 0; row < getMapSize(); ++row){
            for (int column = 0; column < getMapSize(); ++column){
                stringBuffer.append(getCell(row, column).getCharacter());
            }
            stringBuffer.append("\r\n");
        }
        return stringBuffer.toString();
    }

    private boolean isValid(int row, int column){
        return  0 <= row && row < getMapSize() && 0 <= column && column < getMapSize();
    }

    private static String cellToString(int row, int column){
        return "[" + row + "," + column + "]";
    }
}
