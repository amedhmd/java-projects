package boxassignment;

public class Box {
	double width;
	  double height;
	  double depth;

	  // This is an empty constructor
	  public Box() {
	    ;
	  }

	  // This is a constructor with attributes
	  public Box(double w, double h, double d) {
	    width = w;
	    height = h;
	    depth = d;
	  }

	  public void getVolume() {
	    System.out.println("Volume is : " + width * height * depth);
	  }

}
