package com.zakaria.bowling.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class BowlingService {
    public HashMap<String,Integer> getScores(Map<String,byte[]> players) {
        HashMap<String,Integer> finalScores = new HashMap<String,Integer>();
        for(Entry<String,byte[]> e: players.entrySet()){
            byte [] playerScore = e.getValue();
            int score=0;
            int pointer=0;
            for(int frame=0;frame<10;frame++){
                //System.out.println("-----------------------");
                //System.out.println("calculating for frame "+frame);
                //System.out.println("starting score is "+score);
                if(playerScore[pointer]==10){
                   // System.out.println("got a strike so adding 10+"+playerScore[pointer+1]+"+"+playerScore[pointer+2]);
                    score+= 10+playerScore[pointer+1]+playerScore[pointer+2];
                    pointer++;
                }
                else{
                    score+=playerScore[pointer]+playerScore[pointer+1];
                   // System.out.println("no strike so adding to score "+playerScore[pointer]+"+"+playerScore[pointer+1]);
                    if(playerScore[pointer]+playerScore[pointer+1]==10){
                       // System.out.println("got a spare so adding to score "+playerScore[pointer+2]);
                        score+=playerScore[pointer+2];
                    }
                    pointer+=2;
                }
               // System.out.println("ending score "+score);
            }
            finalScores.put(e.getKey(),score);
        }
        return finalScores;
    }
}
