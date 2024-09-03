package statcalc;

public class StatCalc {
	private int count; // Number of numbers that have been entered.
	  private double sum; // The sum of all the items that have been entered.
	  private double squareSum; // The sum of the squares of all the items.

	  public void enter(double num) {
	    count++;
	    sum += num;
	    squareSum += num * num;
	  }

	  public int getCount() {
	    return count;
	  }

	  public double getSum() {
	    return sum;
	  }

	  public double getMean() {
	    return sum / count;
	  }

	  public double getStandardDeviation() {
	    double mean = getMean();
	    return Math.sqrt(squareSum / count - mean * mean);
	  }

	  public static void main(String[] args) {
	    int dataArrray[] = { 5, 7, 12, 23, 3, 2, 8, 14, 10, 5, 9, 13 };
	    StatCalc myStatCalc;
	    myStatCalc = new StatCalc();

	    for (int i : dataArrray) {
	      myStatCalc.enter(i);
	    }

	    System.out.println("Count: " + myStatCalc.getCount());
	    System.out.println("Mean: " + myStatCalc.getMean());
	    System.out.println("Standard Deviation: " + myStatCalc.getStandardDeviation());
	  }
	}