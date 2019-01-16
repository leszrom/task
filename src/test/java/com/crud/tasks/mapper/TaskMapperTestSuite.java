package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskMapperTestSuite {
    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void should_return_task_mapped_from_task_dto() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertEquals(1L, (long) mappedTask.getId());
        Assert.assertEquals("title", mappedTask.getTitle());
        Assert.assertEquals("content", mappedTask.getContent());
    }

    @Test
    public void should_return_task_mapped_from_task_dto_when_id_is_null() {
        //Given
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("title");
        taskDto.setContent("content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertNull(task.getId());
        Assert.assertEquals("title", task.getTitle());
        Assert.assertEquals("content", task.getContent());
    }

    @Test
    public void should_return_task_dto_mapped_from_task() {
        //Given
        Task task = new Task(1L, "title", "content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        Assert.assertEquals(1L, (long) taskDto.getId());
        Assert.assertEquals("title", taskDto.getTitle());
        Assert.assertEquals("content", taskDto.getContent());
    }

    @Test
    public void should_return_task_dto_list_mapped_from_task_list() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "title_1", "content_1"));
        taskList.add(new Task(2L, "title_2", "content_2"));
        taskList.add(new Task(3L, "title_3", "content_3"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        Assert.assertEquals(3, taskDtoList.size());
        Assert.assertEquals(1L, (long) taskDtoList.get(0).getId());
        Assert.assertEquals("title_1", taskDtoList.get(0).getTitle());
        Assert.assertEquals("content_1", taskDtoList.get(0).getContent());
        Assert.assertEquals(2L, (long) taskDtoList.get(1).getId());
        Assert.assertEquals("title_2", taskDtoList.get(1).getTitle());
        Assert.assertEquals("content_2", taskDtoList.get(1).getContent());
        Assert.assertEquals(3L, (long) taskDtoList.get(2).getId());
        Assert.assertEquals("title_3", taskDtoList.get(2).getTitle());
        Assert.assertEquals("content_3", taskDtoList.get(2).getContent());
    }
}