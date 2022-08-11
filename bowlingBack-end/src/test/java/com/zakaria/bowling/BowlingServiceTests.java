package com.zakaria.bowling;

import com.zakaria.bowling.service.BowlingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class BowlingServiceTests {

    private BowlingService bowlingService;

    @BeforeEach
    public void setup(){
        bowlingService = new BowlingService();
    }

    //used to calculate scores for games with no spares or strikes
    private static int arraySum(byte[] arr){
        int sum=0;
        for(byte b:arr){
            sum+=b;
        }
        return sum;
    }

    @Test
    void checkGameWithNoSparesOrStrikesWithOnePlayer(){
        //given
        byte[] score = {3,4, 3,4, 1,2, 5,4 ,4,2, 2,3, 1,3, 1,5, 1,6, 1,7};
        String playerName = "John";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(playerName,score);
        //when
        HashMap<String,Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore = arraySum(score);
        assertEquals(expectedScore,result.get(playerName));
    }

    @Test
    void checkGameWithNoSparesOrStrikesWithManyPlayers(){
        //given
        byte[] score1 = {3,4, 3,4, 1,2, 5,4 ,4,2, 2,3, 1,3, 1,5, 1,6, 1,7};
        String player1 = "John";
        byte[] score2 = {3,4, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 1,5, 1,8, 0,7};
        String player2 = "Moe";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(player1,score1);
        players.put(player2,score2);
        //when
        HashMap<String,Integer> result = bowlingService.getScores(players);
        //then
        int expectedScore1 = arraySum(score1);
        int expectedScore2 = arraySum(score2);
        assertAll(
                ()-> assertEquals(expectedScore1,result.get(player1)),
                ()-> assertEquals(expectedScore2,result.get(player2))
        );
    }

    @Test
    void checkGameWithSparesAndNoStrikesWithOnePlayer(){
        //given
        byte[] score = {3,7, 3,4, 8,2, 5,4 ,4,2, 2,3, 7,3, 1,5, 1,6, 3,7, 5};
        String playerName = "John";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(playerName,score);
        //when
        HashMap<String,Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore = 13+7+15+9+6+5+11+6+7+15;
        assertEquals(expectedScore,result.get(playerName));
    }

    @Test
    void checkGameWithSparesAndNoStrikesWithManyPlayers(){
       //given
        byte[] score1 = {3,7, 3,4, 8,2, 5,4 ,4,2, 2,3, 7,3, 1,5, 1,6, 3,7, 5};
        String player1 = "John";
        byte[] score2 = {3,4, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 1,5, 1,8, 0,7};
        String player2 = "Moe";
        byte[] score3 = {4,6, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 5,5, 2,8, 0,7};
        String player3 = "Joe";
        HashMap<String,byte[]> players = new HashMap<String,byte[]>();
        players.put(player1,score1);
        players.put(player2,score2);
        players.put(player3,score3);
        //when
        HashMap<String,Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore1 = 13+7+15+9+6+5+11+6+7+15;
        int expectedScore2 = arraySum(score2);
        int expectedScore3 = 13+8+3+9+6+9+5+12+10+7;
        assertAll(
                ()->  assertEquals(expectedScore1,result.get(player1)),
                ()-> assertEquals(expectedScore2,result.get(player2)),
                ()-> assertEquals(expectedScore3,result.get(player3))
        );
    }

    @Test
    void checkGameWithStrikesAndNoSparesWithManyPlayers() {
        //given
        byte[] score1 = {3,6, 3,4, 6,2, 10, 4,2, 2,3, 4,3, 1,5, 1,6, 10, 5,5};
        String player1 = "John";
        byte[] score2 = {10, 3,5, 1,2, 10, 10, 2,7, 1,4, 1,5, 1,8, 0,7};
        String player2 = "Moe";
        byte[] score3 = {4,5, 3,5, 1,2, 3,6 ,4,2, 2,7, 1,4, 5,4, 1,8, 10, 10,10};
        String player3 = "Joe";
        HashMap<String, byte[]> players = new HashMap<String, byte[]>();
        players.put(player1, score1);
        players.put(player2, score2);
        players.put(player3, score3);
        //when
        HashMap<String, Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore1 = 9+7+8+16+6+5+7+6+7+20;
        int expectedScore2 = 18+8+3+22+19+9+5+6+9+7;
        int expectedScore3 = 9+8+3+9+6+9+5+9+9+30;
        assertAll(
                () -> assertEquals(expectedScore1, result.get(player1)),
                () -> assertEquals(expectedScore2, result.get(player2)),
                () -> assertEquals(expectedScore3, result.get(player3))
        );
    }

    @Test
    void checkGameWithSparesAndStrikesWithManyPlayers() {
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
        HashMap<String, Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore1 = 13+7+20+16+6+5+11+6+7+20;
        int expectedScore2 = 18+8+3+22+19+9+5+6+9+7;
        int expectedScore3 = 13+8+3+9+6+9+5+12+20+30;
        assertAll(
                () -> assertEquals(expectedScore1, result.get(player1)),
                () -> assertEquals(expectedScore2, result.get(player2)),
                () -> assertEquals(expectedScore3, result.get(player3))
        );
    }

    @Test
    void checkPerfectGameWithManyPlayers() {
        //given
        byte[] score1 = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        String player1 = "John";
        byte[] score2 = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        String player2 = "Moe";
        byte[] score3 = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        String player3 = "Joe";
        HashMap<String, byte[]> players = new HashMap<String, byte[]>();
        players.put(player1, score1);
        players.put(player2, score2);
        players.put(player3, score3);
        //when
        HashMap<String, Integer> result =  bowlingService.getScores(players);
        //then
        int expectedScore = 300;
        assertAll(
                () -> assertEquals(expectedScore, result.get(player1)),
                () -> assertEquals(expectedScore, result.get(player2)),
                () -> assertEquals(expectedScore, result.get(player3))
        );
    }
}
