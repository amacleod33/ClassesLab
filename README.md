# Week 05 Lab

## Instructions
Fork this repository into the group given to you by the instructor. Once forked, clone it to work with it in your computer.

This repository has a Gradle project under the folder named `Gradle`. Import this folder into Eclipse as a Gradle project. Write your implementation files in its `src/main/java` folder. JUnit test files are given to you in the `src/test/java` folder. Use these files to validate your implementation.

Commit and push your work as often as possible. When pushed, your project will be automatically deployed to [Web-CAT](https://web-cat.cs.vt.edu) for grading. You can push as many times as you want. The last push before the lab is due represents your final submission.

## Exercise 1
### REQUIRED Class NAME: Variable
A variable is an an object holding a string identifier and an integer data value.

The class has the following methods:
- `public Variable(String id, Integer data)`: constructor; it initializes fields with the given values.
- `public String getIdentifier()`: getter; it returns the variable's identifier.
- `public void setIdentifier(String id)`: setter; it initializes the variable's identifier.
- `public Integer getData()`: getter; it returns the variable's data.
- `public void setData(Integer data)`: setter; it initializes the variable's data.
- `public String toString()`: getter; it returns the string representation of the variable. The string is formatted as "[*id*,*data*]", where *id* and *data* are the variable's id and data.

## Exercise 2
### REQUIRED Class NAME: Alu
The (imaginary) ASP Virtual Machine (AVM) has an Arithmetic Logic Unit (ALU) with the functions listed in the table below.

| OPCODE | OPERAND1 | OPERAND2 | MEANING | EXAMPLE | RESULT |
| ------ | -------- | -------- | ------- | ------- | ------ |
| +      | N        | M        | N + M   | 5 + 2   | 7      |
| -      | N        | M        | N - M   | 5 - 2   | 3      |
| *      | N        | M        | N * M   | 5 * 2   | 10     |
| /      | N        | M        | N / M   | 5 / 2   | 2      |
| %      | N        | M        | N % M   | 5 % 2   | 1      |

These functions are handled by the static method below
- `public static Integer eval(String[] x)`: it evaluates the expression given in argument x. This array argument has three elements: x[0] and x[2] are integer operands; and x[1] is the operator, which can be one of `+`, `*`, `-`, `*`, `/`, `%`. The method returns *null* if x[1] is not one of these five operators.

Examples
```markdown
Given String[] x == {1 , '+', 3}
When Integer val = eval(x);
Then val == 4
```
```markdown
Given String[] x == {3, '%', 2}
When Integer val = eval(x);
Then val == 1
```
In addition, no objects of this class should be created. To prevent this from happening, a default no-arg constructor must be declared with private access, as follows:
```java
private Alu() {
    // does not allow instantiation
}
```

## Exercise 3
### REQUIRED Class NAME: Mu
The ASP Virtual Machine (AVM) has a Memory Unit that stores `Variable` objects. To this end implement the Memory Unit class. Objects of this class must be able to store up to 10 Variable objects. You **must use an array** (not a list) to store these objects.

This class implements the following methods:
- `public Mu()`: constructor; it initializes the array of variables (aka memory) to all empty.
- `public int capacity()`: getter; it returns the number of variables that can be stored in memory.
- `public Variable fetch(Integer adr)`: getter; it returns the variable object stored at the given address; or *null* if no variable is at that location or if the address is not a valid address. A valid address ranges from [0..9].
- `public Boolean store(Variable v, Integer adr)`: setter; it stores a variable at the given address. It replaces a variable if one exists at that location. The method returns true if the variable can be stored; false otherwise.
- `public int used()`: getter; it returns the number of memory spaces currently occupied by variables.
