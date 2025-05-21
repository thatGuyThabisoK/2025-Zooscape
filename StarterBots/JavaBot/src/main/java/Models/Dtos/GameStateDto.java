package Models.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class GameStateDto {
    private String timeStamp;
    private int tick;
    private List<CellDto> cells = new ArrayList<>();
    private List<AnimalDto> animals  = new ArrayList<>();
    private List<ZookeeperDto> zookeepers = new ArrayList<>();

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public List<CellDto> getCells() {
        return cells;
    }

    public void setCells(List<CellDto> cells) {
        this.cells = cells;
    }

    public List<AnimalDto> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalDto> animals) {
        this.animals = animals;
    }

    public List<ZookeeperDto> getZookeepers() {
        return zookeepers;
    }

    public void setZookeepers(List<ZookeeperDto> Zookeepers) {
        this.zookeepers = Zookeepers;
    }
    
    public String toString() {
    	return "TimeStamp :"+timeStamp+" Tick: "+tick+animals.get(0).toString();
    }
    
    public String printSizes() {
    	return "Tick: "+tick+" Animal Size: "+animals.size()+" Zookepers :"+zookeepers.size()+" Cells: "+cells.size();
    }
    
    
}
