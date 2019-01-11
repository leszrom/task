package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {
    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void should_return_trello_boards_list_after_mapping_from_trello_boards_dto_list() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        TrelloBoardDto boardDto_1 = new TrelloBoardDto("id_1", "board_1", new ArrayList<>());
        TrelloBoardDto boardDto_2 = new TrelloBoardDto("id_2", "board_2", new ArrayList<>());
        TrelloBoardDto boardDto_3 = new TrelloBoardDto("id_3", "board_3", new ArrayList<>());
        trelloBoardsDto.add(boardDto_1);
        trelloBoardsDto.add(boardDto_2);
        trelloBoardsDto.add(boardDto_3);

        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        Assert.assertEquals(3, boards.size());
        Assert.assertEquals("id_1", boards.get(0).getId());
        Assert.assertEquals("id_2", boards.get(1).getId());
        Assert.assertEquals("id_3", boards.get(2).getId());
    }

    @Test
    public void  should_return_trello_boards_dto_list_after_mapping_from_trello_boards_list() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard boardDto_1 = new TrelloBoard("id_1", "board_1", new ArrayList<>());
        TrelloBoard boardDto_2 = new TrelloBoard("id_2", "board_2", new ArrayList<>());
        TrelloBoard boardDto_3 = new TrelloBoard("id_3", "board_3", new ArrayList<>());
        trelloBoards.add(boardDto_1);
        trelloBoards.add(boardDto_2);
        trelloBoards.add(boardDto_3);

        //When
        List<TrelloBoardDto> boardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assert.assertEquals(3, boardsDto.size());
        Assert.assertEquals("id_1", boardsDto.get(0).getId());
        Assert.assertEquals("id_2", boardsDto.get(1).getId());
        Assert.assertEquals("id_3", boardsDto.get(2).getId());
    }

    @Test
    public void should_return_MapToList() {
        //Given

        //When

        //Then

    }

    @Test
    public void should_return_MapToListDto() {
        //Given

        //When

        //Then

    }

    @Test
    public void should_return_MapToCardDtoRequest() {
        //Given

        //When

        //Then

    }

    @Test
    public void should_return_MapToCard() {
        //Given

        //When

        //Then

    }


}