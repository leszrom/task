package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TrelloValidatorTestSuite {
    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void should_return_log_that_application_is_used_in_proper_way() {
        //Given
        Logger validatorLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        validatorLogger.addAppender(listAppender);

        TrelloCard card = new TrelloCard("name", "description", "position", "id");

        //When
        trelloValidator.validCard(card);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;

        Assert.assertEquals(1, logsList.size());
        Assert.assertEquals("Seems that my application is used in proper way.", logsList.get(0).getMessage());
    }

    @Test
    public void should_return_log_that__someone_is_testing_my_application_when_name_contain_test() {
        //Given

        Logger validatorLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        validatorLogger.addAppender(listAppender);

        TrelloCard card = new TrelloCard("test", "description", "position", "id");

        //When
        trelloValidator.validCard(card);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;

        Assert.assertEquals(1, logsList.size());
        Assert.assertEquals("Someone is testing my application!", logsList.get(0).getMessage());
    }

    @Test
    public void should_return_not_changed_list_of_boards_after_filtering() {
        //Given
        List<TrelloBoard> boardList = new ArrayList<>();
        boardList.add(new TrelloBoard("id_1", "Tasks_example_board", new ArrayList<>()));
        boardList.add(new TrelloBoard("id_2", "Tasks_example_board", new ArrayList<>()));

        //When
        List<TrelloBoard> filteredBoardList = trelloValidator.validateTrelloBoards(boardList);

        //Then
        Assert.assertEquals(2, filteredBoardList.size());
    }

    @Test
    public void should_return_filtered_list_of_boards_after_filtering_boards() {
        //Given
        List<TrelloBoard> boardList = new ArrayList<>();
        boardList.add(new TrelloBoard("id_1", "name_1", new ArrayList<>()));
        boardList.add(new TrelloBoard("id_2", "Tasks_example_board", new ArrayList<>()));

        //When
        List<TrelloBoard> filteredBoardList = trelloValidator.validateTrelloBoards(boardList);

        //Then
        Assert.assertEquals(1, filteredBoardList.size());
    }
}