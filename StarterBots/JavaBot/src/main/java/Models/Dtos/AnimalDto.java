package Models.Dtos;

import utilities.Point;

public class AnimalDto {
    private String id;
    private String nickname;
    private int x;
    private int y;
    private int spawnX;
    private int spawnY;
    private int score;
    private int capturedCounter;
    private int distanceCovered;
    private boolean isViable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNickName() {
    	return nickname;
    }
    
    public void setNickName(String NickName) {
    	this.nickname = NickName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCapturedCounter() {
        return capturedCounter;
    }

    public void setCapturedCounter(int capturedCounter) {
        this.capturedCounter = capturedCounter;
    }

    public int getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(int distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public boolean isViable() {
        return isViable;
    }

    public void setViable(boolean viable) {
        isViable = viable;
    }
    
    public Point getCurrentPoint() {
    	return new Point(x,y);
    }
    
    public Point getSpawnPoint() {
    	return new Point(spawnX,spawnY);
    }
    
    public String toString() {
    	return "\nAnimal_ID : "+id+"\nNickname: "+nickname+"\nCurrent position: "+x+","+y+" Spawn Point: "+spawnX+","+spawnY+
    			" isViable: "+isViable+" Distance_covered: "+distanceCovered+" Captured: "+capturedCounter;
    }
    
    
}
