package com.example.javaquizhub.controller;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import com.example.javaquizhub.model.TestSession;
import com.example.javaquizhub.service.BookService;
import com.example.javaquizhub.service.TestAnswerService;
import com.example.javaquizhub.service.TestService;
import com.example.javaquizhub.validation.TestRequestValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@Controller
@SessionAttributes("testSession")
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    TestAnswerService answerService;
    @Autowired
    BookService bookService;

    @PostMapping  ("/book/{bookId}/start")
    public String startTesting(@PathVariable("bookId") int bookId, @RequestParam("numberOfTests") int numberOfTests,
                               @RequestParam("categories") List<String> testCategories,
                               HttpSession httpSession,Model model) {
        BindingResult result = new TestRequestValidator(testService).validate(bookId,numberOfTests,testCategories);

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "numberOfTests", result);
            model.addAttribute("book", bookService.getBookById(bookId));
            model.addAttribute("categories", Category.values());
            return "book-details";
        }
        List<Test> testList = testService.findTestsByBookIdAndCategoryInWithLimit(bookId,testCategories,numberOfTests);
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
