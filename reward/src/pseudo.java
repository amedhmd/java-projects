package unit7;

import java.util.ArrayList;

public class pseudo {
	
	static int steps = 0; static States current_State;
	static ArrayList<States> current_neigbor_States = new ArrayList<States>(); static ArrayList<Rewards> current_neigbor_Rewards = new ArrayList<Rewards>(); static ArrayList<Actions> action_tracks = new ArrayList<Actions>();
	static int total_rewards = 0;
	static enum States {
		grid_row1_column1("Start State"), // the Start State 
		grid_row1_column2("Grid(row 1, column 2) State"), // 
		grid_row1_column3("Blocked State"), // the Blocked State 
		grid_row2_column1("Grid(row 2, column 1) State"), //
		grid_row2_column2("Grid(row 2, column 2) State"), //
		grid_row2_column3("Grid(row 2, column 3) State"), // 
		grid_row3_column1("Fire State"), // the Fire State 
		grid_row3_column2("Grid(row 3, column 2) State"), // 
		grid_row3_column3("Diamond State"); // the Diamond State

		private final String value;

		private States(String value) {
		this.value = value;
		}
	}	
	static enum Actions {
		Move_Up, // 
		Move_Down, // 
		Move_Left, // 
		Move_Right, //
		Job_Done // an action trigger that specifies that the desired goal is achieved

	}
	
	static enum Rewards {
		for_moving_Right_to_Diamond(11), // positive reward that will fetch 11 points
		for_moving_Up_to_Diamond(10), // positive reward that will fetch 10 points
		
		for_moving_Left_to_Fire(-11), // Negative reward that will punish -10 points
		
		for_moving_Up_to_Fire(-10), // Negative reward that will punish -10 points

		for_moving_Down_to_Blocked(-3), // Negative reward that will punish -2 points

		for_moving_Right_to_Blocked(-2), // Negative reward that will punish -2 points

		for_moving_Up(1), // positive reward that will fetch 1 points 

		for_moving_Down(-1), // Negative reward that will punish -1 points
		for_moving_Right(2), // positive reward that will fetch 2 points
		for_moving_Left(-2); // Negative reward that will punish -2 points

	private final int value;

	private Rewards(int value) {
	this.value = value;
	}

	}
	/**
	*	This method instantiate the program by calling the start_the_agent_moves.
	*
	*	@param args
	*/
	public static void main(String[] args) { start_the_agent_moves1();

}
	private static void start_the_agent_moves1() {
		// TODO Auto-generated method stub
	}
	
	/**
	*	This method mainly start the agent at initial state. it sets and saves the
	*	current state and the neighboring states to the knowledge base and then call
	*	the function/method that take decision of what action to take based on the
	*	Markov Decision Process algorithm.
	*/
	private static void start_the_agent_moves() {
	// set the current state to the start state current_State = States.grid_row1_column1;

	// gather neighboring states gather_neigboring_States_and_Rewards(current_State);

	// Take decision based on Markov Decision Process take_decision_based_on_Markov_Decision_Process();
	}
	/**
	*	This method gathers neighboring state of the agent by observation
	and then
	*	save the states to the knowledge base for Markov Decision Process.
	*
	*	@param s : is the current state of the agent.
	*/
	private static void gather_neigboring_States_and_Rewards(States s) { 
		current_neigbor_States = new ArrayList<States>(); current_neigbor_States.clear();
	}
}

	current_neigbor_Rewards = new ArrayList<Rewards>();
	switch (s) {
	case grid_row1_column1:
		// the agent can only move either to the up or right 
		
		current_neigbor_States.add(States.grid_row2_column1);
	// up:

		current_neigbor_Rewards.add(Rewards.for_moving_Up); 
		current_neigbor_States.add(States.grid_row1_column2);
	// right:

		current_neigbor_Rewards.add(Rewards.for_moving_Right);
		break;
		case grid_row1_column2:
			// the agent can only move either to the left, up, right

			current_neigbor_States.add(States.grid_row1_column1);
	 
	// left: Remember this is the Start state

		current_neigbor_Rewards.add(Rewards.for_moving_Left); 
		current_neigbor_States.add(States.grid_row2_column2);
	 
	// up:
	 

		current_neigbor_Rewards.add(Rewards.for_moving_Up); 
		current_neigbor_States.add(States.grid_row1_column3);
	 
	// right: Remember this is the Blocked state

	current_neigbor_Rewards.add(Rewards.for_moving_Right_to_Blocked);
			break;
		case grid_row1_column3:
	// the agent cannot enter this state since it is blocked and cannot pass
	// The agent must decide what neighbor to move to that is nearest to the blocked
	// state. This decision is made my the MDP after observation that the state is
	// blocked
	// Hence, the Neighbor list here is empty. The core reason is that the agent
	// cannot
	// pass through.
			break;
		case grid_row2_column1:
	// the agent can only move either to the up, right, or
	down

			current_neigbor_States.add(States.grid_row3_column1);

	// up: Remember this is the Fire state

	current_neigbor_Rewards.add(Rewards.for_moving_Up_to_Fire); 
			current_neigbor_States.add(States.grid_row2_column2);
	 
	// right:
	 

	current_neigbor_Rewards.add(Rewards.for_moving_Right); 
	current_neigbor_States.add(States.grid_row1_column1);
	 
	// down: Remember this is the Start state

			current_neigbor_Rewards.add(Rewards.for_moving_Down);
			break;
		case grid_row2_column2:

	}
	}
}

