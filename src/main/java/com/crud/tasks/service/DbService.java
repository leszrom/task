package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public DbService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Long saveTask(final TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        return taskRepository.save(task).getId();
    }

    public List<TaskDto> getAllTasks() {
        return taskMapper.mapToTaskDtoList(taskRepository.findAll());
    }

    public TaskDto getTaskById(final Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return taskMapper.mapToTaskDto(task);
    }

    public void deleteTaskById(final Long id) {
        taskRepository.deleteById(id);
    }
}
