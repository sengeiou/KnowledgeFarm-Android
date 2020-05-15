package com.knowledge_farm.front.question.controller;

import com.knowledge_farm.entity.*;
import com.knowledge_farm.front.question.service.FrontQuestionService;
import com.knowledge_farm.util.PageUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FrontQuestionController
 * @Description
 * @Author 张帅华
 * @Date 2020-05-14 16:25
 */
@Controller
@RequestMapping("/admin/question")
public class FrontQuestionController {
    @Resource
    private FrontQuestionService frontQuestionService;
    @Value("${excel.subjects}")
    public List<String> subjects;

    @GetMapping("/toTest")
    public String toTest(){
        return "test";
    }

    @GetMapping("/toAdd")
    public String toAdd(@RequestParam("questionTypeId") Integer questionId, Model model){
        List<QuestionType> questionTypeList = this.frontQuestionService.findAllQuestionType();
        for(QuestionType questionType : questionTypeList){
            if(questionType.getId() == questionId){
                model.addAttribute("questionType", questionType);
            }
        }
        List<String> gradeList = new ArrayList<>();
        gradeList.add("一年级上");
        gradeList.add("一年级下");
        gradeList.add("二年级上");
        gradeList.add("二年级下");
        gradeList.add("三年级上");
        gradeList.add("三年级下");
        model.addAttribute("grades", gradeList);
        model.addAttribute("subjects", subjects);
        return "question-add";
    }

    @GetMapping("/toEdit")
    public String toEdit(@RequestParam("id") Integer id, Model model){
        Question question = this.frontQuestionService.findQuestionById(id);
        if(question != null){
            List<String> gradeList = new ArrayList<>();
            gradeList.add("一年级上");
            gradeList.add("一年级下");
            gradeList.add("二年级上");
            gradeList.add("二年级下");
            gradeList.add("三年级上");
            gradeList.add("三年级下");
            model.addAttribute("question", question);
            model.addAttribute("grades", gradeList);
        }
        return "question-edit";
    }

    @GetMapping("/findAllQuestion")
    public String findAllQuestion(@RequestParam(value = "questionTitle", required = false) String questionTitle,
                                          @RequestParam(value = "questionTypeId", required = false) Integer questionTypeId,
                                          @RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                          Model model){
        Page<Question> page = this.frontQuestionService.findAllQuestion(questionTitle, questionTypeId, grade, pageNumber, pageSize);
        PageUtil<Question> pageUtil = new PageUtil<>(pageNumber, pageSize);
        pageUtil.setTotalCount((int) page.getTotalElements());
        pageUtil.setList(page.getContent());
        List<String> gradeList = new ArrayList<>();
        gradeList.add("一年级上");
        gradeList.add("一年级下");
        gradeList.add("二年级上");
        gradeList.add("二年级下");
        gradeList.add("三年级上");
        gradeList.add("三年级下");
        model.addAttribute("grades", gradeList);
        model.addAttribute("subjects", subjects);
        model.addAttribute("questionPage", pageUtil);
        switch (questionTypeId){
            case 1:
                return "question-singleChoice-list";
            case 2:
                return "question-completion-list";
            case 3:
                return "question-judgment-list";
            default:
                return "";
        }
    }

