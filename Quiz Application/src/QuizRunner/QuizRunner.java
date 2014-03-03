/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizRunner;
import Users.*;
/**
 *
 * @author Anton
 */
public class QuizRunner {
    public QuizRunner(Quiz quiz, User user)
    {
        QuizForm qfrm = new QuizForm(quiz, user);
        //qfrm.setVisible(true);
        qfrm.setAlwaysOnTop(true);
        
        
        
    }
    private String StudentId;
    private int currentQuestion;
    
    
   
}
