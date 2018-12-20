package com.crud.tasks.controller;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("The task with the given ID doesn't exist!");
    }

}
