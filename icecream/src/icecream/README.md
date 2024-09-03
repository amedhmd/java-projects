# Icecreamecones--java
a program. that reads the file icecream.dat. 
You must use TextIO methods to read the file. As you read the file, count the total number of ice cream cones that were sold. 
(This is the same as the number of lines that you read from the file.) Also count the total number of "Strawberry" cones that were sold.

At the end of the program, print out the total number of cones, the number of Strawberry cones, and the percentage of cones
that were Strawberry.

Note: To do this program, you have to know when to stop reading from the file. TextIO has a function named TextIO.eof() to 
check whether the entire file has been read. The value of this function is true if the entire file has been read. The value 
is false if there is more data in the file. You want to continue reading from the file as long as TextIO.eof() is false.

Note: Suppose that flavor is a variable of type String and you want to test whether its value is "Strawberry". To do this,
test whether flavor.equals("Strawberry"). (Do not use == to test for equality of Strings.)

Each line of the file icecream.dat is an ice cream flavor such as "Vanilla" or "Strawberry." A line represents the sale 
of one ice cream cone of the given flavor.
