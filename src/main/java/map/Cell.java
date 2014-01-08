package map;

public class Cell {
    private boolean east = true;
    private boolean south = true;
    private boolean west = true;
    private boolean north = true;

    private int character = 0;

    public boolean isEast() {
        return east;
    }

    public Cell(boolean east, boolean south, boolean west, boolean north, int character) {
        this.east = east;
        this.south = south;
        this.west = west;
        this.north = north;
        this.character = character;
    }

    public Cell() {
    }

    public Cell(boolean east, boolean south, boolean west, boolean north) {
        this.east = east;
        this.south = south;
        this.west = west;
        this.north = north;

    }

    public Cell(boolean[] direction){
        this.east = direction[0];
        this.south = direction[1];
        this.west = direction[2];
        this.north = direction[3];
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public boolean isSouth() {
        return south;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public boolean isWest() {
        return west;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public boolean isNorth() {
        return north;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }
}
