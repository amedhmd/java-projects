package textcollage;


public class Sample {

	public void switchMethod(String input){
        switch(input){
            case "apple":
                System.out.println("sauce");
                break;
            case "orange":
                System.out.println("julius");
            case "banana":
                System.out.println("split");
                break;
            case "pear":
                System.out.println("juice");
            default:
                System.out.println(input);
        }
	}
}