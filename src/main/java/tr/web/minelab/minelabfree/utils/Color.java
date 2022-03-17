package tr.web.minelab.minelabfree.utils;

public class Color {

    public static java.awt.Color getColor(String col) {
        java.awt.Color color;
        switch (col.toLowerCase()) {
            case "black":
                color = java.awt.Color.BLACK;
                break;
            case "blue":
                color = java.awt.Color.BLUE;
                break;
            case "cyan":
                color = java.awt.Color.CYAN;
                break;
            case "darkgray":
                color = java.awt.Color.DARK_GRAY;
                break;
            case "gray":
                color = java.awt.Color.GRAY;
                break;
            case "green":
                color = java.awt.Color.GREEN;
                break;
            case "yellow":
                color = java.awt.Color.YELLOW;
                break;
            case "lightgray":
                color = java.awt.Color.LIGHT_GRAY;
                break;
            case "magneta":
                color = java.awt.Color.MAGENTA;
                break;
            case "orange":
                color = java.awt.Color.ORANGE;
                break;
            case "pink":
                color = java.awt.Color.PINK;
                break;
            case "red":
                color = java.awt.Color.RED;
                break;
            case "white":
                color = java.awt.Color.WHITE;
                break;
            default:
                color = java.awt.Color.WHITE;
        }
        return color;
    }

}
