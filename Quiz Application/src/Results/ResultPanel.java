/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Results;

import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Helper.DbAccess;
import Helper.DbAccess.*;
import QuizApp.Core.QuizResult;
import QuizApp.Core.User;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author User
 */
public class ResultPanel extends JPanel {
    private String newline = "/n";

    public ResultPanel() {
        
    }
    
    public ResultPanel(int quizid, int userid){
        
       BoxLayout boxLayout1 = new BoxLayout(this, BoxLayout.Y_AXIS);
       setLayout(boxLayout1);
       
       JTextArea question = new JTextArea();
       JScrollPane scroll = new JScrollPane(question);
       question.setEditable(false);              

       
       QuizResult Results = DbAccess.getQuizResult(quizid, userid);
       for(int i = 0; i < Results.ResultRows.size(); i++) { 
            question.append(  "Question:\n" + Results.ResultRows.get(i).questionText + " \n"
                            + "Correct Answer: " + Results.ResultRows.get(i).correctAnswerText + "\n"
                            + "Selected Answer: " + Results.ResultRows.get(i).selectedAnswerText + "\n\n"
                            );            
            add(scroll);
                  
       }
   }
}
