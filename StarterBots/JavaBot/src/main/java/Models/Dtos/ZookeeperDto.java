package Models.Dtos;

import utilities.Point;

public class ZookeeperDto {
    private String id;
    private String nickname;
    private int x;
    private int y;
    private int spawnX;
    private int spawnY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String Nickname() {
    	return nickname;
    }
    
    public void setNickname(String Nickname) {
    	this.nickname = Nickname;
    }
    

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

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int SpawnX) {
        this.spawnX = SpawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int SpawnY) {
        this.spawnY = SpawnY;
    }
    
    public Point getKeeperPoint() {
    	return new Point(x,y);
    }
    
    
    public String toString() {
    	return "UUID : "+id+" NickName: "+nickname+" Current position: "+x+","+y+" Spawn Point: "+spawnX+","+spawnY;
    }
    
    
}
