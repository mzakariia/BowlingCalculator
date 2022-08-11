package com.zakaria.bowling.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BowlingResponse {

    private String name;

    private int score;

    private boolean perfect;

    private byte[] frames;
}
