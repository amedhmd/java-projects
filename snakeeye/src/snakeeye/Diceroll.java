package snakeeye;

public class Diceroll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Declare variables
        boolean snakeEyes;
        int countSnakeEyes;
        int die1;
        int die2;
        // Initialize variables
        countSnakeEyes = 0;
        snakeEyes = false;
        do {
          die1 = (int)(Math.random()*6)+1;
          die2 = (int)(Math.random()*6)+1;
          countSnakeEyes += 1;
          if (die1 == 1 && die2 == 1) {
	      snakeEyes = true;
   	  }
    	} while ( !snakeEyes);
    	TextIO.putln("Snake Eyes after "+countSnakeEyes+" rolls of the dice");
	}

}