    @PostMapping("/deleteOneQuestion")
    @ResponseBody
    public String deleteOneQuestion(@RequestParam("id") Integer id){
        try {
            this.frontQuestionService.deleteQuestion(id);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/deleteMultiQuestion")
    @ResponseBody
    public String deleteMultiQuestion(@RequestParam("deleteStr") String deleteStr){
        String deleteIds[] = deleteStr.split(",");
        List<Integer> idList = new ArrayList<>();
        for(String id : deleteIds){
            idList.add(Integer.parseInt(id));
        }
        try {
            this.frontQuestionService.deleteQuestionByIdList(idList);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/addSingleChoiceQuestion")
    @ResponseBody
    public String addSingleChoiceQuestion(@RequestParam("questionTitle") String questionTitle,
                                          @RequestParam("subject") String subject,
                                          @RequestParam("grade") Integer grade,
                                          @RequestParam("answer") String answer,
                                          @RequestParam("choice1") String choice1,
                                          @RequestParam("choice2") String choice2,
                                          @RequestParam("choice3") String choice3){
        try {
            SingleChoice singleChoice = new SingleChoice();
            QuestionType questionType = this.frontQuestionService.findQuestionTypeById(1);
            QuestionTitle title = new QuestionTitle();
            title.setTitle(questionTitle);
            singleChoice.setQuestionType(questionType);
            singleChoice.setSubject(subject);
            singleChoice.setGrade(grade);
            singleChoice.setAnswer(answer);
            singleChoice.setChoice1(choice1);
            singleChoice.setChoice2(choice2);
            singleChoice.setChoice3(choice3);
            singleChoice.setQuestionTitle(title);
            title.setQuestion(singleChoice);
            this.frontQuestionService.save(singleChoice);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/addCompletionQuestion")
    @ResponseBody
    public String addCompletionQuestion(@RequestParam("questionTitle") String questionTitle,
                                        @RequestParam("subject") String subject,
                                        @RequestParam("grade") Integer grade,
                                        @RequestParam("answer") String answer){
        try {
            Completion completion = new Completion();
            QuestionType questionType = this.frontQuestionService.findQuestionTypeById(2);
            QuestionTitle title = new QuestionTitle();
            title.setTitle(questionTitle);
            completion.setQuestionType(questionType);
            completion.setSubject(subject);
            completion.setGrade(grade);
            completion.setAnswer(answer);
            completion.setQuestionTitle(title);
            title.setQuestion(completion);
            this.frontQuestionService.save(completion);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/addJudgmentQuestion")
    @ResponseBody
    public String addJudgmentQuestion(@RequestParam("questionTitle") String questionTitle,
                                      @RequestParam("subject") String subject,
                                      @RequestParam("grade") Integer grade,
                                      @RequestParam("answer") Integer answer,
                                      @RequestParam("choice") Integer choice){
        try {
            Judgment judgment = new Judgment();
            QuestionType questionType = this.frontQuestionService.findQuestionTypeById(3);
            QuestionTitle title = new QuestionTitle();
            title.setTitle(questionTitle);
            judgment.setQuestionType(questionType);
            judgment.setSubject(subject);
            judgment.setGrade(grade);
            judgment.setAnswer(answer);
            judgment.setChoice(choice);
            judgment.setQuestionTitle(title);
            title.setQuestion(judgment);
            this.frontQuestionService.save(judgment);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/editSingleChoiceQuestion")
    @ResponseBody
    public String editSingleChoiceQuestion(@RequestParam("id") Integer id,
                                           @RequestParam("questionTitle") String questionTitle,
                                           @RequestParam("grade") Integer grade,
                                           @RequestParam("answer") String answer,
                                           @RequestParam("choice1") String choice1,
                                           @RequestParam("choice2") String choice2,
                                           @RequestParam("choice3") String choice3){
        try {
            SingleChoice singleChoice = (SingleChoice) this.frontQuestionService.findQuestionById(id);
            singleChoice.getQuestionTitle().setTitle(questionTitle);;
            singleChoice.setGrade(grade);
            singleChoice.setAnswer(answer);
            singleChoice.setChoice1(choice1);
            singleChoice.setChoice2(choice2);
            singleChoice.setChoice3(choice3);
            this.frontQuestionService.save(singleChoice);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/editCompletionQuestion")
    @ResponseBody
    public String editCompletionQuestion(@RequestParam("id") Integer id,
                                         @RequestParam("questionTitle") String questionTitle,
                                         @RequestParam("grade") Integer grade,
                                         @RequestParam("answer") String answer){
        try {
            Completion completion = (Completion) this.frontQuestionService.findQuestionById(id);
            completion.getQuestionTitle().setTitle(questionTitle);;
            completion.setGrade(grade);
            completion.setAnswer(answer);
            this.frontQuestionService.save(completion);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @PostMapping("/editJudgmentQuestion")
    @ResponseBody
    public String editJudgmentQuestion(@RequestParam("id") Integer id,
                                       @RequestParam("questionTitle") String questionTitle,
                                       @RequestParam("grade") Integer grade,
                                       @RequestParam("answer") Integer answer,
                                       @RequestParam("choice") Integer choice){
        try {
            Judgment judgment = (Judgment) this.frontQuestionService.findQuestionById(id);
            judgment.getQuestionTitle().setTitle(questionTitle);;
            judgment.setGrade(grade);
            judgment.setAnswer(answer);
            judgment.setChoice(choice);
            this.frontQuestionService.save(judgment);
            return Result.SUCCEED;
        }catch (Exception e){
            e.printStackTrace();
            return Result.FAIL;
        }
    }

    @GetMapping(value = "/exportExcelModel")
    public void exportExcelModel(@RequestParam("questionTypeId") Integer questionTypeId, HttpServletResponse response) {
        try {
            //设置文件名(避免文件名中文乱码，将UTF8打散重组成ISO-8859-1编码方式)
            String fileName = new String ("知识农场题目.xlsx".getBytes("UTF8"),"ISO-8859-1");
            XSSFWorkbook workbook = this.frontQuestionService.exportExcelModel(questionTypeId);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/exportExcelData")
    public void exportExcelData(@RequestParam("questionTypeId") Integer questionTypeId, HttpServletResponse response) {
        try {
            //设置文件名(避免文件名中文乱码，将UTF8打散重组成ISO-8859-1编码方式)
            String fileName = new String ("知识农场题目.xlsx".getBytes("UTF8"),"ISO-8859-1");
            XSSFWorkbook workbook = this.frontQuestionService.exportExcelData(questionTypeId);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/importExcelToEdit")
    @ResponseBody
    public String importExcelToEdit(@RequestParam("questionTypeId") Integer questionTypeId, @RequestParam("file") MultipartFile file){
        try {
            return this.frontQuestionService.importExcelToEdit(questionTypeId, file);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @PostMapping("/importExcelToAdd")
    @ResponseBody
    public String importExcelToAdd(@RequestParam("questionTypeId") Integer questionTypeId, @RequestParam("file") MultipartFile file){
        try {
            return this.frontQuestionService.importExcelToAdd(questionTypeId, file);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
