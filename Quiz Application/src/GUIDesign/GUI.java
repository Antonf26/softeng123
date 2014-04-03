/**
 * this Module contains the main GUI frame to which all the panels are added an 
 * removed as needed for the application to work. 
 */

package GUIDesign;


import Login.LoginPanel;
import QuizApp.Core.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class GUI {
    
    public static JFrame mainFrame;

    /*
     * What we do upon successful user Login.
     */
    public static void handleLogIn(User newUser) {
        
        user = newUser;
        titleField.setText(titleField.getText() + "| Logged in as: " + newUser.fullName);
        removePanel();
        
        switch (user.utype){        
        case Student:
            //Load the Student StudentGUIPanel
            StudentGUIPanel student = new StudentGUIPanel();
            loadButtonPanel(student);
            break;
            
        case Lecturer:
            //Load the Student LecturerGUIPanel
            LecturerGUIPanel lecturer = new LecturerGUIPanel();
            loadButtonPanel(lecturer);
            break;
            
        case ModuleLeader:
            //Load the Student ModuleLeaderGUIPanel
            ModuleLeaderGUIPanel moduleLeader = new ModuleLeaderGUIPanel();
            loadButtonPanel(moduleLeader);
            break;
        }

    }

    public static JPanel buttonpannel;
    private JPanel titlePanel;
    private static JTextField titleField;
    public static User user;
    private static Component e;
        
    public GUI(){
        gui();
    }
    
    
    
    public void gui(){
        /*
         *Create JFrame and apply settings for size and exiting
         */
        mainFrame = new JFrame();

        /*
         *Sets the main panel up
         */

        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);

        /*
         *Sets up the text field and applies the settings below
         */    
        titleField = new JTextField();
        titleField.setEditable(false);
        titleField.setFont(new java.awt.Font("Tahoma", 0, 12));
        titleField.setHorizontalAlignment(JTextField.RIGHT);
        titleField.setText("Cardiff Quiz Application");
        titleField.setBorder(null);

        titlePanel.add(titleField);

        /*
         *Add ButtonPanel to the frame and apply settings for size and exiting
         */
        mainFrame.add(e = new LoginPanel(), BorderLayout.CENTER);


        mainFrame.add(titlePanel, BorderLayout.NORTH);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
             
        ActionListener loginListener;
        loginListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removePanel();
                loadPanel(new LoginPanel());
            }
        };
    }
     
    
    /**
    * Displays login panel;
    */
    private void showLogin(){
        e = new LoginPanel();
        //ButtonPanel.setVisible(false);

        mainFrame.add(e);
        Dimension d = mainFrame.getSize();
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
    }
    
    
    public static void loadPanel(JPanel Panel){
        e = Panel;
        mainFrame.add(Panel, BorderLayout.CENTER);
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
    }

    
    public static void loadButtonPanel(JPanel Panel){
        buttonpannel = Panel;
        mainFrame.add(Panel, BorderLayout.WEST);
        Panel.setSize(600, 600);
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
    }
    
    
    public static void toggleButtonPanel(boolean makeVisible){
        buttonpannel.setVisible(makeVisible);
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
    }
    
      
    public static void removePanel() {
        if(e == null){
            return;
        }else{
            mainFrame.remove(e);
            mainFrame.invalidate();
            mainFrame.validate();
            mainFrame.repaint();
        }
    }
    
     
    public static void main(String[] args) {
        GUI JF = new GUI();
    }
}
