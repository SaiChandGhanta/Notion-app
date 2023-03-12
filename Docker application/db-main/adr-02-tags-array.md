#Number: ADR-02

##Date: 
    07/28/2022

##Title:
    Creating tags array in tasks table.

##Context(Why):
    Each task can have 0 to 10 tags attached to it. 

##Decision(What/How):
    We thought of creating a tags column with array datatype inside tasks table. But when a user deletes a tag, then it must be deleted from the arrays from all the tasks which are associated with the particular task making it little complicated

##Status:
    Rejected

##Consequences:
     For now we think that it might improve performance and instead created TaskTags table
