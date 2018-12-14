package com.crud.tasks.trello.client;

import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class TrelloClient {

    private RestTemplate restTemplate;
    private TrelloConfig trelloConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    public TrelloClient(RestTemplate restTemplate, TrelloConfig trelloConfig) {
        this.restTemplate = restTemplate;
        this.trelloConfig = trelloConfig;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = buildGetBoardsUrl();
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Arrays.asList(Optional.ofNullable(boardsResponse)
                    .orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public TrelloCardDto createNewCard(TrelloCardDtoRequest trelloCardDtoRequest) {
        URI url = buildCreateNewCardUrl(trelloCardDtoRequest);
        return restTemplate.postForObject(url, null, TrelloCardDto.class);
    }

    private URI buildGetBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloAppUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    private URI buildCreateNewCardUrl(TrelloCardDtoRequest trelloCardDtoRequest) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("name", trelloCardDtoRequest.getName())
                .queryParam("desc", trelloCardDtoRequest.getDescription())
                .queryParam("pos", trelloCardDtoRequest.getPosition())
                .queryParam("idList", trelloCardDtoRequest.getListId())
                .build().encode().toUri();
    }
}
