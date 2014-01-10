package util;

import game.Coordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinateConverter {
    public static Coordinate string2Value(String coordinate){
        Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher mather = pattern.matcher(coordinate);

        if (mather.find()){
            return new Coordinate(Integer.valueOf(mather.group(1)), Integer.valueOf(mather.group(2)));
        }
        return null;
    }

    public static String value2String(Coordinate coordinate){
        return "(" + coordinate.getRow() + "," + coordinate.getColumn() + ")";
    }
}
