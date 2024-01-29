package com.example.javaquizhub.controller;

import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import com.example.javaquizhub.model.TestSession;
import com.example.javaquizhub.service.TestAnswerService;
import com.example.javaquizhub.service.TestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("testSession")
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    TestAnswerService answerService;

    @PostMapping  ("/book/{bookId}/start")
    public String startTesting( @PathVariable("bookId") int bookId, @RequestParam("numberOfTests") int limit,
                               @RequestParam("categories") List<String> testCategories, HttpSession httpSession) {

        List<Test> testList = testService.findTestsByBookIdAndCategoryInWithLimit(bookId,testCategories,limit);

        TestSession testSession = new TestSession(testList);

        httpSession.setAttribute("testSession",testSession);

        return "redirect:/book/"+bookId+ "/test";
    }

   @GetMapping("/book/{bookId}/test")
    public String getNextTest(HttpSession httpSession, Model model,@PathVariable("bookId") int bookId){
        TestSession testSession = (TestSession)httpSession.getAttribute("testSession");

        if(testSession.hasMoreTests()){
            Test test = testSession.getCurrentTest();

            List<TestAnswer> answers = answerService.getTestAnswersByTest_Id(test.getId());

            model.addAttribute("test",test);
            model.addAttribute("answers",answers);
            return "current-test-page";
        }
        return "redirect:/book/"+bookId+"/testResults";
    }

    @PostMapping("/book/{bookId}/handleAnswer")
    public String handleTestAnswer(@PathVariable("bookId") int bookId,
                                   @RequestParam("testId") int testId,
                                   @RequestParam("selectedAnswers") List<TestAnswer> testAnswers, HttpSession httpSession){

        TestSession testSession = (TestSession) httpSession.getAttribute("testSession");

        testSession.addAnswer(testService.getTestById(testId),testAnswers);

        return "redirect:/book/"+ bookId+"/test";
    }

    @GetMapping("/book/{bookId}/testResults")
    public String showPageWithTestResults(HttpSession httpSession,@PathVariable("bookId") int bookId, Model model){
        TestSession testSession =(TestSession) httpSession.getAttribute("testSession");
        model.addAttribute("resultsMap",testSession.getAnswersMap());

        return "test-result-page";
    }
}
