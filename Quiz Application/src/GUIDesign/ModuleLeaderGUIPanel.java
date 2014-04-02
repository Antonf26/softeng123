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
public class ModuleLeaderGUIPanel extends JPanel {
    private JButton CreateQuestionButton;
    private JButton ValidateQuestionButton;
    private JButton ViewStatsButton;
    private JButton CreateQuizButton;
    private JButton Exit;
    
    ModuleLeaderGUIPanel(){
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        /*
         *Sets up Buttons
         */
        CreateQuestionButton = new JButton("Create Question");
        ViewStatsButton = new JButton("View Statistics");
        ValidateQuestionButton = new JButton("Validate Question");
        CreateQuizButton = new JButton("Create Quiz");
        Exit= new JButton("Exit");

        add(Box.createRigidArea(new Dimension(5,100)));
        add(CreateQuestionButton);
        add(Box.createRigidArea(new Dimension(5,10)));
        add(ValidateQuestionButton);
        add(Box.createRigidArea(new Dimension(5,10)));
        add(CreateQuizButton);
        add(Box.createRigidArea(new Dimension(5,10)));
        add(ViewStatsButton);
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
               GUI.loadPanel(new Results.ResultPanel(100, 321));
            }
        };
        
        ActionListener actionListener4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               MFrame.dispose();
            }
        };
     ActionListener actionListener5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               GUI.removePanel();
                GUI.loadPanel(new Question.ValidateQuestion());
            }
        };
     ActionListener actionListener6 = new ActionListener() {
         @Override
            public void actionPerformed(ActionEvent actionEvent) {
               GUI.removePanel();
                GUI.loadPanel(new QuizGeneration.QuizGenerationForm());
            }
     };
    
      
      
      //log_in.addActionListener(actionListener1);
      CreateQuestionButton.addActionListener(actionListener2);
      ViewStatsButton.addActionListener(actionListener3);
      ValidateQuestionButton.addActionListener(actionListener5);
      CreateQuizButton.addActionListener(actionListener6);
      Exit.addActionListener(actionListener4);
    }
    
    

}
