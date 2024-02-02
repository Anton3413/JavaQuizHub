package com.example.javaquizhub.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@EqualsAndHashCode
public class TestSession {

   private int counter;

   private int  numberOfTests;

   private List<Test> testList;

   private List<TestResult> testResults;


   {
      this.counter = 0;
      this.testResults = new ArrayList<>();
   }

   public TestSession(List<Test> testList){
      this.numberOfTests = testList.size();
      this.testList = testList;
   }

   public boolean hasMoreTests(){
      return counter < numberOfTests;
   }

   public  Test getCurrentTest(){
      return testList.get(counter);
   }

   public void addAnswer(Test test,List<TestAnswer> testAnswerList,List<TestAnswer> correctAnswers){

      TestResult testResult = new TestResult(test,testAnswerList,correctAnswers);
      testResults.add(testResult);
      counter++;
   }

   @Override
   public String toString() {
      return "TestSession{" +
              "counter=" + counter +
              ", numberOfTests=" + numberOfTests +
              '}';
   }
}
