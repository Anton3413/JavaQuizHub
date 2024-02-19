package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.TestSessionDTO;
import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import com.example.javaquizhub.session.TestSession;
import com.example.javaquizhub.service.BookService;
import com.example.javaquizhub.service.TestAnswerService;
import com.example.javaquizhub.service.TestService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final TestAnswerService answerService;
    private final BookService bookService;

    @PostMapping  ("/book/{bookId}/start")
    public String startTesting(@PathVariable("bookId") int bookId,
                               @Valid TestSessionDTO testSessionDTO,
                               BindingResult result,Model model,HttpSession httpSession) {
        if(result.hasErrors()){
            model.addAttribute("book",bookService.getBookById(bookId));
            model.addAttribute("categories", Category.values());
            model.addAttribute("testSessionDTO", testSessionDTO);
            return "book-details";
        }

        List<Test> testList = testService
                .findTestsByBookIdAndCategoryInWithLimit(bookId,testSessionDTO
                        .getTestCategories(),testSessionDTO.getNumberOfTests());

        TestSession testSession = TestSession.init(testList);
        httpSession.setAttribute("testSession",testSession);
        return "redirect:/book/"+bookId+ "/test";
    }
   @GetMapping("/book/{bookId}/test")
    public String getNextTest(@PathVariable("bookId")int bookId,HttpSession httpSession, Model model){
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
        List<TestAnswer> correctAnswers = answerService.findCorrectAnswersByQuestionId(testId);

        testSession.addAnswer(testService.getTestById(testId),testAnswers,correctAnswers);

        return "redirect:/book/"+ bookId+"/test";
    }

    @GetMapping("/book/{bookId}/testResults")
    public String showPageWithTestResults(HttpSession httpSession,@PathVariable("bookId") int bookId, Model model){
        TestSession testSession = (TestSession) httpSession.getAttribute("testSession");
        model.addAttribute("testResults",testSession.getTestResults());
        httpSession.removeAttribute("testSession");
        return "test-result-page";
    }

}
