package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class TaskController {
    private final DbService service;

    @Autowired
    public TaskController(DbService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public Long createTask(@RequestBody TaskDto taskDto) {
        return service.saveTask(taskDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {
        return service.getAllTasks();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return service.getTaskById(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public Long updateTask(@RequestBody TaskDto taskDto) {
        return service.saveTask(taskDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        service.deleteTaskById(taskId);
    }
}
