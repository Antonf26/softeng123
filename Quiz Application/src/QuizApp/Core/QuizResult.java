/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;
import QuizApp.Core.Quiz;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import Users.*;
/**
 *
 * @author Anton
 */
public class QuizResult {
    public QuizResult(Quiz Quiz, User user)
    {
        quiz = Quiz;
    }
    
    private Quiz quiz;
    public void recordAnswer(Question q, Answer a){
        
        
    }
    
    
}
