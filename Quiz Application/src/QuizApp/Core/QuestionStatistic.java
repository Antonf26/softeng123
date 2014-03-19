/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

/**
 *
 * @author Anton
 */
public class QuestionStatistic implements Comparable<QuestionStatistic> {
   
    public QuestionStatistic(String QuestionText, int CorrectAnswers)
    {
        questionText = QuestionText;
        correctAnswers = CorrectAnswers;
    }
    
     /** 
     * How to compare with others - we need this for sorting, finding max, etc.
     * @param o
     * @return 
     */
    public int compareTo(QuestionStatistic o)
    {
        return correctAnswers - o.correctAnswers;
    }
    
    public boolean equals(Object o)
    {
        if (!(o instanceof String))
        {
            return false;
        }
        String qText = (String) o;
        
        return (questionText == qText);
    }
    
    public String questionText;
    public int correctAnswers = 0;
    
}
