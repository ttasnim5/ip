# Taskmaster Nanami: User Guide

## Features 

### Create Tasks
Create three different types of tasks to add to a list of tasks.

You can create a ToDo task for something that has no deadlines or times attached.
You can create a Deadline task for something that has a deadline to be done by.
You can create an Event task for something that will happen between certain dates/times.

All your tasks will be saved and updated as you add to and change your list.

### Change Task Status
Mark any of the tasks as complete or incomplete as you do them.

### Search Task List
Search through your list of tasks by keywords to find specific tasks.

### Delete Tasks
Delete any task as you move through your list.

### Save Data
You task list will be updated and saved every time you make changes to it, so 
you can continue working through your list during future sessions.

## Usage

### `todo ...` - creates a todo task.

Creates a todo task and adds it to your list. Must not contain any attachments, 
denoted by a backward slash ("/").

Example of usage: 

`todo bake emma's cake`

Expected outcome: 
If done correctly, it will be added to your list. 
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» I can't store a todo like that. It only needs a description, no attachments using /.
```
Or:
```
» A todo task does not have any attachments using /. I can't store it like this.
```

### `deadline ...` - creates a deadline task.

Creates a deadline task and adds it to your list. Must contain a description body and only 
a single attachment indicating when the task must be done by, denoted with a "/by".

Example of usage:

`deadline finish midterm review sheet /by sunday night`

Expected outcome:
If done correctly, it will be added to your list.
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» The deadline is missing something. I need the task type, description, and /by attachment. Try again.
```
Or:
```
» A deadline has a /by attachment only. I can't store it like this.
```

### `event ...` - creates an event task.

Creates an event task and adds it to your list. Must contain a description body and two attachments
indicating when the task occurs, denoted with a "/to" and a "/from".

Example of usage:

`event mom's birthday party /from 6m /to 9pm`

Expected outcome:
If done correctly, it will be added to your list.
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» The event is missing information. I need the task type, description, and /to and /from attachments. Try again.
```
Or:
```
» An event has a /to attachment and a /from attachment only. I can't store it like this.
```

### `mark ...` - mark a task as complete.

Marks a task as complete based on the number directly following the keyword "mark". If the 
given task number is invalid (empty or not included in the list), the user will be informed 
and reprompted.

Example of usage:

`mark 3`

Expected outcome:
If done correctly, it will mark your task as complete in the list.
```
» I've marked [3] as completed.
```
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» That task isn't on record. Look at it again.
```
```
» If you need me to mark or unmark a task, I need a task number right after the command word.
```

### `unmark ...` - mark a task as incomplete.

Marks a task as incomplete based on the number directly following the keyword "unmark". If the
given task number is invalid (empty or not included in the list), the user will be informed
and reprompted.

Example of usage:

`unmark 3`

Expected outcome:
If done correctly, it will mark your task as incomplete in the list.
```
» I've unmarked [3] as completed.
```
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» That task isn't on record. Look at it again.
```
```
» If you need me to mark or unmark a task, I need a task number right after the command word.
```

### `delete ...` - delete a task from the list

deletes a task based on the number directly following the keyword "delete". If the
given task number is invalid (empty or not included in the list), the user will be informed
and reprompted.

Example of usage:

`delete 6`

Expected outcome:
If done correctly, it will remove the task from the list.
```
» Okay, I've scrapped [T][¤] buy carrots for dinner
```
If not done correctly, user will be informed of what went wrong and will be reprompted.
```
» That task isn't on record. Look at it again.
```
```
» If you want me to delete a task, I need a task number with it.
```

### `find ...` - find tasks in the list

searches the list of tasks for any tasks with a description body matching the keywords following "find". 
It will show the tasks if any match. If no tasks match, Nanami will show that as well.

Example of usage:

`find cook`

Expected outcome:
If found, it will present the matching tasks.
```
» Let's see...
6 [T][ ] give the cooked rice to the neighbor
9 [D][¤] cook lunch for tmm: midnight
11 [D][ ] recook the pasta from last week: monday
```
If not found:
```
» I don't know about that one. Can't find it anywhere.
```
