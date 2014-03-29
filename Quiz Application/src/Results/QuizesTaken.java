/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Results;

import Helper.DbAccess;
import QuizApp.Core.Quiz;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class QuizesTaken extends JPanel {
    
    public QuizesTaken(int userid){
        
        Quiz[] quiz = DbAccess.getQuizzesbyUser(userid, true);
        System.out.println(quiz.length);
    }
    
    public static void main (String [] arg){
        QuizesTaken q = new QuizesTaken(321);
    }
    
}
