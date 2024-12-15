# adventuresinthespiderverse
 /* Program Description: The game begins with the title screen displaying the characters that are used within the game. 
 * There is Miles Morales, Peter Parker, Gwen Stacy, and Miguel O’Hara from Spiderman. This game has four levels where 
 * each level takes place in a different multiverse and has a different character associated with it. Click the level button 
 * to enter it. The character will enter through the portal and the character being used in the level will be displayed. 
 * Within the level, the characters must complete tasks in order to complete the level. Each level uses a scrolling 
 * background. The characters move with W, A, S, and D and they can also attack with J. Level 1 has 2 tasks while the 
 * rest of the levels have 3. The levels can vary where some have the player, some require using the mouse, the keys, 
 * entering information into a textfield, shooting projectiles, catching spawns from the sky, etc. Each task has its own 
 * alert which explains the instructions and reminds the users of the controls that would be used within the task. 
 * Each level also has its own song associated with it along with some sound effects throughout. In the end, the user can 
 * add their name to the list of names and their score. They can also find out what place they are on the leaderboard 
 * through binary search. Overall, the aim of the game is to successfully complete all four levels in as little time as 
 * possible. Some tasks may provide the user with three hearts, while others there is only one chance to successfully 
 * complete a task or else it is game over. 
 * 
 * Program Details: JavaFX components were utilized all throughout the program. There are buttons in the end to see 
 * the scoreboard and add the user’s name to the list. There are also buttons within the tasks such as in the code task 
 * where the user must enter the code from the screen. There are labels for the instructions as well as for the countdowns 
 * shown on many of the levels within the task. RadioButtons are used in the second task in level 1 where the user can 
 * select which enemy they would like to obtain from the database. Textfield is used in the task where the user must enter 
 * the code as a place for the user to input. Alerts with custom images are used as an error when the user tries to enter a
 * level that they have not unlocked yet. Alerts with custom buttons are used in each of the tasks to inform the user of 
 * what the instructions and controls for that task are. There are also alerts with custom images used in the codetask as 
 * well as the matchingtask to let the user know if their selection is correct. . Many different layouts are used all throughout the program 
 * such as tilepane for the menu buttons, gridpane to set up the layout of the matching task, VBox to place the scoreboard and add name button 
 * next to each other, Borderpane to format the send back task, as well as HBox and pane used as well. 1D arrays are used constantly in the program 
 * for checking if a level is complete through a boolean array, many image arrays such as in the electrotask and shoottask for 
 * the hearts, and for the dark matter object. The menu buttons also use an array. A 2D array is used in the matchingtask as 
 * the user needs to select the matching images from the square where random images are generated to each of the buttons. 
 * The bubble sort algorithm is used to sort the scores in the text file by score. Binary search from the Collections 
 * class is used to find where in the leaderboard the current user would place based on the other scores. 
 * For object oriented programming, there are about 24 classes used in this program where each task has its own class 
 * as well as many other elements such as the player and the enemy. The abstract class is Spawn where it has the general
 * methods and fields that something that spawns would need. It is inherited by ElectricitySpawn, GoodSpawn, and BadSpawn. 
 * side from this, inheritance is also used in DarkMatter which inherits the Background because the dark matter must move 
 * at the same pace as the scrolling background. Polymorphism is used for the tasks where the parent class is MiniTasks and
 * all the other class tasks inherit this class as it has the basic functionality needed for a task. The MiniTasks variable 
 * is re-instantiated every time a new task is run. Animation is used when moving the player within tasks as well as from 
 * the scrolling background. There are also many other moving parts such as enemies, spawns, and the darkmatter. 
 * Collision detection is used in many tasks such as ElectroTask, CatchTask, ShootTask, SendBackTask, and many others. 
 * They are used to detect collision between player and enemy, player and spawn, projectile and enemy, etc. The File class
 * is used to print the user’s name along with their score. Their score is the amount of time it took them to complete the 
 * full game. ArrayLists are used in ElectroTask for the electricity spawns, in ShootTask for the enemies, for the user’s 
 * score, for the user’s name, and many other places. As for sounds, there is background music in the menu screen, each level 
 * has its own background music, and then there are also sound effects sprinkled throughout the program. 
 */
