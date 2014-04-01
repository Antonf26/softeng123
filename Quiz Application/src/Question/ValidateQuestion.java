/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Question;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import Helper.DbAccess;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
/**
 *
 * @author Gareth laptop
 */
public class ValidateQuestion extends javax.swing.JPanel implements ActionListener
{
    public String questionText;
    public String Answer1Text;
    public String Answer2Text;
    public String Answer3Text;
    public String Answer4Text;
    public boolean Answer1Correct = false;
    public boolean Answer2Correct = false;
    public boolean Answer3Correct = false;
    public boolean Answer4Correct = false;
    public int QuestionDbId;
    public int Answer1DbId;
    public int Answer2DbId;
    public int Answer3DbId;
    public int Answer4DbId;
    public int i = 0;
    public int QuestionCount;
    /**
     * Creates new form ValidateQuestion
     */
    public ValidateQuestion() 
    {
        initComponents();
        Approve_btn.addActionListener( this );
        Reject_btn.addActionListener( this );
        Edit_btn.addActionListener( this );
        Next_btn.addActionListener( this );
        Previous_btn.addActionListener( this );
        Submit_btn.addActionListener(this);
        Answer1_rdbtn.addActionListener( this );
        Answer2_rdbtn.addActionListener( this );
        Answer3_rdbtn.addActionListener( this );
        Answer4_rdbtn.addActionListener( this );
        
        this.setUp();
    }
    
