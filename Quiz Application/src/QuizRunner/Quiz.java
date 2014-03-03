/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizRunner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */
public class Quiz {
    public int dbId;
    public Quiz(String QuizTitle, int QuizDbId)
    {
        quizTitle = QuizTitle;
        questionList = new ArrayList<Question>();
        quizDBId = QuizDbId;
    }
    public Quiz(String QuizTitle, List<Question> QuestionList, int QuizDbId)
    {
        quizTitle = QuizTitle;
        questionList = QuestionList;
        quizDBId = QuizDbId;
    }
    public List<Question> questionList;
    public double timeLimit;
    public String quizTitle; 
    private int quizDBId;
    
    public int getQuizLength()
    {
        return questionList.size();
    }
    public void addQuestion(Question q)
    {
        questionList.add(q);
    }
    
    
}
