package org.example;

import Models.Dtos.BotCommandDto;
import Models.Dtos.CellDto;
import Models.Dtos.GameStateDto;
import Services.BotService;

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
	static boolean allow = true;
	
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

        String nickname = environmentNickname != null ? environmentNickname : "ResetJavaBot";
        
        

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
        	
        	
        	System.out.println(gameStateDto.toString());
        }, GameStateDto.class);
        

        hubConnection.start().blockingAwait();

        System.out.println("Registering with the bothub...");
        hubConnection.send("Register", token, nickname);

        //Gson gsonObj = new Gson();
        
        while (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            GameStateDto gameState = botService.getGameStateDto();
            
            if (gameState == null) {
            	System.out.println("NULL..");
                continue;
            }
            
           
            commandDto = botService.processState(gameState);
           // System.out.println(gsonObj.toJson(commandDto));
            hubConnection.send("BotCommand", commandDto);
            
           
        }

        hubConnection.stop();
    }
}