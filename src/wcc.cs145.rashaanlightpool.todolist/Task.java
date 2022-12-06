/*
Rashaan Lightpool
12/06/2022
CS 145
Final Assignment
Task.java
 */
package wcc.cs145.rashaanlightpool.todolist;

public class Task {
    private final String description;
    private boolean isComplete;

    // 1 argument constructor
    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    // 2 argument constructor
    public Task(String description, boolean isComplete) {
        this.description = description;
        this.isComplete = isComplete;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public String toString() {
        if (isComplete) {
            return description + ", Complete";
        } else {
            return description;
        }
    }
}
