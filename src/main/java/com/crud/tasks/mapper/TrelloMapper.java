package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.domain.trello.TrelloListDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {
    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardsDto) {
        return trelloBoardsDto.stream()
                .map(boardDto ->
                        new TrelloBoard(boardDto.getId(), boardDto.getName(), mapToList(boardDto.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(board ->
                        new TrelloBoardDto(board.getId(), board.getName(), mapToListDto(board.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListsDto) {
        return trelloListsDto.stream()
                .map(listDto -> new TrelloList(listDto.getId(), listDto.getName(), listDto.isClosed()))
                .collect(Collectors.toList());

    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(list -> new TrelloListDto(list.getId(), list.getName(), list.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCardDtoRequest mapToCardDtoRequest(final TrelloCard trelloCard) {
        return new TrelloCardDtoRequest(trelloCard.getName(), trelloCard.getDescription(),
                trelloCard.getPosition(), trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDtoRequest trelloCardDtoRequest) {
        return new TrelloCard(trelloCardDtoRequest.getName(), trelloCardDtoRequest.getDescription(),
                trelloCardDtoRequest.getPosition(), trelloCardDtoRequest.getListId());
    }
}