    public void setUp()
    {        
        // Get questions
        List<Question> allQuestions = DbAccess.getAllQuestions(false);
        // creates values for size of list
        QuestionCount = allQuestions.size();
        
        if(QuestionCount == 0)
        {
            // Set Values
            QuestionText_txtbx.setText("No questions to validate");
            Answer1_txtbx.setText("N/A");
            Answer2_txtbx.setText("N/A");
            Answer3_txtbx.setText("N/A");
            Answer4_txtbx.setText("N/A");

            // set buttons and text boxes disabled
            Next_btn.setEnabled(false);
            Previous_btn.setEnabled(false);
            Submit_btn.setEnabled(false);
            Approve_btn.setEnabled(false);
            Reject_btn.setEnabled(false);
            Edit_btn.setEnabled(false);
            QuestionText_txtbx.setEnabled(false);
            Answer1_txtbx.setEnabled(false);
            Answer2_txtbx.setEnabled(false);
            Answer3_txtbx.setEnabled(false);
            Answer4_txtbx.setEnabled(false);
            Answer1_rdbtn.setEnabled(false);
            Answer2_rdbtn.setEnabled(false);
            Answer3_rdbtn.setEnabled(false);
            Answer4_rdbtn.setEnabled(false);
            
            // Message box pop up to say please select correct answer
            JOptionPane.showMessageDialog(null,"There are no more questions to validate.",
                                          "No Questions to Validate",JOptionPane.WARNING_MESSAGE);
        }
        
        else
        {
            Question s = new Question();
     
            s = allQuestions.get(i);
            List<Answer> sanswers = s.answers;
        
        
            // Assign values from the question to the variables
            questionText = s.questionText;
            QuestionDbId = s.dbId;
            Answer1Text = sanswers.get(0).answerText;
            Answer1Correct = sanswers.get(0).isCorrect;
            Answer1DbId = sanswers.get(0).dbId;
            Answer2Text = sanswers.get(1).answerText;
            Answer2Correct = sanswers.get(1).isCorrect;
            Answer2DbId = sanswers.get(1).dbId;
            Answer3Text = sanswers.get(2).answerText;
            Answer3Correct = sanswers.get(2).isCorrect;
            Answer3DbId = sanswers.get(2).dbId;
            Answer4Text = sanswers.get(3).answerText;
            Answer4Correct = sanswers.get(3).isCorrect;
            Answer4DbId = sanswers.get(3).dbId;
        
            // Display question and answer text
            QuestionText_txtbx.setText(questionText);
            Answer1_txtbx.setText(Answer1Text);
            Answer2_txtbx.setText(Answer2Text);
            Answer3_txtbx.setText(Answer3Text);
            Answer4_txtbx.setText(Answer4Text);
        
            // Set Correct Answer Radio buttons
            Answer1_rdbtn.setSelected(Answer1Correct);
            Answer2_rdbtn.setSelected(Answer2Correct);
            Answer3_rdbtn.setSelected(Answer3Correct);
            Answer4_rdbtn.setSelected(Answer4Correct);
        
            // Set Text Boxes to stop editing unless edit button is clicked
            QuestionText_txtbx.setEnabled(false);
            Answer1_txtbx.setEnabled(false);
            Answer2_txtbx.setEnabled(false);
            Answer3_txtbx.setEnabled(false);
            Answer4_txtbx.setEnabled(false);
        
            // Set radio buttons to stop editing unless edit button is clicked
            Answer1_rdbtn.setEnabled(false);
            Answer2_rdbtn.setEnabled(false);
            Answer3_rdbtn.setEnabled(false);
            Answer4_rdbtn.setEnabled(false);
        
            // Set Submit button as disabled to be changed when Edit_btn is clicked
            Submit_btn.setEnabled(false);  
        
            if(QuestionCount == 1)
            {
                // set Next and Previous button disabled
                Next_btn.setEnabled(false);
                Previous_btn.setEnabled(false);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        System.out.print(evt.getSource());
        if(evt.getSource().equals(Approve_btn))
        {
            //BUTTON CODE HERE
            this.approveQuestion();
        }
        if(evt.getSource().equals(Reject_btn))
        {
            //BUTTON CODE HERE
            this.rejectQuestion();
        }
        if(evt.getSource().equals(Edit_btn))
        {
            //Button code here
            this.editQuestion();
        }
        if(evt.getSource().equals(Submit_btn))
        {
            if(Answer1Correct || Answer2Correct || Answer3Correct || Answer4Correct)
            {
                //BUTTON CODE HERE
                this.editSubmit();       
            }
            else
            {
                // Message box pop up to say please select correct answer
                JOptionPane.showMessageDialog(null,"Please indicate correct answer.","Error",JOptionPane.WARNING_MESSAGE); 
            }
        }
        if(evt.getSource().equals(Next_btn))
        {
            //BUTTON CODE HERE
            this.nextQuestion();
        }
        if(evt.getSource().equals(Previous_btn))
        {
            //BUTTON CODE HERE
            this.previousQuestion();
        }
             
    }

    public void approveQuestion()
    {
        // Change the is validated field in the data base from false to true
        DbAccess.ToggleQuestionValidation(QuestionDbId, true );
        // Refresh the form
        this.setUp();
    }
    
    public void rejectQuestion()
    {
        // Change the is rejected field in the data base from false to true
        DbAccess.ToggleQuestionRejection(QuestionDbId, true );
        // Refresh the form
        this.setUp();
    }
    
    public void editQuestion()
    {
        // Set Buttons enabled and disabled
        Submit_btn.setEnabled(true);
        Approve_btn.setEnabled(false);
        Reject_btn.setEnabled(false);
        Edit_btn.setEnabled(false);
        
        // Set Text Boxes to enable editing once the edit button is clicked
        QuestionText_txtbx.setEnabled(true);
        Answer1_txtbx.setEnabled(true);
        Answer2_txtbx.setEnabled(true);
        Answer3_txtbx.setEnabled(true);
        Answer4_txtbx.setEnabled(true);
        
        // Set radio buttons to enable editing once the edit button is clicked
        Answer1_rdbtn.setEnabled(true);
        Answer2_rdbtn.setEnabled(true);
        Answer3_rdbtn.setEnabled(true);
        Answer4_rdbtn.setEnabled(true);
              
    }
    
    public void editSubmit()
    {
       // Edit question
        Question e = new Question();
        
        e.dbId = QuestionDbId;
        e.AuthorId = 1341131;
        e.isRejected = false;
        e.isValidated = false;
        e.questionText = QuestionText_txtbx.getText();
        e.answers.add(new Answer(Answer1_txtbx.getText(), Answer1Correct, Answer1DbId));
        e.answers.add(new Answer(Answer2_txtbx.getText(), Answer2Correct, Answer2DbId));
        e.answers.add(new Answer(Answer3_txtbx.getText(), Answer3Correct, Answer3DbId));
        e.answers.add(new Answer(Answer4_txtbx.getText(), Answer4Correct, Answer4DbId));
        
        Boolean updated = DbAccess.UpdateQuestion(e); 
        
        this.setUp();
    }
    
    public void nextQuestion()
    {
        if(i == QuestionCount -1 )
        {
            i = 0;
        }
        else
        {
            // Current Question Id incremented by 1
            i++; 
        }
 
        // Display question in form
        this.setUp();
    }
    
    public void previousQuestion()
    {   
        if(i == 0)
        {
            i = QuestionCount -1;
        }
        else
        {
            // Current Question Id incremented by 1
            i--; 
        }
        
        // Display question in form
        this.setUp();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Title_lbl = new javax.swing.JLabel();
        Answer4_lbl = new javax.swing.JLabel();
        Approve_btn = new javax.swing.JButton();
        Reject_btn = new javax.swing.JButton();
        Question_lbl = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Answer3_txtbx = new javax.swing.JTextArea();
        Answer3_lbl = new javax.swing.JLabel();
        AnswerDes_lbl = new javax.swing.JLabel();
        Answer1_lbl = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Answer4_txtbx = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        QuestionText_txtbx = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        Answer2_txtbx = new javax.swing.JTextArea();
        Edit_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Answer1_txtbx = new javax.swing.JTextArea();
        Answer2_lbl = new javax.swing.JLabel();
        Next_btn = new javax.swing.JButton();
        Previous_btn = new javax.swing.JButton();
        Answer2_rdbtn = new javax.swing.JRadioButton();
        Answer3_rdbtn = new javax.swing.JRadioButton();
        Submit_btn = new javax.swing.JButton();
        Answer4_rdbtn = new javax.swing.JRadioButton();
        Answer1_rdbtn = new javax.swing.JRadioButton();

        Title_lbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        Title_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title_lbl.setText("Validate Question");

        Answer4_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Answer4_lbl.setText("Answer 4");

        Approve_btn.setText("Approve");

        Reject_btn.setText("Reject");

        Question_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Question_lbl.setText("Please review question below:-");

        Answer3_txtbx.setColumns(20);
        Answer3_txtbx.setRows(5);
        jScrollPane4.setViewportView(Answer3_txtbx);

        Answer3_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Answer3_lbl.setText("Answer 3");
        Answer3_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        AnswerDes_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AnswerDes_lbl.setText("Please review answers and indicated correct answer below:-");

        Answer1_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Answer1_lbl.setText("Answer 1");

        Answer4_txtbx.setColumns(20);
        Answer4_txtbx.setRows(5);
        jScrollPane5.setViewportView(Answer4_txtbx);

        QuestionText_txtbx.setColumns(20);
        QuestionText_txtbx.setRows(5);
        jScrollPane1.setViewportView(QuestionText_txtbx);

        Answer2_txtbx.setColumns(20);
        Answer2_txtbx.setRows(5);
        jScrollPane3.setViewportView(Answer2_txtbx);

        Edit_btn.setText("Edit");

        Answer1_txtbx.setColumns(20);
        Answer1_txtbx.setRows(5);
        jScrollPane2.setViewportView(Answer1_txtbx);

        Answer2_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Answer2_lbl.setText("Answer 2");

        Next_btn.setText("Next");

        Previous_btn.setText("Previous");

        Answer2_rdbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer2_rdbtnActionPerformed(evt);
            }
        });

