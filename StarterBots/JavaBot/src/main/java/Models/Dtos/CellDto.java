package Models.Dtos;

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

    public int getCellContent() {
        return content;
    }

    public void setCellContent(int Content) {
        this.content = Content;
    }
    
    public String toString() {
    	return "Coordinates: "+x+","+y+" Content: "+content;
    }
    
    
}
