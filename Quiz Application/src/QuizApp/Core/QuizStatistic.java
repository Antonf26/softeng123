/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Anton
 */
public class QuizStatistic {
    public QuizStatistic()
    {
    }
    public List<QuizResult> quizResults = new ArrayList<QuizResult>();
    public String QuizTitle;
    public int QuizId;
    public List<QuestionStatistic> questionStats = new ArrayList<QuestionStatistic>(); 
    /**
     * Returns average percentage score attained by students on the quiz
     * @return 
     */
    public double AverageScore()
    {
        double sum = 0;
        for (QuizResult qr: quizResults)
        {
            sum += qr.PercentScore();
        }
        double avg = sum / quizResults.size();
        return avg;
    }
    /**
     * Return minimum percentage scroe for a student on the quis
     * @return 
     */
    public double minScore()
    {
       Collections.sort(quizResults);
       return quizResults.get(0).PercentScore();
    }
    
    /**
     * Returns maximum percentage score for a student on the quiz
     * @return 
     */
    public double maxScore()
    {
        return Collections.max(quizResults).PercentScore();
    }
    
    public String bestQuestion()
    {
       if(questionStats.size() > 0)
       {
           QuestionStatistic max = Collections.max(questionStats);
           return max.questionText + ", " + max.correctAnswers + " times.";
       }
       return "no statistics found!";
    }
    
    public String worstQuestion()
    {
        if(questionStats.size() > 0)
        {
            QuestionStatistic min = Collections.min(questionStats);
            return min.questionText + ", " + min.correctAnswers + " times.";
        }
        return "no statistics found!";
    }
    /**
     * Returns an array of percentage scores acheived by students for the quiz.
     * @return 
     */
    public double[] getScores()
    {
        double[] scores = new double[quizResults.size()];
        for (int i = 0; i < quizResults.size(); i++)
        {
            scores[i] = quizResults.get(i).PercentScore();
        }
        return scores;  
    }
    /**
     * Populates the questionStats field - this holds a list of questionStatistic objects, listing quesiton texts and the number of times 
     * the questions were answered correctly
     * @return 
     */
    public void populateQuestionStats()
    {
        for (QuizResult qr : quizResults)
        {
            for (QuizResultRow qrr : qr.ResultRows)
            {
                if (qrr.isCorrect)
                {
                    if(!questionStats.contains(qrr.questionText))
                    {
                        questionStats.add(new QuestionStatistic(qrr.questionText, 1));
                    }
                    else
                    {
                        questionStats.get(questionStats.indexOf(qrr.questionText)).correctAnswers += 1;
                    }
                }
            }
        }
    }
    
    
 
}
