# COMP261 Assignment 3 - Flow Networks and min-cut 

**Due: Wed 11 May 2022 (Week 9)**

The focus of this assignment is to demonstrate an understanding of maximum flow problems and the relationship between maximum flow in a flow network and min-cut in the corresponding graph. 

Given a flow network you will be required to find maximum flow through the network using an implementation of Ford fulkerson method. Next, you will be required to find the min-cut exhibited by your algorithm and verify Max-flow min-cut theorem. 

**Note from the lecture slides: If f is a flow, and (S, T) an s-t cut whose capacity equals the value of f, then f is a max flow and (S, T) is a min cut.**

The assignment repository contains:
* A folder of the template code.
* A folder with some test case flow networks. 

**Note: During evaluation, we may use other test cases to verify the correctness of your algorithm. Try evaluating your implementation against different possible scenarios (in addition to those included with in the assignment repository).**

## Submission
The submission will be using the ECS submission systems and we would strongly prefer the use of the ECS git submission so that submission status of the files can be tracked. We will reuse the folder for assignment-1 https://gitlab.ecs.vuw.ac.nz/course-work/comp261/2022/assignment-1/<username> with your username.  Fork this repository into that area by selecting the 'fork' in the top left of this page. Select the repository area to fork to, to be the repository above. Once you have forked the assignment, you can clone the assignment using the blue 'Clone' button to get a copy of your project on your local machine. When you have working code push it to the fork you cloned the project from, and then go to the submit page and select the Assignment 3 repository to submit from the gitlab option.  This will also put a tag in the repository to show when you submitted.  You can check the tags on the left bar 'Repository->Tags'.  You can still submit a .zip of the code but if there are any issues or we want to give you partical credit for fixing last minute issues that cannot be done with the .zip file.

The submission will include:
* The Java files and the data files needed for the project - if submitting by gitlab repo this is included in the submission
* A Readme.md in the root file replacing this with a description of which parts of the assignment you completed
  
The places to Edit are listed with TODOs.  

**You can change any of the code to make it easier for you to use and you can add any imports you want.**


### Core: Ford-fulkerson [0-45]
1. [20] Using DFS/BFS find a flow between source and sink nodes.
    - [10] Find and display the flow value.
    - [10] Find and display the flow path.

2. [25] Implement the Edmond-karp algorithm.
	- [15] Find and display the maximum flow through a given flow network.
	- [10] Find and display all the Augmentation paths identified in the flow network. 


### Completion: s-t cut and Vehicle-type sensitivity[45-80]
1. [25]: Identify the s-t cut exhibited by your implementation of the Ford-fulkerson method.
	- [15] Find and display the s-t cut.
	- [10] Find and display the capacity of the s-t cut
        
2. [10]: Vehicle-type sensitivity [10].
    - [10] Allow your program to take "type" of the vehicle as input- Update your code to include "type" of vehicle as input. 
Based on the "type" update the capacity of **all** the edges of the network graph using the following formula : Bus - x, Cars - 2x, Moped - 4x.

	
### Challenge[80-100]: Extend to allow different edge "types"
Create another version of your algorithm - "Extended-FF" which allows different edges to support different vehicle types to cater to scenarios where some roads are only suitable for a specific type of a vehicle. 
Your implementation should provide the maximum flow in terms of _number of vehicles._

### Example
 **1. Sample graph with Max-flow and APs.**

![Example Graph](/images/Example%20Graph.png)

**2. Sample graph with Min-cut capacity.**

![Min-cut](/images/Min-cut.png)

### File description
Main parts of Assignment 3 needing updates: 

- FordFulkerson.java - This has the TODO tasks for finding the maximum flow, augmentation paths and min-cut.
- GraphController.java - This has TODOs for displaying the results.

For each method in FordFulkerson.java a return type has been specified in the skeleton code. You can change the return type if you wish to - as long as the output is in the same format as is expected.

Do not need to be changed(but can be). Some updations/deletions have been made to these files since assignment 2 to sketch assignment 3(important changes are listed)

- Edge.java
- Graph.java: this has been updated to include a method that return a node given its int Id.
- Main.java
- MapView.fxml
- Node.java: this has been updated to include a function that returns neighbours of a given node.
- Parser.java
- Projection.java