        Answer3_rdbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer3_rdbtnActionPerformed(evt);
            }
        });

        Submit_btn.setText("Submit");

        Answer4_rdbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer4_rdbtnActionPerformed(evt);
            }
        });

        Answer1_rdbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer1_rdbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(Title_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(Question_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Answer1_rdbtn)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Answer2_rdbtn)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(Answer3_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189)
                .addComponent(Answer4_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Answer3_rdbtn)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Answer4_rdbtn)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(Previous_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Approve_btn)
                .addGap(6, 6, 6)
                .addComponent(Edit_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Submit_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Reject_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AnswerDes_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Answer1_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182)
                        .addComponent(Answer2_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(Title_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Question_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(AnswerDes_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Answer1_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(Answer2_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(Answer1_rdbtn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(Answer2_rdbtn))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Answer3_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Answer4_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(Answer3_rdbtn))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(Answer4_rdbtn))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Previous_btn)
                    .addComponent(Approve_btn)
                    .addComponent(Edit_btn)
                    .addComponent(Submit_btn)
                    .addComponent(Reject_btn)
                    .addComponent(Next_btn)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Answer3_rdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer3_rdbtnActionPerformed
        Answer1Correct = false;
        Answer2Correct = false;
        Answer4Correct = false;
        Answer3Correct = true;
    }//GEN-LAST:event_Answer3_rdbtnActionPerformed

    private void Answer4_rdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer4_rdbtnActionPerformed
        Answer1Correct = false;
        Answer2Correct = false;
        Answer3Correct = false;
        Answer4Correct = true;
    }//GEN-LAST:event_Answer4_rdbtnActionPerformed

    private void Answer2_rdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer2_rdbtnActionPerformed
        Answer1Correct = false;
        Answer3Correct = false;
        Answer4Correct = false;
        Answer2Correct = true;
    }//GEN-LAST:event_Answer2_rdbtnActionPerformed

    private void Answer1_rdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer1_rdbtnActionPerformed
        Answer2Correct = false;
        Answer3Correct = false;
        Answer4Correct = false;
        Answer1Correct = true;
    }//GEN-LAST:event_Answer1_rdbtnActionPerformed
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Answer1_lbl;
    private javax.swing.JRadioButton Answer1_rdbtn;
    private javax.swing.JTextArea Answer1_txtbx;
    private javax.swing.JLabel Answer2_lbl;
    private javax.swing.JRadioButton Answer2_rdbtn;
    private javax.swing.JTextArea Answer2_txtbx;
    private javax.swing.JLabel Answer3_lbl;
    private javax.swing.JRadioButton Answer3_rdbtn;
    private javax.swing.JTextArea Answer3_txtbx;
    private javax.swing.JLabel Answer4_lbl;
    private javax.swing.JRadioButton Answer4_rdbtn;
    private javax.swing.JTextArea Answer4_txtbx;
    private javax.swing.JLabel AnswerDes_lbl;
    private javax.swing.JButton Approve_btn;
    private javax.swing.JButton Edit_btn;
    private javax.swing.JButton Next_btn;
    private javax.swing.JButton Previous_btn;
    private javax.swing.JTextArea QuestionText_txtbx;
    private javax.swing.JLabel Question_lbl;
    private javax.swing.JButton Reject_btn;
    private javax.swing.JButton Submit_btn;
    private javax.swing.JLabel Title_lbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
