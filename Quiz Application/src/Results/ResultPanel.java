/**
 * 
 */

package Results;

import Helper.DbAccess;
import QuizApp.Core.QuizResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class ResultPanel extends JPanel {
   
    public ResultPanel(int userid, int quizid){
        
        /*
         * Creates a toolbar with a print button
         */
        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
        JButton printbutton = new JButton("Print");
        toolbar.add(printbutton);
        this.add(toolbar);
        
       /*
        * Layout for the panel, to align on the Y_AXIS (virtically)
        */
       BoxLayout boxlayout1 = new BoxLayout(this, BoxLayout.Y_AXIS);
       setLayout(boxlayout1);
       
       /*
        * Creates the scrollbar and textarea, and adds some formattign for the panel
        */
       final JTextArea question = new JTextArea();
       JScrollPane scroll = new JScrollPane(question);
       scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       question.setEditable(false);   
       add(scroll);
       
       /*
        * Get the functions from QuizResults and get the intergers
        * the intergers below get the percentage, total marks and 
        * number of questions to be put into the text pane
        */
       QuizResult Results = DbAccess.getQuizResult(quizid,userid);
       double pecentage = Results.PercentScore();
       DecimalFormat percentageFormat = new DecimalFormat("#.00");
       double totalcorrect = Results.NumberCorrect();
       double numberofquestions = Results.TotalQuestions();
              
       /*
        * The first part appends the scores and percentage achieved by the student
        *
        * The loop below gets the questions from the array Results.ResultRow
        * for each item in the array it will put the question text, the correct answer
        * and the answer the user has selected.
        */
       question.append("Percentage = " + percentageFormat.format(pecentage) + "%");
       question.append("\nTotal = " + totalcorrect + "/" + numberofquestions);
              
       for(int i = 0; i < Results.ResultRows.size(); i++) { 
            question.append(  "\n\nQuestion:\n" + Results.ResultRows.get(i).questionText + " \n"
                            + "Correct Answer: " + Results.ResultRows.get(i).correctAnswerText + "\n"
                            + "Selected Answer: " + Results.ResultRows.get(i).selectedAnswerText + "\n\n"
                            );
            System.out.println(Results.ResultRows.size());
       }
       
       /*
        * Action to print when the button in the toolbar is pushed
        * it will print the questions in the text area
        */
       printbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printDoc.printDoc print = new printDoc.printDoc(question);
            }
        });
   }
}
