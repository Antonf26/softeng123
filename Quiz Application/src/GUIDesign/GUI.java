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
                
                Quiz q = DbAccess.getQuizzes()[0];
                q.questionList = DbAccess.getQuizQuestions(q.quizDBId);
                QuizTakingPanel qtp = new QuizTakingPanel(q);
                loadPanel(qtp);
                break;
            case Lecturer:
                break;
            case ModuleLeader:
                break;
        }
        
    }
    private JPanel ButtonPanel;
    private JPanel MPanel2;
    private JPanel MPanel3;
    private JButton log_in;
    private JButton QuizScreen;
    private JButton Results;
    private JButton Exit;
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
        ButtonPanel = new JPanel(new GridBagLayout());
        ButtonPanel.setBackground(Color.WHITE);
        MPanel2 = new JPanel();
        MPanel2.setBackground(Color.WHITE);
        MPanel3 = new JPanel();
        MPanel3.setBackground(Color.BLACK);
        
        /*
         *Sets up Buttons
         */            
        log_in = new JButton("Log in Screen");
        QuizScreen= new JButton("Take A Quiz");
        Results= new JButton("View Results");
        Exit= new JButton("Exit");

        /*
         *Sets up the text field and applies the settings below
         */    
        jtField = new JTextField();
        jtField.setEditable(false);
        jtField.setFont(new java.awt.Font("Tahoma", 0, 12));
        jtField.setHorizontalAlignment(JTextField.RIGHT);
        jtField.setText("Cardiff Quiz Application");
        jtField.setBorder(null);

        /*
         *Activates GridBagConstraints 
         * this is used for formating the layout of swing
         */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(10,10,5,5);
        gbc.gridheight = 10;
        gbc.gridwidth =  10;

        /*
         *Add items to the JPanel so they can be seen
         *gbc are the positioning for the buttons
         *they align virtically in MPanel1
         */
        gbc.gridx = 0;
        gbc.gridy = 10;
        ButtonPanel.add(log_in, gbc);
        gbc.gridx = 0;
        gbc.gridy = 20;
        ButtonPanel.add(QuizScreen, gbc);
        gbc.gridx = 0;
        gbc.gridy = 30;
        ButtonPanel.add(Results, gbc);
        gbc.gridx = 0;
        gbc.gridy = 40;
        ButtonPanel.add(Exit, gbc);
        gbc.gridx = 0;
        gbc.gridy = 50;
        MPanel2.add(jtField);

        /*
         *Add ButtonPanel to the frame and apply settings for size and exiting
         */
        MFrame.add(ButtonPanel, BorderLayout.WEST);
        //MFrame.add(MPanel3, BorderLayout.EAST);
        MFrame.add(MPanel2, BorderLayout.NORTH);
        
        MFrame.setVisible(true);
        MFrame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        MFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
             
      ActionListener actionListener1;
        actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                
                //MFrame.remove(e);
                
                
                e = new LoginPanel();
                e.setSize(600,600);
                
                MFrame.add(e);
                MFrame.invalidate();
                MFrame.validate();
                MFrame.repaint();
            }
        };
      
      ActionListener actionListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        
        MFrame.remove(e);
        e = new NJP2();
        MFrame.add(e);
        MFrame.invalidate();
        MFrame.validate();
        MFrame.repaint();
      }
    };
     
      
       
      ActionListener actionListener3 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        removePanel();
        loadPanel(new Results.ResultPanel(100, 321));
        
      }
    };
    
      
      
      log_in.addActionListener(actionListener1);
      QuizScreen.addActionListener(actionListener2);
      Results.addActionListener(actionListener3);
    }
     /**
      * Displays login panel;
      */
     private void showLogin()
      {
        
        e = new LoginPanel();
        ButtonPanel.setVisible(false);
        
        MFrame.add(e);
        Dimension d = MFrame.getSize();
        MFrame.invalidate();
        MFrame.validate();
        MFrame.repaint();
      }
     private static void loadPanel(JPanel Panel)
     {
         e = Panel;
         MFrame.add(Panel);
         MFrame.invalidate();
         MFrame.validate();
         MFrame.repaint();
     }
      
     private static void removePanel()
     {
         MFrame.remove(e);
         MFrame.invalidate();
        MFrame.validate();
        MFrame.repaint();
     }
     private void toggleButtons(boolean ButtonsShown)
     {
         ButtonPanel.setVisible(ButtonsShown);
         
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
