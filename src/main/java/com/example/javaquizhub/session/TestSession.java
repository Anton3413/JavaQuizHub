package com.example.javaquizhub.session;

import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import com.example.javaquizhub.service.TestService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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


   private TestSession(){
   }

   public  static TestSession init(List<Test> testList){
      TestSession testSession = new TestSession();
      testSession.setNumberOfTests(testList.size());
      testSession.setTestList(testList);
      testSession.counter = 0;
      testSession.testResults = new ArrayList<>();
      return testSession;
   }

   public boolean hasMoreTests(){
      return counter < numberOfTests;
   }

   public  Test getCurrentTest(){
      return testList.get(counter);
   }

   public void addAnswer(Test test, List<TestAnswer> testAnswerList, List<TestAnswer> correctAnswers){

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
