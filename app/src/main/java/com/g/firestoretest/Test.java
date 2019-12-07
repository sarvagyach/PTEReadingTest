package com.g.firestoretest;

public class Test {
    private String testNumber;
    private String testName;

    public Test(){

    }

    public Test(String testNumber, String testName) {
        this.testNumber = testNumber;
        this.testName = testName;
    }

    public String getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
