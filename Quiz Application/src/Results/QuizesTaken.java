/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Results;

import GUIDesign.GUI;
import static GUIDesign.GUI.MFrame;
import Helper.DbAccess;
import QuizApp.Core.Quiz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */
public class QuizesTaken{
    public static JPanel completeQuizes;
    
   public QuizesTaken(final int dbId) {
               
        /*
        * Layout for the panel, to align on the Y_AXIS (virtically)
        */
        completeQuizes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        BoxLayout boxlayout1 = new BoxLayout(completeQuizes, BoxLayout.Y_AXIS);
        completeQuizes.setLayout(boxlayout1);

        /*
        * Creates the scrollbar and textarea, and adds some formattign for the panel
        */
        JScrollPane scroll = new JScrollPane(completeQuizes);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel mainLabel =new JLabel("Results Available to View");
        
        completeQuizes.add(mainLabel);
        completeQuizes.add(Box.createRigidArea(new Dimension(5,30)));
        
        final Quiz[] quiz = DbAccess.getQuizzesbyUser(dbId, true);
        for(int i = 0; i < quiz.length; i++){
               final int quizid = quiz[i].quizDBId;
               String title = quiz[i].quizDBId + " - " + quiz[i].quizTitle; 
               JLabel lblTitle = new JLabel(title);
               JButton viewresults = new JButton("View Results");
               completeQuizes.add(lblTitle);
               completeQuizes.add(viewresults);
               completeQuizes.add(Box.createRigidArea(new Dimension(5,30)));
               
               
               
            ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               System.out.println(dbId);
               System.out.println(quizid);
                
                GUIDesign.GUI.removePanel();
                GUIDesign.GUI.loadPanel(new ResultPanel(dbId, quizid));
                }
            };

          //log_in.addActionListener(actionListener1);
          viewresults.addActionListener(actionListener);
            
        }
    }
    
}
