package randomsentences;

/**
 * A program that implements BNF rules to generate random sentences.
 * 
 * The program generates and outputs one random sentence every three
 * seconds until it is halted (for example, by typing Control-C in the
 * terminal window where it is running).
 * 
 * The rules that this program follows are:
 * 
 * <sentence> ::= <simple_sentence> [ <conjunction> <sentence> ]
 * 
 * <simple_sentence> ::= this is a <adjective> project <conjunction> 
 * <noun_phrase> <verb_phrase>
 * 
 * <noun_phrase> ::= <proper_noun> | 
 * <determiner> [ <adjective> ]. <common_noun> [ who <verb_phrase> ]
 * 
 * <verb_phrase> ::= <intransitive_verb> | 
 * <transitive_verb> <noun_phrase> |
 * is <adjective> |
 * believes that <simple_sentence>
 * 
 * Rules for nouns, verbs, conjunctions, etc. are implemented by arrays.
 *   
 * The program generates and outputs one random sentence every three
 * seconds until it is halted (for example, by typing Control-C in the
 * terminal window where it is running).
 * 
 */
public class RandomSentences {
	// Declaration of the seven rules used by each subroutine.
		static final String[] properNoun = {"Fred","Jane","Richard Nixon","Miss America"};
		static final String[] determiner = {"a","the","every","some"};
		static final String[] adjective = {"big","tiny","pretty","bald"};
		static final String[] commonNoun = {"man","woman","fish","elephant","unicorn"};
		static final String[] intransitiveVerb = {"runs","jumps","talks","sleeps"};
		static final String[] transitiveVerb = {"loves","hates","sees","knows","looks for",
													   "finds"};
		static final String[] conjunction = {"and","or","but","because"};
		
		// Main routine using for loop to iterate the subroutine 20 times, and writing 20 setences
		
		public static void main(String[] args) {
			
			for(int i = 1; i < 21; i++) {
				System.out.print(i +"-) ");
				Sentence();
				System.out.print(".");
				System.out.println();
			}
		}
		
		/**
		 * The Sentence subroutine follow the main rule calling the SimpleSentence subroutine and
		 * with a 0.20% chance to include a conjunction and recursively call itself again., potentially
		 * building longer sentences.
		 */
		
		static void Sentence() {
			
			SimpleSentence();
			
			if (Math.random() > 0.80) {
				System.out.print(" ");
				RandomSelectItem(conjunction);
				System.out.print(" ");
				Sentence();
			}
			
		}
		
		// integrating the NounPhrase and VerbPhrase subroutines
		
		static void SimpleSentence( ) {
			NounPhrase();
			VerbPhrase();
		}
		
		//building root phrase with the rules from the NounPhrase

		static void NounPhrase() {
			
			int switchSelect = RandomIntFromInterval(1,2);
			switch (switchSelect) {
				
				case 1: 	RandomSelectItem(properNoun);
						System.out.print(" ");
						break;
					
				case 2: 	RandomSelectItem(determiner);
						System.out.print(" ");
						
						// 40% chance to run this if condition.
						if (Math.random() > 0.4) {
							RandomSelectItem(adjective);
							System.out.print(" ");
						}
				
						RandomSelectItem(commonNoun);
						System.out.print(" ");
						
						// 60% chance to run this if condition.
						if (Math.random() > 0.6) {
							System.out.print("who ");
							VerbPhrase();
							System.out.print(" ");
						}
						break;
			}
			
			
		}
		// part of the root SimpleSentence subroutine
		
		static void VerbPhrase() {
			
			int switchSelect = RandomIntFromInterval(1,4);
			switch (switchSelect) {
				
				case 1: RandomSelectItem(intransitiveVerb);
						break;
				
				case 2: RandomSelectItem(transitiveVerb);
						System.out.print(" ");
						NounPhrase();
						break;
				
				case 3: System.out.print("is ");
						RandomSelectItem(adjective);
						break;
			
				case 4: System.out.print("believes that ");
						SimpleSentence();
						break;		
			}
			
		}
		
		// This is method which picks random items from an array of strings used in the sentece subroutines
		
		static void RandomSelectItem(String[] listOfStrings) {
			
			int item = (int)(Math.random()*listOfStrings.length);
			System.out.print(listOfStrings[item]);
			
		}
		
		//This method generates random integers
		 
		static int RandomIntFromInterval(int min, int max) {
		
			int result = (int)(Math.random()*(max-min+1)+min);
			return result;
			
		}
		
	}
