package unit5;
public class TruthTable {
    
    public static boolean conjuction(boolean p, boolean q){
        
     return (p && q);
    }
    
    public static boolean conjuction(boolean p, boolean q, boolean r) {
        
     return (p && q && r);
    }
    
      public static boolean implication(boolean p, boolean q){
          if(p) return q;
          return true;
          
          
      }
      
      public static void main(String[] args){
          
        boolean P =true;		
		boolean Q =true;	
		boolean R =true;
		
		System.out.println("-------------------------------------------------");
		
		System.out.println("| P | Q | R |(P^Q) => R | Q => P | KB | KB => R |");
		
		System.out.println("-------------------------------------------------");
		
		for(int i=0; i<8;i++){
		    
		    boolean S = implication(conjuction(P,Q),R);
            boolean T = implication(Q,P);
            boolean KB = conjuction(Q,S,T);
            
            System.out.printf("|%-6b|%-6b|%-6b|%-11b|%-7b|%-6b|%-9b|\n",P,Q,R,S,T,KB,R,implication(KB,R));
            
           
		 
		if(i==3) P = !P;

		if(i%2 == 1) Q = !Q;
		 R = !R;
		 }

System.out.println("--------------------------------------------------");
    }
}