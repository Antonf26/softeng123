/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Question;

/**
 *
 * @author Gareth
 */
import QuizApp.Core.Answer;
import java.util.ArrayList;
import java.util.List;

public class Question {
    public Question (String QuestionText)
    {
        questionText = QuestionText;
        answers = new ArrayList<Answer>();
        
    }
    public Question(String QuestionText, List<Answer> Answers, int CorrectIndex)
    {
        questionText = QuestionText;
        answers = Answers;
        isValidated = true;
        correctIndex = CorrectIndex;  
    }
    public String questionText;
    public List<Answer> answers;
    public boolean isValidated;
    public int correctIndex;
    public int dbId;
    
    public void CreateQuestion(String QuestionText, List<Answer> Answers, 
            int CorrectIndex)
    {
        //Connect to DB
        this.questionText = QuestionText;
        this.answers = Answers;
        this.correctIndex = CorrectIndex;
        this.isValidated = false;
        
        // Update database
    }
    
    public void ApproveQuestion(String QuestionText, List<Answer> Answers, 
            int CorrectIndex)
    {
        //Connect to DB
        this.questionText = QuestionText;
        this.answers = Answers;
        this.correctIndex = CorrectIndex;
        this.isValidated = true;
        
        // Update database
        
    }
    
    public void RejectQuestion(String QuestionText, List<Answer> Answers, 
            int CorrectIndex)
    {
        //Connect to DB
        this.questionText = QuestionText;
        this.answers = Answers;
        this.correctIndex = CorrectIndex;
        this.isValidated = false;
        
        // Update database
        
    }
    
    public void EditQuestion()
    {
        
    }

}
