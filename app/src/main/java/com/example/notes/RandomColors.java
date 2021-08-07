package com.example.notes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class RandomColors {
    private Stack<Integer> recycle, colors;

    public RandomColors() {
        colors = new Stack<>();
        recycle =new Stack<>();
        recycle.addAll(Arrays.asList(
                0xFF9FEB,0xABF7FF,0xFF778A,0x6BC58A,
                0xFFC58A)
        );
    }

    public int getColor() {
        if (colors.size()==0) {
            while(!recycle.isEmpty())
                colors.push(recycle.pop());
            Collections.shuffle(colors);
        }
        Integer c= colors.pop();
        recycle.push(c);
        return c;
    }
}

