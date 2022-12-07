/*
Rashaan Lightpool
12/06/2022
CS 145
Final Assignment
Main.java
 */

package wcc.cs145.rashaanlightpool.todolist;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.control.*;

public class Main extends Application {
    private ToDoList toDoList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        toDoList = new ToDoList();

        // Create the ListView element of the UI
        ListView<Task> taskListView = new ListView<>();
        toDoList.sortTasks();
        for (Task task : toDoList.getTasks()) {
            taskListView.getItems().add(task);
        }

        // Set the cell factory for the ListView
        taskListView.setCellFactory(list -> {
            // Create a new ListCell
            ListCell<Task> cell = new ListCell<>();

            // Set the text of the cell to the result of the toString method of the Task
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                Task task = cell.getItem();
                return task != null ? task.toString() : "";
            }, cell.itemProperty()));

            // Set the text color of the cell based on the value of the isComplete property
            cell.textFillProperty().bind(Bindings.createObjectBinding(() -> {
                Task task = cell.getItem();
                if (task != null) {
                    return task.isComplete() ? Color.ORANGE : Color.DARKRED;
                } else {
                    return Color.BLACK;
                }
            }, cell.itemProperty()));

            return cell;
        });

        // Create the buttons and text field
        Button addTaskButton = new Button("        Add Task        ");
        Button removeTaskButton = new Button("     Remove Task    ");
        Button markTaskCompleteButton = new Button("Mark Task Complete");
        TextField taskDescriptionTextField = new TextField();
        taskDescriptionTextField.setPromptText("Enter new task here");

        // Handle user input to add a task.
        addTaskButton.setOnAction(event -> {
            String description = taskDescriptionTextField.getText();
            if (!description.isEmpty()) {
                Task task = new Task(description);
                toDoList.addTask(task);
                taskListView.getItems().setAll(toDoList.getTasks());
                taskDescriptionTextField.clear();
            }
        });

        // Handle user input to add a task using the return key.
        taskDescriptionTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String description = taskDescriptionTextField.getText();
                if (!description.isEmpty()) {
                    Task task = new Task(description);
                    toDoList.addTask(task);
                    taskListView.getItems().setAll(toDoList.getTasks());
                    taskDescriptionTextField.clear();
                }
            }
        });

        // Handle user input to remove a task.
        removeTaskButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                toDoList.removeTask(selectedTask);
                taskListView.getItems().remove(selectedTask);
            }
        });

        // Handle user input to mark a task as complete.
        markTaskCompleteButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                toDoList.markTaskComplete(selectedTask);
                taskListView.getItems().setAll(toDoList.getTasks());
                taskListView.refresh();
            }
        });

        // Handle window close request to save the tasks.
        stage.setOnCloseRequest(event -> toDoList.saveTasks());

        // Organize the UI elements
        HBox buttonContainer = new HBox(addTaskButton, removeTaskButton, markTaskCompleteButton);
        VBox root = new VBox(taskListView, taskDescriptionTextField, buttonContainer);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
