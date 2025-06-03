package Services;


import Models.Dtos.BotCommandDto;
import Models.Dtos.GameStateDto;
import utilities.Point;
import utilities.UtilFunctions;

import java.util.ArrayList;
import java.util.UUID;

public class BotService {
    private UUID botId;
    private GameStateDto gameStateDto;
    private ArrayList<Point> pathToPellet = new ArrayList<>();
    private int index = 0;

    public BotService() {
    }

    public GameStateDto getGameStateDto() {
        return gameStateDto;
    }

    public void setGameStateDto(GameStateDto gameStateDto) {
        this.gameStateDto = gameStateDto;
    }

    public UUID getBotId() {
        return botId;
    }

    public void setBotId(UUID botId) {
        this.botId = botId;
    }

    public BotCommandDto processState(GameStateDto gameState) {
        //Add your bot logic here
    	GraphTraversal path = new GraphTraversal();
    	Point curr = UtilFunctions.getAgentPoint(gameState.getAnimals(), this.getBotId());
    	
    	if(curr.equals(null)) {
    		return null;
    	}
    	//Point move = UtilFunctions.basicMove(gameState.getCells(), curr);
    	if(pathToPellet.isEmpty() || index == -1 || !UtilFunctions.isStillValid(gameState.getCells(), pathToPellet.get(index))) {
    		pathToPellet = path.findPathToNearestPellet(gameState.getCells(), curr);
    		index = pathToPellet.size()-1;
    	}
    	
    	/*if(move.x == -100) {
    		return null;
    	}*/
    	
    	
    	
    	//System.out.println("Current: "+curr.toString()+"Move to: "+move.toString()+" Cell content: "+gameState.getCells().get(UtilFunctions.getElementIndex(51, move.x, move.y)).getCellContent());
    	BotCommandDto command = new BotCommandDto();
    	command.setAction(pathToPellet.get(index).getDirection());
    	index--;
        return command;
    }
}
