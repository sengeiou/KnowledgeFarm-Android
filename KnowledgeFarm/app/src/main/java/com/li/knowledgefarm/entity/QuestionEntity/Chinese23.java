package com.li.knowledgefarm.entity.QuestionEntity;

import java.io.Serializable;

/**
 * @program: knowledge_farm
 * @description: 语文23年级
 * @author: 景光赞
 * @create: 2020-04-20 17:11
 **/
public class Chinese23 implements Serializable {
    private int id;
    private String word;
    private String pinYin;
    private int tone;
    private String word2;
    private String word3;
    private String grade;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public int getTone() {
        return tone;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
