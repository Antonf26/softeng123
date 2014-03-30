/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIDesign;

import static GUIDesign.GUI.MFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class StudentGUIPanel extends JPanel {
    private JButton QuizScreen;
    private JButton Results;
    private JButton Exit;
    
    StudentGUIPanel(){
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        /*
         *Sets up Buttons
         */
        QuizScreen= new JButton("Take A Quiz");
        Results= new JButton("View Results");
        Exit= new JButton("Exit");

        add(Box.createRigidArea(new Dimension(5,100)));
        add(QuizScreen);
        add(Box.createRigidArea(new Dimension(5,10)));
        add(Results);
        add(Box.createRigidArea(new Dimension(5,400)));
        add(Exit);
               
        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GUI.removePanel();
                GUI.loadPanel(new Question.CreateQuestion());
            }
        };
     
      
       
        ActionListener actionListener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               GUI.removePanel();
               GUI.loadPanel(new Results.ResultPanel(100, GUI.user.dbId));
            }
        };
        
        ActionListener actionListener4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               MFrame.dispose();
            }
        };
    
      
      
      //log_in.addActionListener(actionListener1);
      QuizScreen.addActionListener(actionListener2);
      Results.addActionListener(actionListener3);
      Exit.addActionListener(actionListener4);
    }
    
    

}
