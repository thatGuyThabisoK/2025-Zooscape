package org.example;

import Models.Dtos.AnimalDto;
import Models.Dtos.BotCommandDto;
import Models.Dtos.CellDto;
import Models.Dtos.GameStateDto;
import Services.BotService;
import utilities.Point;
import utilities.UtilFunctions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
	
	
    public static void main(String[] args) {	
    	
        Logger logger = LoggerFactory.getLogger(Main.class);
        BotService botService = new BotService();
        BotCommandDto commandDto;
        

        String environmentIp = System.getenv("RUNNER_IPV4");
        String environmentNickname = System.getenv("BOT_NICKNAME");
        UUID token = UUID.fromString(System.getenv("Token"));
        token = (token != null) ? token : UUID.fromString(System.getenv("REGISTRATION_TOKEN"));

        String ip = (environmentIp != null && !environmentIp.isBlank()) ? environmentIp : "localhost";
        ip = ip.startsWith("http://") ? ip : "http://" + ip;

        String url = ip + ":" + "5000" + "/bothub";

        String nickname = environmentNickname != null ? environmentNickname : "JavaBot";
        
        

        HubConnection hubConnection = HubConnectionBuilder
                .create(url)
                .build();

        hubConnection.on("Disconnect", (reason) -> {
            logger.info("Disconnected: {}", reason);
            hubConnection.stop();
        }, UUID.class);

        hubConnection.on("Registered", (id) -> {
            System.out.println("Registered with the runner, with bot ID: " + id);
            botService.setBotId(id);
        }, UUID.class);

        hubConnection.on("EndGame", (state) -> {
            System.out.println("Game complete");
        }, String.class);

        
        hubConnection.on("GameState", (gameStateDto) -> {
        	    
        	//Gson gsonObj = new Gson();
        	
        	//String payLoad = gsonObj.fromJson((String)gameStateDto, String.class);
        	
        	botService.setGameStateDto(gameStateDto);
        	
        	
        	//System.out.println(gameStateDto.toString());
        }, GameStateDto.class);
        

        hubConnection.start().blockingAwait();

        System.out.println("Registering with the bothub...");
        hubConnection.send("Register", token, nickname);

        //Gson gsonObj = new Gson();
        Point prevPoint= new Point(0,0);
        int sentCommand = 0;
        
        while (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            GameStateDto gameState = botService.getGameStateDto();
            
            if (gameState == null) {
            	System.out.println("null......");
                continue;
            }
            
           
            commandDto = botService.processState(gameState);
            
            if(commandDto == null) {
            	System.out.println("could not find a pellet!! at Tick "+gameState.getTick());
            	continue;
            }
            //check if it at spawn
            AnimalDto myAgent = gameState.getAnimals().get(0);
            
            /*if(gameState.getTick() == 20) {
            	printState(gameState.getCells(),myAgent.getCurrentPoint());
            	hubConnection.stop();
            	break;
            }*/
            
            if(UtilFunctions.isAtSpawn(myAgent) && !myAgent.isViable() ) {
            	
            	hubConnection.send("BotCommand", commandDto);
            	prevPoint = myAgent.getCurrentPoint();
            	sentCommand=+1000;
            	try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	continue;
            }
            
            if(myAgent.isViable() ) {
            	
            	
            	hubConnection.send("BotCommand", commandDto);
            	prevPoint = myAgent.getCurrentPoint();
            	sentCommand+=1000;
            	
            	try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	continue;
            	
            }
            
            System.out.println("Tick :"+gameState.getTick()+" not viable/not at spawn!!");
            
        }
        
        hubConnection.stop();
    }
    
    
    public static void printState(List<CellDto> cells, Point start) {
    	int colIndex = 0;
    	for(CellDto c : cells) {
    		
    		
    		
    		System.out.print((c.getCellPoint().equals(start))?"6 ":c.getContent()+" ");
    	
    		if(colIndex == limit(51,c.getX())) {
    			System.out.println();
    		}
    		
    		++colIndex;
    			
    	}	
    }
    
    public static int limit(int n, int index) {
    	return (n-1)+(n*index);
    }
    
    
    
    
}