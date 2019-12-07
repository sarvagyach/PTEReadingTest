package com.g.firestoretest;

public class MCSA {
    private String testNm;
    private String passage;
    private String mainQuestion;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

MCSA(){

}

    public MCSA(String testNm, String passage, String mainQuestion, String option1, String option2, String option3, String option4, String answer) {
        this.testNm = testNm;
        this.passage = passage;
        this.mainQuestion = mainQuestion;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public String getTestNm() {
        return testNm;
    }

    public void setTestNm(String testNm) {
        this.testNm = testNm;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public String getMainQuestion() {
        return mainQuestion;
    }

    public void setMainQuestion(String mainQuestion) {
        this.mainQuestion = mainQuestion;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
