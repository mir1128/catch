package map;

public class MapInfo {
    public final static int POLICE_STATION = 1;
    public final static int BANK = 2;

    private Cell    cells[][];
    private int N;

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

    //[1,2]=(1,[1,2],[2,2],[1,1],[0,2]);
    public String toMazeMap(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int row = 0; row < getMapSize(); ++row){
            for (int column = 0; column < getMapSize(); ++column){
                stringBuffer.append("[" + row + "," + column + "]=");
                stringBuffer.append("(");
                stringBuffer.append(""+ getCell(row, column).getCharacter());

                if (getCell(row, column).isEast() && isValid(row, column+1)){
                    if (getCell(row,column+1).isWest()){
                        stringBuffer.append("["+row+","+(column+1)+"],");
                    }
                }

                if (getCell(row, column).isSouth() && isValid(row+1, column)){
                    if (getCell(row+1,column).isWest()){
                        stringBuffer.append("["+(row+1)+","+(column)+"],");
                    }
                }

                if (getCell(row, column).isWest() && isValid(row, column-1)){
                    if (getCell(row,column-1).isWest()){
                        stringBuffer.append("["+row+","+(column-1)+"],");
                    }
                }

                if (getCell(row, column).isEast() && isValid(row-1, column)){
                    if (getCell(row-1,column).isWest()){
                        stringBuffer.append("["+(row-1)+","+(column)+"],");
                    }
                }

                stringBuffer.append(")\r\n");
            }
        }
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
