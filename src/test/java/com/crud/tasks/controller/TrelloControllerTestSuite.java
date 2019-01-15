package com.crud.tasks.controller;

import com.crud.tasks.domain.trello.*;
import com.crud.tasks.trello.facade.TrelloFacade;
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
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void should_fetch_empty_trello_boards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When
        mockMvc.perform(get("/v1/trello/boards")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void should_fetch_trello_boards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("list_id", "list_name", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("board_id", "board_name", trelloLists));

        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When
        mockMvc.perform(get("/v1/trello/boards")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("board_id")))
                .andExpect(jsonPath("$[0].name", is("board_name")))
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("list_id")))
                .andExpect(jsonPath("$[0].lists[0].name", is("list_name")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void should_create_trello_card() throws Exception {
        //Given
        TrelloCardDtoRequest requestedCard =
                new TrelloCardDtoRequest("name", "description", "position", "list_id");

        TrelloCardDto createdCard =
                new TrelloCardDto("323", "name", "http://test.com", new TrelloBadgesDto());

        Mockito.when(trelloFacade.createCard(any(TrelloCardDtoRequest.class))).thenReturn(createdCard);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(requestedCard);

        //When
        mockMvc.perform(post("/v1/trello/cards/new")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                //Then
                .andExpect(jsonPath("$.id", is("323")))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")));
    }
}
