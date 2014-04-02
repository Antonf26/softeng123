/**
 * This is the panel for showing all the quizes the user has completed
 * it will then allow you to see which quizes are available to view the results
 * in the event no quizes have been completed it will prompt you to take a quiz
 */

package Results;

import Helper.DbAccess;
import QuizApp.Core.Quiz;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class QuizesTaken{
    public static JPanel completeQuizes;

    public QuizesTaken(final int dbId) {

        /*
         * Add the Pane; and then creates the
         * Layout for the panel, to align on the Y_AXIS (virtically)
         */
        completeQuizes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        BoxLayout quizListlayout = new BoxLayout(completeQuizes, BoxLayout.Y_AXIS);
        completeQuizes.setLayout(quizListlayout);
        
        //Creates the scrollbar and textarea, and adds some formatting for the completeQuizesScroll
        JScrollPane completeQuizesScroll = new JScrollPane(completeQuizes);
        completeQuizesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel mainLabel =new JLabel("Results Available to View");
       
        completeQuizes.add(mainLabel);
        completeQuizes.add(Box.createRigidArea(new Dimension(5,30)));

        // This gets the completed quizes from the db an puts it into an array
        Quiz[] quizArray = DbAccess.getQuizzesbyUser(dbId, true);
        
        /*
         * This if statment is to check if the user has completed any quizes
         * if the user hasn't completed any it will have an array length of 0
         * and perform the action in the if statment
         *
         * If the array length is greater than 0 it will perform the else statment
         * which creates the Buttons to view feedback for each quiz completed
         */
        if(quizArray.length == 0){
            
            // creates a label indicating that no quizes have been completed
            JLabel noCompletedQuizLabel = new JLabel("***You have not completed any Quizes, "
                                                    + "please click on take quiz***");
            completeQuizes.add(noCompletedQuizLabel);
            
        }else{
            
            //this loop is for getting the infromation out of the array
            for(int i = 0; i < quizArray.length; i++){

                // Create a string from quizDBId and quizTitle to in
                String labelTitle = quizArray[i].quizDBId + " - " + quizArray[i].quizTitle;
                
                //Creates a lable with the quiz information
                JLabel quizTitleLabel = new JLabel(labelTitle);
                
                //Creates a button to view the results for this quiz
                JButton viewResultsButton = new JButton("View Results");
                
                //This is so the quizDBId is passed to the action listner below
                viewResultsButton.setActionCommand(Integer.toString(quizArray[i].quizDBId));
                
                //add the quizTitleLabel and viewResultsButton to the panel  
                //the Box.createRigidArea adds space after each item
                completeQuizes.add(quizTitleLabel);
                completeQuizes.add(viewResultsButton);
                completeQuizes.add(Box.createRigidArea(new Dimension(5,30)));


            ActionListener viewResultsListener = new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt){
                    //Gets the interger from the setActionCommand above
                    int quizId = Integer.parseInt(evt.getActionCommand());
                    
                    //Call the removePanel removal and loadPanel to load the results
                    GUIDesign.GUI.removePanel();
                    GUIDesign.GUI.loadPanel(new ResultPanel(dbId, quizId));
                    }
                };

                viewResultsButton.addActionListener(viewResultsListener);
            }
        }
    }
}
