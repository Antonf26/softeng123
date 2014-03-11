/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

import QuizApp.Core.Question;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */
public class Quiz {
    public Quiz(String QuizTitle)
    {
        this(QuizTitle, 0);
    }
    
    
    public Quiz(String QuizTitle, int QuizDbId)
    {
        this(QuizTitle, new ArrayList<Question>(), QuizDbId);
    }
    public Quiz(String QuizTitle, List<Question> QuestionList, int QuizDbId)
    {
        quizTitle = QuizTitle;
        questionList = QuestionList;
        quizDBId = QuizDbId;
        //SETTING THE BELOW HERE JUST SO THE PROPERTIES ARE INITIALISED, MAY NEED TO CREATE ANOTHER CONSTRUCTOR/RETHINK THEM.
        timeLimit = 30;
        available = true; 
        feedbackAvailable = true;
        navigationEnabled = true;
        showPracticeQuestion = true;
        randomiseQuestions = true;
        timeOutBehaviour = 0;
        
    }
    public List<Question> questionList;
    public int timeLimit;
    public String quizTitle; 
    public int quizDBId;
    public boolean feedbackAvailable;
    public boolean navigationEnabled;
    public boolean showPracticeQuestion;
    public boolean available;
    public boolean randomiseQuestions;
    public int timeOutBehaviour; 
    
    public int getQuizLength()
    {
        return questionList.size();
    }
    public void addQuestion(Question q)
    {
        questionList.add(q);
    }
    
    
}
