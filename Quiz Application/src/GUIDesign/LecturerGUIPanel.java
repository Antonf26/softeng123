/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIDesign;

import static GUIDesign.GUI.mainFrame;
import java.awt.Color;
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
public class LecturerGUIPanel extends JPanel {
    
    private JButton CreateQuestionButton;
    private JButton ViewStatsButton;
    private JButton Exit;
    
    LecturerGUIPanel(){
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*
        *Sets up Buttons
        */
        CreateQuestionButton = new JButton("Create Question");
        ViewStatsButton = new JButton("View Statistics");
        Exit= new JButton("Exit");

        add(Box.createRigidArea(new Dimension(5,100)));
        add(CreateQuestionButton);
        add(Box.createRigidArea(new Dimension(5,10)));
        add(ViewStatsButton);
        add(Box.createRigidArea(new Dimension(5,400)));
        add(Exit);
        

        ActionListener CreateQuestionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GUI.removePanel();
                GUI.loadPanel(new Question.CreateQuestion());
            }
        };
     
      
        ActionListener ViewStatsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GUI.removePanel();
                GUI.loadPanel(new Results.ResultPanel(100, 321));
            }
        };
        
        
        ActionListener ExitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               mainFrame.dispose();
            }
        };
          
      
        //log_in.addActionListener(actionListener1);
        CreateQuestionButton.addActionListener(CreateQuestionListener);
        ViewStatsButton.addActionListener(ViewStatsListener);
        Exit.addActionListener(ExitListener);
    }
}
