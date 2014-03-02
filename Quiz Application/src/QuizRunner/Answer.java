/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizRunner;

/**
 *
 * @author Anton
 */
public class Answer {
    public Answer(String AnswerText, boolean IsCorrect)
    {
        answerText = AnswerText;
        isCorrect = IsCorrect;
    }
    public String answerText;
    public boolean isCorrect;
    
}
