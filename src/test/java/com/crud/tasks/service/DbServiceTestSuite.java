package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @Test
    public void should_return_id_of_the_saved_task() {
        //Given
        TaskDto taskDto = new TaskDto(null, "title", "content");
        Task task = new Task(null, "title", "content");
        Task savedTask = new Task(1L, "title", "content");


        Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        Mockito.when(taskRepository.save(task)).thenReturn(savedTask);

        //When
        Long id = dbService.saveTask(taskDto);

        //Then
        Assert.assertEquals(1L, (long) id);
    }

    @Test
    public void should_return_list_of_all_tasks_in_database() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "title", "content"));

        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "title", "content"));

        Mockito.when(taskRepository.findAll()).thenReturn(taskList);
        Mockito.when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When
        List<TaskDto> allTasks = dbService.getAllTasks();

        //Then
        allTasks.forEach(
                taskDto -> {
                    Assert.assertEquals(1L, (long) taskDto.getId());
                    Assert.assertEquals("title", taskDto.getTitle());
                    Assert.assertEquals("content", taskDto.getContent());
                }
        );
    }

    @Test
    public void should_return_empty_list_of_tasks_when_there_is_no_tasks_in_database() {
        //Given
        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDtoList = new ArrayList<>();

        Mockito.when(taskRepository.findAll()).thenReturn(taskList);
        Mockito.when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When
        List<TaskDto> allTasks = dbService.getAllTasks();

        //Then
        Assert.assertEquals(0, allTasks.size());
    }

    @Test
    public void should_return_task_by_given_id() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        Task task = new Task(1L, "title", "content");

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When
        TaskDto readTask = dbService.getTaskById(1L);

        //Then
        Assert.assertEquals(1L, (long) readTask.getId());
        Assert.assertEquals("title", readTask.getTitle());
        Assert.assertEquals("content", readTask.getContent());
    }

    @Test
    public void should_throw_exception_when_there_is_no_task_with_given_id() {
        //Given
        String exceptionMessage = "The task with the given ID doesn't exist!";
        Mockito.when(taskRepository.findById(1L)).thenThrow(new TaskNotFoundException());

        //When
        try {
            dbService.getTaskById(1L);
        } catch (Exception e) {
            //Then
            Assert.assertEquals(exceptionMessage, e.getMessage());
            Assert.assertEquals(TaskNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void should_delete_task_by_given_id() {
        //Given
        //When
        dbService.deleteTaskById(1L);

        //Then
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }
}
