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
        dbId = 0;
    }
    public Answer(String AnswerText, boolean IsCorrect, int DbId)
    {
        answerText = AnswerText;
        isCorrect = IsCorrect;
        dbId = DbId;
    }
    public String answerText;
    public boolean isCorrect;
    public int dbId;
    
}
