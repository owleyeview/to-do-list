/*
Rashaan Lightpool
12/06/2022
CS 145
Final Assignment
ToDoList.java
 */
package wcc.cs145.rashaanlightpool.todolist;

import java.io.*;
import java.util.*;


public class ToDoList {
    private List<Task> tasks;

    // Constructor
    public ToDoList() {
        this.tasks = new ArrayList<>();
        loadTasks();
    }

    // Create a comparator object that compares the isComplete property of two tasks
    private final Comparator<Task> taskComparator = (t1, t2) -> {
        if (t1.isComplete() && !t2.isComplete()) {
            return 1;
        } else if (!t1.isComplete() && t2.isComplete()) {
            return -1;
        } else {
            return 0;
        }
    };

    // Given a Task object, it is added to the list of tasks.
    // I chose BufferedWriter and BufferedReader instead of
    // Printstream and Scanner because I've learned that they
    // are more efficient, more powerful and more commonly used.
    public void addTask(Task task) {
        this.tasks.add(task);
        sortTasks();
        // update the save file
        saveTasks();
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        //update the save file
        saveTasks();
    }

    public void markTaskComplete(Task task) {
        task.setComplete(true);
        sortTasks();
    }

    public void sortTasks() {
        // Sort the tasks using the comparator object
        tasks.sort(taskComparator);
    }

    // Load tasks from tasks.txt into tasks List
    public void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String description = parts[0];
                boolean isComplete = Boolean.parseBoolean(parts[1]);
                this.tasks.add(new Task(description, isComplete));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save the tasks List to tasks.txt
    public void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                String line = task.getDescription() + "," + task.isComplete();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return this.tasks;
    }
}
