package com.zakaria.bowling.controller;

import com.zakaria.bowling.models.BowlingResponse;
import com.zakaria.bowling.service.BowlingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@NoArgsConstructor
@AllArgsConstructor
@RestController
public class BowlingController {

    @Autowired
    private BowlingService bowlingService;

    @GetMapping(path = "/scores")
    public ResponseEntity<Object> getScores(Map<String,byte[]> players){
        for(Map.Entry<String,byte[]> e : players.entrySet()){
            if(!validateScores(e.getValue())){
                return new ResponseEntity<Object>("ERROR: please check score card for player "+e.getKey(),HttpStatus.BAD_REQUEST);
            }
        }
        ArrayList<BowlingResponse> result =  generateResponseObject(bowlingService.getScores(players),(HashMap<String, byte[]>) players);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }


    private ArrayList<BowlingResponse> generateResponseObject(HashMap<String,Integer> finalScores, HashMap<String,byte[]> scores){
        ArrayList<BowlingResponse> result = new ArrayList<BowlingResponse>();
        for(Entry<String,Integer> e : finalScores.entrySet()){
            boolean perfect = e.getValue()==300?true:false;
            if(result.isEmpty()){
                result.add(new BowlingResponse(e.getKey(),e.getValue(),perfect,scores.get(e.getKey())));
                continue;
            }
            for(BowlingResponse b : result){
                if(e.getValue()>b.getScore()){
                    result.add(result.indexOf(b),new BowlingResponse(e.getKey(),e.getValue(),perfect,scores.get(e.getKey())));
                    break;
                }
            }
        }
        return result;
    }
    private boolean validateScores(byte[] scores){
        if(scores.length>21) return false;
        int pointer=0;
        for(int frame=0;frame<10;frame++){
            if(scores[pointer]==10){
                if(frame==9 && scores[pointer+1]+scores[pointer+2]>10 && scores[pointer+1]!=10){
                 //   System.out.println("invalidated at last strike frame");
                    return false;
                }
                if(frame==9) pointer+=3; //moving pointer to the supposedly last score index in array in preparation for the 2nd for loop
                else pointer++;
            }
            else {
                if(scores[pointer]+scores[pointer+1]>10){
                   // System.out.println("Invalidated as non strike scores greater than 10");
                    return false;
                }
                pointer+=2;
            }
        }
        for(int i=pointer;i<scores.length;i++){
           // System.out.println("invalidated due to additional scores");
            if(scores[i]>0) return false;
        }
        return true;
    }
}
