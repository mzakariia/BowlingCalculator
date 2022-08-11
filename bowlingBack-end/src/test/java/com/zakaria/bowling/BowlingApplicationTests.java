package com.zakaria.bowling;

import static org.junit.jupiter.api.Assertions.*;

import com.zakaria.bowling.controller.BowlingController;
import com.zakaria.bowling.models.BowlingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
class BowlingApplicationTests {

    @Autowired
    private BowlingController bowlingController;

    private static int arraySum(byte[] arr){
        int sum=0;
        for(byte b:arr){
            sum+=b;
        }
        return sum;
    }

    @Test
    void shouldReturnScoreResponseGivenValidInput(){
        //given
        byte[] score = {3,4, 3,4, 1,2, 5,4 ,4,2, 2,3, 1,3, 1,5, 1,6, 1,7};
        String playerName = "John";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(playerName,score);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(players);
        //then
        int expectedResult = arraySum(score);
        ArrayList<BowlingResponse> scores = (ArrayList<BowlingResponse>) result.getBody();
        assertInstanceOf(ArrayList.class,result.getBody());
        assertSame(HttpStatus.OK,result.getStatusCode());
        assertEquals(expectedResult,scores.get(0).getScore());
    }

    @Test
    void shouldReturnFalseResponseGivenInvalidInput(){
        //given
        byte[] score = {3,9, 3,4, 1,2, 5,4 ,4,2, 2,3, 1,3, 1,5, 1,6, 1,7};
        String playerName = "John";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(playerName,score);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(players);
        //then
        assertInstanceOf(String.class,result.getBody());
        assertSame(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void shouldReturnPlayersOrderedByScore(){
        //given
        byte[] score1 = {3,7, 3,4, 8,2, 10, 4,2, 2,3, 7,3, 1,5, 1,6, 10, 5,5};
        String player1 = "John";
        byte[] score2 = {10, 3,5, 1,2, 10, 10, 2,7, 1,4, 1,5, 1,8, 0,7};
        String player2 = "Moe";
        byte[] score3 = {4,6, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 5,5, 2,8, 10, 10,10};
        String player3 = "Joe";
        HashMap<String, byte[]> players = new HashMap<String, byte[]>();
        players.put(player1, score1);
        players.put(player2, score2);
        players.put(player3, score3);
        //when
        ResponseEntity<Object> result = bowlingController.getScores(players);
        //then
        assertInstanceOf(ArrayList.class,result.getBody());
        boolean ordered = true;
        ArrayList<BowlingResponse> finalScores = (ArrayList<BowlingResponse>) result.getBody();
        int prev = Integer.MAX_VALUE;
        for(BowlingResponse b : finalScores){
            if(b.getScore()>prev){
                ordered=false;
                break;
            }
            prev = b.getScore();
        }
        assertTrue(ordered);
    }

}
