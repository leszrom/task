package com.crud.tasks.trello.client;

import com.crud.tasks.domain.trello.TrelloBadgesDto;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTestSuite {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void setUp() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("key");
        when(trelloConfig.getTrelloAppToken()).thenReturn("token");
        when(trelloConfig.getTrelloAppUsername()).thenReturn("username");
    }

    @Test
    public void should_fetch_trello_boards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/username/boards?key=key&token=token&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(1, fetchedTrelloBoards.size());
        Assert.assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        Assert.assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void should_return_empty_list() throws URISyntaxException {
        //Given
        URI uri = new URI("http://test.com/members/username/boards?key=key&token=token&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(0, fetchedTrelloBoards.size());
    }

    @Test
    public void should_create_new_card() throws URISyntaxException {
        //Given
        URI uri = new URI("http://test.com/cards?key=key&token=token&name=Test%20task&desc=Test%20description&pos=top&idList=test_id");

        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new TrelloBadgesDto()
        );

        when(restTemplate.postForObject(uri, null, TrelloCardDto.class)).thenReturn(trelloCardDto);

        //When
        TrelloCardDtoRequest trelloCardDtoRequest = new TrelloCardDtoRequest(
                "Test task",
                "Test description",
                "top",
                "test_id"
        );
        TrelloCardDto newCard = trelloClient.createNewCard(trelloCardDtoRequest);

        //Then
        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
        Assert.assertEquals("http://test.com", newCard.getShortUrl());
    }

}
