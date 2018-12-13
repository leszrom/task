package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
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

    private URI buildUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloAppUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .build().encode().toUri();
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUrl(), TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }
}
