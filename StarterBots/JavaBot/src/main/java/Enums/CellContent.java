package Enums;

public enum CellContent {
    Empty(0),
    Wall(1),
    Pellet(2),
    ZookeeperSpawn(3),
    AnimalSpawn(4);
    
    private final int val;

    CellContent(int value) {
        this.val = value;
    }

    public int getValue() {
        return val;
    }
}
