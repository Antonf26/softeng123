/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */
public class Question {
    
    public Question()
    {
        answers = new ArrayList<Answer>();
    }
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
    }
    public String questionText;
    public List<Answer> answers;
    public boolean isValidated;
    public int dbId;
    public int AuthorId;
    
}
