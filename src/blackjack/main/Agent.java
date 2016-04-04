package blackjack.main;
import java.util.*;

import blackjack.model.*;

public class Agent {
	double epsilon;
	double discount;
	double alpha;
	int numTraining;
	Dealer dealer;
	HashMap<Pair,Double> qvalues;//=new HashMap<Pair,Double>();
	
	public Agent(int numDecks,double e,double d, double a,int numTraining){
		this.epsilon=e;
		this.discount=d;
		this.alpha=a;
		this.numTraining=numTraining;
		this.dealer=new Dealer(numDecks,true);
		this.qvalues=new HashMap<Pair,Double>();
	}
	
	public int[] getState(){
		int[] state=new int[4];
		state[0]=this.dealer.getPlayerValue();
		state[1]=(this.dealer.getPlayerAceFlag()) ? 1 : 0; 
		state[2]=this.dealer.getDealerValue();
		state[3]=(this.dealer.getDealerAceFlag()) ? 1 : 0;
		return state;
	}
	
	public int getAction(int[] state){
		int[] legalActions=this.getLegalActions(state);
		      
		// get next next boolean value 	      
		if(Math.random() > this.epsilon){
			Random randomG = new Random();
			int index = randomG.nextInt(2);
	        int action = legalActions[index];
			return action; 
		}
		return this.getPolicy(state);
	}

	public int getPolicy(int[] state) {
		// TODO Auto-generated method stub
		double maxValue = -99999.0;
		int maxAction=0;
		double actionValue;
		int[] legalActions=this.getLegalActions(state);
		for(int i=0;i<2;i++){
			actionValue=this.getQValue(state,legalActions[i]);
			if(maxValue < actionValue){
				maxValue = actionValue;
				maxAction = legalActions[i];
			}
		}
		return maxAction;
	}
	
	public double getValue(int[] state) {
		// TODO Auto-generated method stub
		double maxValue = -99999.0;
		int maxAction=0;
		double actionValue;
		int[] legalActions=this.getLegalActions(state);
		for(int i=0;i<2;i++){
			actionValue=this.getQValue(state,legalActions[i]);
			if(maxValue < actionValue){
				maxValue = actionValue;
				maxAction = legalActions[i];
			}
		}
		return maxValue;
	}
	
	

	public double getQValue(int[] state, int action) {
		// TODO Auto-generated method stub
		Pair pair=new Pair(state,action);
		if(this.qvalues.get(pair) == null)
			return 0.0;
		return this.qvalues.get(pair);
	}

	public int[] getLegalActions(int[] state) {
		// TODO Auto-generated method stub
		int[] a=new int[2];
		a[0]=1;
		a[1]=2;
		return a;
	}
	
	public void update(int[] state,int action,int[] nextState,int reward){
		double qvalue=this.getQValue(state, action)+this.alpha*( reward + this.discount * this.getValue(nextState) - this.getQValue(state, action) );
		Pair pair=new Pair(state,action);
		this.qvalues.put(pair, qvalue);
	}
	
	public void setEpsilon(double e){
		this.epsilon=e;
	}
	
	public void setAlpha(double a){
		this.alpha=a;
	}

}


