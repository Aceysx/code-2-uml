# Number Guess Game（Uniting Test Practice）

## Business Requirement:

This is a number guess game. The game has four lattice, each grid has a 0 to 9 number, any two lattice figures are not the same. You have 6 chances to guess. If your guessing is correct, the result is win, otherwise fail. Each time to guess you need to enter the order of four numbers, the program will be given xAxB feedback according to the guess. The number in front of "A" represents that the numbers and the positions are both correct, the number in front of "B" represents that the number is correct but the position is wrong.
 

**Example**：

> Answer:1 2 3 4， Due to the different input，we will get the different output:
 
```
Input　　    Output             Instruction
1 5 6 7      1A0B                 1 correct
2 4 7 8      0A2B                 2 and 4 wrong position 
0 3 2 4      1A2B                 4 correct，2 and 3 wrong position
5 6 7 8      0A0B                 all wrong
4 3 2 1      0A4B                 4 numbers position wrong
1 2 3 4      4A0B                 win, all correct
1 1 2 3    Wrong Input，Input again
1 2        Wrong Input，Input again
```
 

The answer is randomly generated at the beginning of the game. The input has only 6 chances, and at each guess, the program should display the result of the current guessed number like 0A1B, as well as all the previously guessed numbers and results for this player's reference. 
In typing your guess, separate the numbers with a space.
(User command line as the user interface)

## Challenges in the Exercise:

1. Isolate uncontrollable parts in the program.
2. Understand the logic of code what has been implemented.
3.	Do not interfere with thinking because of these uncontrollable parts like random number generator module, it could result in some logic missing.
4.	 Strange business logic may be hidden behind the demand.
5.	 Test Double for implementation in different scenario unit test.
6.	 Apply mockito in right way.
7.	 Test the chronological order.
8.	 Analyze the boundary condition on time dimension when writing unit test.
9.	 Analyze the test cases and design.
10.	 Give the meaningful names for each tests.
11.	 Unit Test Structure should flow Given When Then process.
12.	 Understand Java 8 Stream API.

## Practice Requirements:

1. Analysis the interfaces of each classes, understand the relationship of all classes.
2. Design and write test cases.
3. Do the unit test refactor with small steps.
4. Use Junit for test implementation, and Mockito for dependency isolate around different modules.
5. Named Meaningful name for unit tests and methods.
6. The Unit test should cover all core business logic.
7. Code commit by small steps with meaningful comment.
8. Coding by IDE shortcuts.

## Deliverable:
Please push your deliverable repo to the site that the trainer will tell you.  The Repository should includes：
a)	Unit Test for core business modules.
b)	Unit Test cases.

## How to Begin：

get the practice repository:
```
 git clone https://github.com/ThoughtWorks-School-Showcase/unit-test-java
```

Stack Initial and build:

```
Mac/Linux: ./gradlew idea   
Whindows:  gradlew.bat idea  
```
```
Mac/Linux: ./gradlew clean build   
Whindows:  gradlew.bat clean build 
```

Test:
```
Mac/Linux: ./gradlew clean test 
Whindows:  gradlew.bat clean test
```

## Learn Resourse(Chinese Version):

1. [任务分解](https://www.zybuluo.com/jtong/note/504192)
2. [Gradle基础教程](http://tutorials.jenkov.com/gradle/gradle-tutorial.html)
3. [Java 基础](http://www.runoob.com/java/java-tutorial.html)
4. [Git 参考手册](http://gitref.org/zh/index.html)
5. [Junit](http://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)
6. [Mockito](http://site.mockito.org/)
