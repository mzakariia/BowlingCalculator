package com.zakaria.bowling;


import com.zakaria.bowling.controller.BowlingController;
import com.zakaria.bowling.service.BowlingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class BowlingValidationTests {

    @InjectMocks
    private BowlingController bowlingController;

    @Mock
    private BowlingService bowlingService;

    @Test
    void passOnAllZerosArray(){
        //given
        HashMap<String,byte[]> input = new HashMap<String,byte[]>();
        input.put("player1", new byte[21]);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(ArrayList.class,result.getBody());
    }

    @Test
    void failedResponseOnArrayLargerThan21(){
        //given
        HashMap<String,byte[]> input = new HashMap<String,byte[]>();
        input.put("player1", new byte[25]);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(String.class,result.getBody());
    }

    @Test
    void failedResponseOnScoringMoreThan10InTheSameFrame(){
        //given
        HashMap<String,byte[]> input = new HashMap<String,byte[]>();
        byte[] score = new byte[21];
        score[0]=5;
        score[1]=6;
        input.put("player1", score);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(String.class,result.getBody());
    }

    @Test
    void failedResponseOnScoringMoreThan10FinalFrame(){
        //testing case on getting strike in the 10th frame
        //given
        HashMap<String,byte[]> input = new HashMap<String,byte[]>();
        byte[] score = new byte[21];
        score[20]=5;
        score[19]=6;
        score[18]=10;
        input.put("player1", score);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(String.class,result.getBody());
    }

    @Test
    void failedResponseOnScoresAfterFinalFrame(){
        //given
        HashMap<String,byte[]> input = new HashMap<String,byte[]>();
        byte[] score = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        input.put("player1", score);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(String.class,result.getBody());
    }

    @Test
    void passOnValidInputs(){
       //given
        byte[] score1 = {3,7, 3,4, 8,2, 10, 4,2, 2,3, 7,3, 1,5, 1,6, 10, 5,5};
        String player1 = "John";
        byte[] score2 = {10, 3,5, 1,2, 10, 10, 2,7, 1,4, 1,5, 1,8, 0,7};
        String player2 = "Moe";
        byte[] score3 = {4,6, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 5,5, 2,8, 10, 10,10};
        String player3 = "Joe";
        HashMap<String, byte[]> input = new HashMap<String, byte[]>();
        input.put(player1, score1);
        input.put(player2, score2);
        input.put(player3, score3);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(input);
        //then
        assertInstanceOf(ArrayList.class,result.getBody());

    }
}
