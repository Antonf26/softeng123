/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIDesign;


import Helper.DbAccess;
import Login.LoginPanel;
import QuizApp.Core.Quiz;
import QuizApp.Core.User;
import QuizApp.Core.User.UserType;
import QuizRunner.QuizTakingPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class GUI {
    
    private static JFrame MFrame;

    /*
    What we do upon successful user Login.
    */
    public static void handleLogIn(User newUser) {
        user = newUser;
        removePanel();
        switch (user.utype){
            case Student:
                //just trying out the quiz here
                StudentGUIPanel student = new StudentGUIPanel();
                loadButtonPanel(student);
           
                break;
            case Lecturer:
                break;
            case ModuleLeader:
                break;
        }
        
    }

    public static JPanel buttonpannel;
    private JPanel MPanel2;
    private JPanel MPanel3;
    private JTextField jtField;
    public static User user;
    private static Component e;
        
    public GUI(){
        gui();
        //showLogin();
    }
    
    
    
    public void gui(){
        /*
         *Create JFrame and apply settings for size and exiting
         */
        MFrame = new JFrame();

        /*
         *Sets the main panel up
         */

        MPanel2 = new JPanel();
        MPanel2.setBackground(Color.WHITE);
        MPanel3 = new JPanel();
        MPanel3.setBackground(Color.BLACK);
        
        /*
         *Sets up the text field and applies the settings below
         */    
        jtField = new JTextField();
        jtField.setEditable(false);
        jtField.setFont(new java.awt.Font("Tahoma", 0, 12));
        jtField.setHorizontalAlignment(JTextField.RIGHT);
        jtField.setText("Cardiff Quiz Application");
        jtField.setBorder(null);
        
        MPanel2.add(jtField);

        /*
         *Add ButtonPanel to the frame and apply settings for size and exiting
         */
        MFrame.add(e = new LoginPanel(), BorderLayout.CENTER);
        
 
        MFrame.add(MPanel2, BorderLayout.NORTH);
        
        MFrame.setVisible(true);
        MFrame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        MFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
             
      ActionListener actionListener1;
        actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                
                removePanel();
                
                
                e = new LoginPanel();
                e.setSize(600,600);
                
                MFrame.add(new LoginPanel());
                MFrame.invalidate();
                MFrame.validate();
                MFrame.repaint();
            }
        };
      
 
    }
     /**
      * Displays login panel;
      */
     private void showLogin()
      {
        
        e = new LoginPanel();
        //ButtonPanel.setVisible(false);
        
        MFrame.add(e);
        Dimension d = MFrame.getSize();
        MFrame.invalidate();
        MFrame.validate();
        MFrame.repaint();
      }
     static void loadPanel(JPanel Panel)
     {
         e = Panel;
         MFrame.add(Panel);
         Panel.setSize(600, 600);
         MFrame.invalidate();
         MFrame.validate();
         MFrame.repaint();
     }
     
     static void loadButtonPanel(JPanel Panel)
     {
         buttonpannel = Panel;
         MFrame.add(Panel, BorderLayout.WEST);
         Panel.setSize(600, 600);
         MFrame.invalidate();
         MFrame.validate();
         MFrame.repaint();
     }
      
     static void removePanel() {
        if(e == null){
            return;
        }else{
            MFrame.remove(e);
            MFrame.invalidate();
            MFrame.validate();
            MFrame.repaint();
        }
            
     }
     private void toggleButtons(boolean ButtonsShown)
     {
         //ButtonPanel.setVisible(ButtonsShown);
         
     }
    public static void main(String[] args) {
         GUI JF = new GUI();
         
         /*
          * as can be seen i have made MFrame static so that we can use it in outside
          * of the main method. this will allow us to set up action buttons to change
          * to whichever panel we will then be able to import panels created using 
          * netbeans GUI designing tools
          */
         //MFrame.add(new NJP());
    }
}
