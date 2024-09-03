import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader; 
import java.io.IOException; 
import java.nio.file.FileSystems; 
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public final class MapColoringConstraint extends Constraint<String, String> { private String place1, place2;
public MapColoringConstraint(String place1, String place2) { super(List.of(place1, place2));
this.place1 = place1; this.place2 = place2;
}
public boolean satisfied(Map<String, String> assignment) {
// if either place is not in the assignment, then it is not
// yet possible for their colors to be conflicting
if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) { return true;
}
// check the color assigned to place1 is not the same as the
// color assigned to place2
return !assignment.get(place1).equals(assignment.get(place2));
}
public static void main (String[] args) throws NumberFormatException, IOException {
List<String> variables = new ArrayList<String>(); Map<String, List<String>> domains = new HashMap<>();
// assuming graph.txt file located in the same project folder
// if not then add the entire path
File file = new File(new File("graph.txt").getAbsolutePath().toString()); BufferedReader br = new BufferedReader(new FileReader(file));
int total_no_vertices = Integer.parseInt(br.readLine()); for (int i = 1; i <= total_no_vertices; ++i) {
String line = br.readLine();
String[] vertex_and_adjacent = line.split(" ");
for (int j = 0; j < vertex_and_adjacent.length; j++) {
// Assign the vertex vertex_and_adjacent[0] into your data structure
// Assign it's adjacent vertices from vertex_and_adjacent[1] to
// vertex_and_adjacent[length - 1] into another data structure
variables.add(vertex_and_adjacent[j]);
}
}
variables = variables.stream().distinct().collect(Collectors.toList()); for (String variable : variables) {
domains.put(variable, List.of("red", "green", "blue", "yellow", "violet", "gray", "orange"));
}
CSP<String, String> csp = new CSP<>(variables, domains); br = new BufferedReader(new FileReader(file));
for (int i = 1; i <= total_no_vertices; ++i) { String line = br.readLine();
String[] vertex_and_adjacent = line.split(" ");
for (int j = 1; j < vertex_and_adjacent.length; j++) {
// Assign the vertex vertex_and_adjacent[0] into your data structure
// Assign it's adjacent vertices from vertex_and_adjacent[1] to vertex_and_adjacent[length - 1] into another data structure
csp.addConstraint(new MapColoringConstraint(vertex_and_adjacent[0], vertex_and_adjacent[j])); // attach nodes for each starting alphabet, i.e for A- B C D
}
}
Map<String, String> solution = csp.backtrackingSearch(); if (solution == null) {
System.out.println("No solution found!");
} else {
for (Map.Entry<String, String> entry : solution.entrySet()) { System.out.println(entry.getKey() + " " + entry.getValue());
}
}
}



}
CSP.java
import java.util.ArrayList; import java.util.HashMap; import java.util.List; import java.util.Map;

public class CSP<V, D> { private List<V> variables;
private Map<V, List<D>> domains;
private Map<V, List<Constraint<V, D>>> constraints;


public CSP(List<V> variables, Map<V, List<D>> domains) {

this.variables = variables; this.domains = domains; constraints = new HashMap<>(); for (V variable : variables) {
constraints.put(variable, new ArrayList<>()); if (!domains.containsKey(variable)) {
throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
}
}
}


public void addConstraint(Constraint<V, D> constraint) { for (V variable : constraint.variables) {
if (!variables.contains(variable)) {
throw new IllegalArgumentException("Variable in constraint not in CSP");
} else { constraints.get(variable).add(constraint);
}
}
}
public Map<V, D> backtrackingSearch(Map<V, D> assignment) {
// assignment is complete if every variable is assigned (our base case)

if (assignment.size() == variables.size()) { return assignment;
}
// get the first variable in the CSP but not in the assignment
V unassigned = variables.stream().filter(v -> (! assignment.containsKey(v))).findFirst().get();
// get the every possible domain value of the first unassigned variable for (D value : domains.get(unassigned)) {
// shallow copy of assignment that we can change
Map<V, D> localAssignment = new HashMap<>(assignment); localAssignment.put(unassigned, value);
// if we're still consistent, we recurse (continue) if (consistent(unassigned, localAssignment)) {
Map<V, D> result = backtrackingSearch(localAssignment);
// if we didn't find the result, we will end up backtracking if (result != null) {
return result;
}
}
}
return null;
}

// Check if the value assignment is consistent by checking all constraints
// for the given variable against it
public boolean consistent(V variable, Map<V, D> assignment) { for (Constraint<V, D> constraint : constraints.get(variable)) {
if (!constraint.satisfied(assignment)) { return false;
}
}
return true;
}


// helper for backtrackingSearch when nothing known yet public Map<V, D> backtrackingSearch() {
return backtrackingSearch(new HashMap<>());
}
}
Constraint.java import java.util.List; import java.util.Map;

// V is the variable type, and D is the domain type public abstract class Constraint<V, D> {
The Map Coloring Problem

// the variables that the constraint is between protected List<V> variables;

public Constraint(List<V> variables) { this.variables = variables;
}


// must be overridden by subclasses
public abstract boolean satisfied(Map<V, D> assignment);
}
graph.txt
5
A B C D B A C
C A B D D A C
E


Please note- although I didn’t include the out of this code here, I am certain the code works well. You are welcome to run it yourself to confirm, thanks.

