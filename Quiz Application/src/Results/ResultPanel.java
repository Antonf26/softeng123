/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Results;

import javax.swing.JPanel;
import Helper.DbAccess;
import Helper.DbAccess.*;
import QuizApp.Core.QuizResult;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author User
 */
public class ResultPanel extends JPanel {
   
    public ResultPanel() {
        
    }
    
    public ResultPanel(int quizid, int userid){
        
       BoxLayout boxLayout1 = new BoxLayout(this, BoxLayout.Y_AXIS);
       setLayout(boxLayout1);
       
       JTextArea question = new JTextArea();
       JScrollPane scroll = new JScrollPane(question);
       question.setEditable(false);   
       add(scroll);

       
       QuizResult Results = DbAccess.getQuizResult(quizid, userid);
       double pecentage = Results.PercentScore();
       question.append("Percentage = " + pecentage + "%");
       
       for(int i = 1; i < Results.ResultRows.size(); i++) { 
            question.append(  "\n\nQuestion:\n" + Results.ResultRows.get(i).questionText + " \n"
                            + "Correct Answer: " + Results.ResultRows.get(i).correctAnswerText + "\n"
                            + "Selected Answer: " + Results.ResultRows.get(i).selectedAnswerText + "\n\n"
                            );            
                             
       }
   }
}
