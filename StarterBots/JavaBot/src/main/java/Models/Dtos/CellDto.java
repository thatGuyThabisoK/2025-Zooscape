package Models.Dtos;

import utilities.Point;

public class CellDto {
    private int x;
    private int y;
    private int content;

    public int getX() {
        return x;
    }

    public void setX(int X) {
        this.x = X;
    }

    public int getY() {
        return y;
    }

    public void setY(int Y) {
        this.y = Y;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int Content) {
        this.content = Content;
    }
    
    public Point getCellPoint() {
    	return new Point(x,y);
    }
    
    public String toString() {
    	return " Coordinates: "+x+","+y+" Content: "+content;
    }
    
    
}
