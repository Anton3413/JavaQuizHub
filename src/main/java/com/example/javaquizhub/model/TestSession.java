package com.example.javaquizhub.model;

import com.example.javaquizhub.repository.TestRepository;
import com.example.javaquizhub.service.TestService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@EqualsAndHashCode
public class TestSession {

   private int counter;

   private int  numberOfTests;

   private List<Test> testList;

   private HashMap<Test,List<TestAnswer>> answersMap;


   {
      this.counter = 0;
      this.answersMap = new HashMap<>();
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

   public void addAnswer(Test test,List<TestAnswer> testAnswerList){
      answersMap.put(test,testAnswerList);
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
