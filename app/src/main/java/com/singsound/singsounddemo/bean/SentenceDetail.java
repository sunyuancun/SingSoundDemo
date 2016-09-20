package com.singsound.singsounddemo.bean;

/**
 * Created by wang on 2016/9/20.
 */
public class SentenceDetail {


    /**
     * fluency : 100
     * dur : 470
     * score : 84
     * start : 1020
     * liaisonscore : 0
     * toneref : 0
     * stressscore : 1
     * stressref : 0
     * char : cooking
     * sensescore : 1
     * senseref : 0
     * tonescore : 0
     * end : 1490
     * liaisonref : 0
     */


    private double score;

    private String charX;


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public String getCharX() {
        return charX;
    }

    public void setCharX(String charX) {
        this.charX = charX;
    }

}
