#Number: ADR-01

##Date: 
    07/28/2022

##Title:
    Creating priority table for tasks

##Context(Why):
    Each task need to have a priority set i.e., high, low and medium. To reduce the storage and improve performance, we created an enum table

##Decision(What/How):
    As storing high, medium and low is redundant in each row and creating three columns for the same in tasks table didn't seem efficient, we decided to create a priority enum with three values set. With this, in tasks we can store only integers rather than characters.

##Status:
    Accepted

##Consequences:
     Minimizing storage occupied and improving performance when tasks were retrieved based on priority.
