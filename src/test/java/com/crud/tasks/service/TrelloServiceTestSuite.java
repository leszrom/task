package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.domain.trello.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void should_return_fetched_trello_boards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "list_name", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "board_name", trelloLists));

        Mockito.when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> boards = trelloService.fetchTrelloBoards();

        //Then
        boards.forEach(board -> {
            Assert.assertEquals("1", board.getId());
            Assert.assertEquals("board_name", board.getName());

            board.getLists().forEach(list -> {
                Assert.assertEquals("1", list.getId());
                Assert.assertEquals("list_name", list.getName());
                Assert.assertFalse(list.isClosed());
            });
        });
    }

    @Test
    public void should_return_created_trello_card_and_send_mail() {
        //Given
        TrelloCardDtoRequest cardDtoRequest =
                new TrelloCardDtoRequest("name", "description", "position", "listId");
        TrelloCardDto newCard = new TrelloCardDto("id", "name", "shortUrl", null);

        Mockito.when(trelloClient.createNewCard(cardDtoRequest)).thenReturn(newCard);

        //When
        TrelloCardDto createdCard = trelloService.createTrelloCard(cardDtoRequest);

        //Then
        Mockito.verify(emailService, Mockito.times(1)).send(anyObject());
        Assert.assertEquals("id", createdCard.getId());
        Assert.assertEquals("name", createdCard.getName());
        Assert.assertEquals("shortUrl", createdCard.getShortUrl());
        Assert.assertNull(createdCard.getBadges());
    }
}