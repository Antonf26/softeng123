/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIDesign;


import java.awt.*;
import javax.swing.*;


public class GUI {
    
        private static JFrame MFrame;
        private JPanel MPanel;
        private JPanel MPanel2;
        private JPanel MPanel3;
        private JButton jButton1;
        private JButton jButton2;
        private JButton jButton3;
        private JButton jButton4;
        private JTextField jtField;
        
        public GUI(){
            gui();
        }
        public void gui(){
            
            /*
             *Create JFrame and apply settings for size and exiting
             */
            MFrame = new JFrame();
                       
            /*
             *Sets the main panel up
             */
            MPanel = new JPanel(new GridBagLayout());
            MPanel.setBackground(Color.WHITE);
            MPanel2 = new JPanel();
            MPanel2.setBackground(Color.WHITE);
            MPanel3 = new JPanel();
            MPanel3.setBackground(Color.BLACK);
            
            /*
             *Sets up Buttons
             */            
            jButton1= new JButton("Log in Screen");
            jButton2= new JButton("Take A Quiz");
            jButton3= new JButton("View Results");
            jButton4= new JButton("Exit");
            
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
            
            /*
             *Add items to the JPanel so they can be seen
             *gbc are the positioning for the buttons
             *they align virtically in MPanel1
             */
            gbc.gridx= 0;
            gbc.gridy= 0;
            MPanel.add(jButton1, gbc);
            gbc.gridx= 0;
            gbc.gridy= 1;
            MPanel.add(jButton2, gbc);
            gbc.gridx= 0;
            gbc.gridy= 2;
            MPanel.add(jButton3, gbc);
            gbc.gridx= 0;
            gbc.gridy= 3;
            MPanel.add(jButton4, gbc);
            gbc.gridx= 0;
            gbc.gridy= 4;
            MPanel2.add(jtField);
            
            /*
             *Add MPanel to the frame and apply settings for size and exiting
             */            
            MFrame.add(MPanel, BorderLayout.WEST);
            MFrame.add(MPanel2, BorderLayout.NORTH);
            MFrame.setVisible(true);
            MFrame.setSize(1024, 600);
            MFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
           
    }
    public static void main(String[] args) {
         GUI JF = new GUI();
         
         /*
          * as can be seen i have made MFrame static so that we can use it in outside
          * of the main method. this will allow us to set up action buttons to change
          * to whichever panel we will then be able to import panels created using 
          * netbeans GUI designing tools
          */
         MFrame.add(new NJP());
    }
}