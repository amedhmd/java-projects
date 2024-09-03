package programming;

public class GravityCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double  gravity =-9.81; // Earth's gravity in m/s^2

		double  initialVelocity = 0.0; // starting velocity of the object

		double  fallingTime = 10.0; // time in seconds that the object falls

		double  initialPosition = 1000.0; // Starting position in meters, the calculation will 		determine the ending position in meters

		double  finalPosition = 0.0;
		
		finalPosition = 0.5*gravity*(fallingTime*fallingTime) + fallingTime*initialVelocity + initialPosition;

		System.out.println("The object's position after " + fallingTime +" seconds is 			"+finalPosition + " m.");
	}

}
