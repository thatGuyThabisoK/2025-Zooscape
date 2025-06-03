package utilities;

import java.util.List;
import java.util.UUID;

import Enums.CellContent;
import Models.Dtos.AnimalDto;
import Models.Dtos.BotCommandDto;
import Models.Dtos.CellDto;

public class UtilFunctions {
    
    public static int getElementIndex(int n, int x,int y){
    	if(x < 0 || y < 0 || x > n-1 || y > n-1) {
    		return -1;
    	}
    	return (n*x)+ y;
    }
    
    public static Point getAgentPoint(List<AnimalDto> animals, UUID id) {
    	
    	for(AnimalDto myAgent : animals) {
    		if(id.toString().contentEquals(myAgent.getId())) {
    			return myAgent.getCurrentPoint();
    		}
    	}
    	
    	return null;
    }

	public static boolean isAtSpawn(AnimalDto animalDto) {
		
		return animalDto.getSpawnPoint().equals(animalDto.getCurrentPoint());
	}
	
	public static Point basicMove(List<CellDto> graph, Point start) {
		//1 Up, 2 Down, 3 Left, 4 Right
		//up-left ; left-up; right-down; down-right
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		int[] move = {3,4,1,2};
		
		Point result = new Point(-100,-100);
		for(int i = 0; i < 4; ++i) {
			
			int index = getElementIndex(51,start.x+dx[i],start.y+dy[i]);
			CellDto neighbor = graph.get(index);
			int content = -1;
			if(index != -1) {
				content = CellContent.values()[graph.get(index).getContent()].getValue();
			}
			
			if(isTraversable(content) && neighbor.getCellPoint().equals(new Point(start.x+dx[i],start.y+dy[i]))) {
				
				result.x = start.x+dx[i];
				result.y = start.y+dy[i];
				
				result.setDirection(move[i]);
				
				System.out.println("Start: "+start.toString()+" Nextnode: "+result.toString()+" Neighbor "+neighbor.toString());
				return result;
			}
		}
		System.out.println("No free neigbor found");
		return result;
		
	}
	
	public static boolean isTraversable(int content) {
		return content == 2 || content == 0;
	}

	public static boolean isStillValid(List<CellDto> cells, Point point) {
		
		return cells.get(getElementIndex((int)Math.sqrt(cells.size()),point.x,point.y)).getContent() == 2;
		
		
	}
    
}
