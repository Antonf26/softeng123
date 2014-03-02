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
public class Question {
    public Question(String QuestionText, Answer[] Answers)
    {
        questionText = QuestionText;
        answers = Answers;
        isValidated = true;
        
    }
    public String questionText;
    public Answer[] answers;
    public boolean isValidated;
}
