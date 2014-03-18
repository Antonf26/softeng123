/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Anton
 */
public class QuizStatistic {
    public QuizStatistic()
    {
    }
    public List<QuizResult> quizResults = new ArrayList<>();
    public String QuizTitle;
    public int QuizId;
    
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
    public double minScore()
    {
       Collections.sort(quizResults);
       return quizResults.get(0).PercentScore();
    }
    public double maxScore()
    {
        Collections.sort(quizResults);
        return quizResults.get(quizResults.size() - 1).PercentScore();
    }
    
}
