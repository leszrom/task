package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @Test
    public void should_created_new_task_and_return_id() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("title");
        taskDto.setContent("content");

        Mockito.when(dbService.saveTask(taskDto)).thenReturn(3L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    public void should_fetch_all_tasks() throws Exception {
        //Given
        List<TaskDto> allTasks = new ArrayList<>();
        allTasks.add(new TaskDto(1L, "title_1", "content_1"));

        Mockito.when(dbService.getAllTasks()).thenReturn(allTasks);

        //When
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title_1")))
                .andExpect(jsonPath("$[0].content", is("content_1")));
    }

    @Test
    public void should_fetch_task_by_given_id() throws Exception {
        //Given
        TaskDto task = new TaskDto(1L, "title", "content");

        Mockito.when(dbService.getTaskById(1L)).thenReturn(task);

        //When
        mockMvc.perform(get("/v1/task/getTask?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void should_update_task_and_return_id() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "new_title", "new_content");

        Mockito.when(dbService.saveTask(taskDto)).thenReturn(1L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(1))) ;
    }

    @Test
    public void should_delete_task_by_given_id() throws Exception {
        //Given
        //When
        mockMvc.perform(delete("/v1/task/deleteTask?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().is(200));

        Mockito.verify(dbService, Mockito.times(1)).deleteTaskById(1L);
    }
}