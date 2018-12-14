package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloCardDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.username}")
    private String trelloAppUsername;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    private RestTemplate restTemplate;

    @Autowired
    public TrelloClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = buildGetBoardsUrl();
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public TrelloCardDto createNewCard(TrelloCardDtoRequest trelloCardDtoRequest) {
        URI url = buildCreateNewCardUrl(trelloCardDtoRequest);
        return restTemplate.postForObject(url, null, TrelloCardDto.class);
    }

    private URI buildGetBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloAppUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    private URI buildCreateNewCardUrl(TrelloCardDtoRequest trelloCardDtoRequest) {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("name", trelloCardDtoRequest.getName())
                .queryParam("desc", trelloCardDtoRequest.getDescription())
                .queryParam("pos", trelloCardDtoRequest.getPosition())
                .queryParam("idList", trelloCardDtoRequest.getListId())
                .build().encode().toUri();
    }
}
