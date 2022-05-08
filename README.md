# Escape Room

## A Choose-Your-Path Game 

***What will the application do?***

The application will provide users with a series of scenarios, emulating a character stuck in a room, forced
to use the items around them to escape. Users will pick one out of two options, dictating what they want their character 
to do. The next scenario you are faced with will depend on your answer to the previous. Throughout each scenario, 
you will encounter items that can be added into your inventory to aid your escape. Depending on your choice of 
actions, users will either successfully escape the room or fail. 

***Who will use it?***

This application will be used by those looking for a simple, fun activity to pass their time. Choose-your-path games 
are of interest to many people because it allows the user to make their own decisions and have control over their
character. It is fun to explore the various paths you will embark on depending on the answer you pick. 

***Why is this project of interest to you?***

Growing up, I played a multitude of choose-your-path games. I find their creativity and versatility really appealing 
and to this day, I find myself searching for more. I aim to dedicate my project towards an application I would enjoy 
and play myself. 

## User Stories
- As a user, I want to be able to view all scenarios of the game
- As a user, I want to be able to pick an option
- As a user, I want to be able to advance to the next scenario depending on my previous answer 
- As a user, I want to be able to add items into my inventory
- As a user, I want to be able to save the state of my game to file 
- As a user, I want to be able to load the state of my game from file, to return to a previous checkpoint

## Phase 4: Task 2
![](../EventLog.png) 

## Phase 4: Task 3
- To improve the design of my code if I had more time, I would separate my GUI class into several classes. 
  For example, I would have a "Panel" class for panels, "Button" for buttons, "Label" for labels, and more. 
  This would adhere to the single responsibility design principle, ensuring that each class has one function. 
  In addition, it would make my code easier to read and decrease the amount of clutter and disorganization in my 
  GUI class.

- Following the same concept, I would also move all my ActionListener classes, ex: "InitialScenarioOptions", 
  "SaveLoadOptions", that are in GUI all into 1 class. 

- I would also implement some switch statements, to replace many of the if statements I have, for efficiency. 



