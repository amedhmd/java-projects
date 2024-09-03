package boxassignment;

public class MatchBox extends Box {
    double weight;

    public MatchBox(double w, double h, double d) {
        super(w, h, d);
    }

    public double calculateWeight() {
        weight = (width * height * depth) * 03611;
        return weight;
    }

    public void getVolume() {
        System.out.println("width of MatchBox is: " + width);
        System.out.println("height of MatchBox is: " + height);
        System.out.println("depth of MatchBox is: " + depth);
        System.out.println("weight of MatchBox is: " + weight);
    }
}