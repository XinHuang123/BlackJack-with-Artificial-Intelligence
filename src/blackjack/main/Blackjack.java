package blackjack.main;
import java.io.*;

import java.util.HashMap;
import java.util.Scanner;

import blackjack.model.*;

public class Blackjack {
	
	
	public static void train(Agent agent){
		//Agent agent=new Agent(2,0.8,0.8,0.9,10000);
		int total = 0;
		int reward=0;
		int[] oldState;
		int action;
		int games= agent.numTraining;
		while(agent.numTraining > 0){
			agent.dealer.gameBegin();
			while(true){
				oldState = agent.getState();
				action = agent.getAction(oldState);
				if(agent.dealer.playerTurn(action)){
					break;
				}
				int[] newState = agent.getState();
				reward = 0;
				agent.update(oldState, action, newState, reward);
			}
			boolean isWin = agent.dealer.winFlag;
			if(isWin){
				reward=1;
				total +=1;
			}
			else{
				reward = -1;
			}
			agent.update(oldState, action, agent.getState(), reward);
			agent.numTraining -=1;
		}
		System.out.println("Won "+total+" out of "+games);
		System.out.println("Rate: "+ (float)total/games);
		System.out.println("Training Done");
	}
	
	public static void exploit(Agent agent){
		int total=0;
		int games=0;
		int reward=0;
		int[] oldState;
		int action;
		//Agent agent=new Agent(2,0.8,0,0.2,10000);
		agent.setEpsilon(0);
		agent.setAlpha(0.2);
		while(games < 10000){
			agent.dealer.gameBegin();
			while(true){
				oldState = agent.getState();
				action = agent.getAction(oldState);
				if(agent.dealer.playerTurn(action)){
					break;
				}
				int[] newState = agent.getState();
				reward=0;
				agent.update(oldState, action, newState, reward);
				
			}
			boolean isWin=agent.dealer.winFlag;
			if(isWin){
				reward =1;
				total +=1;
			}
			else{
				reward =-1;
			}
			agent.update(oldState, action, agent.getState(), reward);
			games +=1;
		}
		System.out.println("Won "+total+" out of "+games);
		System.out.println("Rate: "+ (float)total/games);
		//System.out.println("Training Done");
		
	}
	
	
	public static void play(Agent agent){
		//Agent agent=new Agent(2,0.8,0.8,0.9,10000);
		int total = 0;
		int reward=0;
		int[] oldState;
		int action;
		int str;
		int games= agent.numTraining;
		//agent.dealer.isSilent=false;
		while(true){
			agent.dealer.gameBegin();
			System.out.println("\nNew game begins!\n");
			while(true){
				agent.dealer.display();
				oldState = agent.getState();
				action = agent.getAction(oldState);
				System.out.println("Suggestion:"+agent.getAction(oldState));
				System.out.println("Please Enter 1 to Hit, 2 to Stand: ");
				Scanner in = new Scanner(System.in);			
				str=in.nextInt();
				//in.close();
				//int str=read();
				if(agent.dealer.playerTurn(str)){
					break;
				}
				int[] newState = agent.getState();
				reward = 0;
				agent.update(oldState, str, newState, reward);
			}
			boolean isWin = agent.dealer.winFlag;
			agent.dealer.display();
			if(isWin){
				reward=1;
				total +=1;
				System.out.println("WIN");
			}
			else{
				reward = -1;
				System.out.println("LOSE");
			}
			agent.dealer.display();
			agent.update(oldState, str, agent.getState(), reward);
			agent.numTraining -=1;
		}
		//System.out.println("Won "+total+" out of "+games);
		//System.out.println("Rate: "+ (float)total/games);
		//System.out.println("Training Done");
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Scanner in = new Scanner(System.in);			
		//int str=in.nextInt();
		//in.close();
		System.out.println(1);
		Agent agent=new Agent(4,0.7,0.8,0.9,10000);
		train(agent);
		Pair pairs=(Pair) agent.qvalues.keySet().toArray()[50];
//		System.out.println("action:"+pairs.getAction());
		exploit(agent);
		play(agent);
		

	}

}
