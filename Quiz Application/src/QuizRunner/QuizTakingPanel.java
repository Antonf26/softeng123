/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizRunner;

import GUIDesign.GUI;
import Helper.DbAccess;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import QuizApp.Core.Quiz;
import QuizApp.Core.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.Timer;

/**
 *
 * @author Anton
 */
public class QuizTakingPanel extends javax.swing.JPanel {
    

    /**
     * Creates new form QuizTakingPanel
     */
    public QuizTakingPanel() {
        initComponents();
        user = GUI.user; //retrieving currently logged in user
        ShowQuizChoice();
        setVisible(true);
        
        }
    
        /**
         * Starts the quiz timer
         */
        private void startTimer()
        {
            quiztimer.start();   
        }
        
        /**
         * Shows quiz information and cheating warning.
         */
        private void DisplayInfo()
        {
           JTextPane InfoPane = new JTextPane();
           InfoPane.setSize(500, 500);
           InfoPane.setLocation(10, 10);
           InfoPane.setEditable(false);
           InfoPane.setText("THIS IS A WARNING");
           JButton StartButton = new JButton();
           StartButton.setText("Start Quiz");
           
           StartButton.setLocation(500, 500);
           StartButton.setSize(StartButton.getPreferredSize());
           StartButton.setLocation(500 - StartButton.getWidth(), 510);
           NextBtn.setVisible(false);
           PrevBtn.setVisible(false);
           StartButton.setVisible(true);
           StartButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                if (quiz.showPracticeQuestion)
                {
                    offerPracticeQuestion();
                }
                //Execute when button is pressed
                else
                {
                startQuiz();
                }
            }

            /**
             * Shows the practice question.
             */   
            
        });
           QuestionPanel.removeAll();
           QuestionPanel.add(StartButton);
           QuestionPanel.add(InfoPane);
           QuestionPanel.setVisible(true);
           QuestionPanel.repaint();            
        }
        private void offerPracticeQuestion() {
            JLabel pqInfoLabel = new JLabel("Would you like to try a practice question?");
            pqInfoLabel.setSize(pqInfoLabel.getPreferredSize());
            pqInfoLabel.setLocation(0, 20);
            JButton yespButton = new JButton("Yes");
            yespButton.setSize(yespButton.getPreferredSize());
            yespButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    displayPracticeQuestion();
                    
                }
            });
            
            JButton nopButton = new JButton("No, Start the Quiz!");
            nopButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    startQuiz();
                }
            });
            nopButton.setSize(nopButton.getPreferredSize());
            yespButton.setLocation(10, 100);
            nopButton.setLocation(yespButton.getWidth() + 20, 100);
            QuestionPanel.removeAll();
            QuestionPanel.add(pqInfoLabel);
            QuestionPanel.add(yespButton);
            QuestionPanel.add(nopButton);
            QuestionPanel.setVisible(true);
            QuestionPanel.repaint();
            
                  
                
            //
               }
        
        private void displayPracticeQuestion(){
            ShowQuestion(practiceQuestion, true);
            JOptionPane.showMessageDialog(RightPanel, "This is how question in the quiz will appear \n"
                    + "You select an answer by clicking the radio button next to it \n"
                    + "You can try it on this question - the resutls won't be evaluated or stored \n"
                    + "You can move between questions by clicking the buttons on the bottom right \n"
            + "Clicking the next button question will begin your quiz");
            
        }
        /**
         * Begins the process of a particular quiz
         */
        private void takeQuiz()
        {
          practiceQuestion = getPracticeQuestion();
        
        if (quiz.randomiseQuestions) 
                {
                    Collections.shuffle(quiz.questionList);
                }
        if (!quiz.navigationEnabled)
            {
                PrevBtn.setVisible(false);
            } 
        DisplayInfo();
        }
        
        
        /**
         * Starts the actual quiz.
         */
        private void startQuiz()
        {
            QuestionPanel.removeAll();
            this.repaint();
        
            NextBtn.setVisible(true);
            NextBtn.setText("Next");
            if (quiz.navigationEnabled)
            {
            PrevBtn.setVisible(true);
            PrevBtn.setText("Previous");
            }
            if (quiz.timeLimit > 0)
            {
                TimeLbl.setVisible(true);
                
                quiztimer = new Timer(1000, timerHit);
                timeLeft = quiz.timeLimit * 60;
                updateTimeLeft(quiz.timeLimit * 60);
            }
            qIndex = 0;
            qnumLbl.setVisible(true);
            ShowQuestion(quiz.questionList.get(qIndex));
        }
        
        private void updateTimeLeft(int timeLeft)
        {
            String seconds = String.format("%02d", timeLeft %60);
            String minutes = String.format("%02d", timeLeft / 60);
            String timestring = String.format("Time left: \n %s m : %s s", minutes, seconds);
            TimeLbl.setText(timestring);
        }
    
        /**
         * Handles the timer countdown
         */
    ActionListener timerHit = new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            timeLeft -= 1;
            updateTimeLeft(timeLeft);
            if (timeLeft == 0)
            {
                quiztimer.stop();
                performTimeOut();
            }
    }
    
        /**
         * What happens when the student runs out of time
         */
        private void performTimeOut() {
            
           confirmSubmission();
           JOptionPane.showMessageDialog(RightPanel, "Out of time, Bitch!");
           
        }
    };
    
    /**
     * Handles student selecting a quiz.
     */
    ActionListener quizChoiceListener = new ActionListener(){
        public void actionPerformed(ActionEvent evt)
        {
            int quizId = Integer.parseInt(evt.getActionCommand());
            //quiz = DbAccess.getQuiz();
            takeQuiz();
    
        }
    };
    
    /**
     * Stores selected response for a question
     */
    private void setResponse()
    {
        try{
        ButtonModel bm = AnswerButtons.getSelection();
        String selectedind = bm.getActionCommand();
        
        selectedAnswers.put(quiz.questionList.get(qIndex).dbId, Integer.parseInt(selectedind));
        }
        catch (NullPointerException npe)
        {
            //If no answer was selected, this gets thrown!! Catching it stops it breaking really.
        }
    }
    
    private void ShowQuestion(Question cq)
    {
        ShowQuestion(cq, false);
    }
    
    /**
     * Displays a question
     * @param cq - question object to display
     */
    private void ShowQuestion(Question cq, boolean isPractice) {
        
       
        QuestionPanel.removeAll();
        AnswerButtons.clearSelection();
        if (quiz.randomiseQuestions)
        {
            Collections.shuffle(cq.answers);
        }
        JLabel qtextLbl = new JLabel(cq.questionText);
        qtextLbl.setSize(qtextLbl.getPreferredSize());
        int ypos = 20;
        qtextLbl.setLocation(0, ypos);
        QuestionPanel.add(qtextLbl);
        int ind = 0;
        int selected = 0;
        if (selectedAnswers.containsKey(cq.dbId))
        {
            selected = selectedAnswers.get(cq.dbId);
        }
        
        for (Answer a : cq.answers)
        {
            ypos += 20;
            JRadioButton jrb = new JRadioButton(a.answerText);
            jrb.setSize(jrb.getPreferredSize());
            jrb.setLocation(0, ypos);
            jrb.getModel().setActionCommand(Integer.toString(a.dbId)); //this is how we can tell which answer was picked
           
            AnswerButtons.add(jrb);
            QuestionPanel.add(jrb);
             if(selected == a.dbId)
            {
                jrb.setSelected(true);
            }
            ind++;
        }
        //QuestionPanel.setSize(800, 800);
        this.repaint();
        this.getParent().repaint();
        QuestionPanel.setVisible(true);
        QuestionPanel.repaint();
        
        //setVisible(true);
        if(!isPractice)
        {
        qnumLbl.setText("Question " + Integer.toString(qIndex + 1) + " of " + quiz.questionList.size());
        if(!quiztimer.isRunning())
        {
            startTimer();
        }
        if(qIndex ==0)
        {
            PrevBtn.setEnabled(false);
        }
        else
        {
            PrevBtn.setEnabled(true);
        }
        if(qIndex ==quiz.questionList.size() - 1)
        {
            NextBtn.setText("Submit");
            NextBtn.setActionCommand("Submit");
        }
        else
        {
            NextBtn.setText("Next");
            NextBtn.setActionCommand("Next");
        }
        }
        else
        {
            NextBtn.setVisible(true);
            
            PrevBtn.setVisible(true);
            PrevBtn.setEnabled(false);
            PrevBtn.setText("Previous");
            NextBtn.setText("Next");
            NextBtn.setActionCommand("Start");
        }
        
    }
    
    /**
     * Stores results of the quiz 
     */

    private void saveResults() {
        
        Object[] options = {"Yes", "No"};
        int reallySubmit =  JOptionPane.showOptionDialog(QuestionPanel,
                               "This will finish the quiz and store your answers, are you sure?", //Object message,
                               "Really Submit?", //String title
                               JOptionPane.YES_NO_OPTION, //int optionType
                               JOptionPane.INFORMATION_MESSAGE, //int messageType
                               null, //Icon icon,
                               options, //Object[] options,
                               options[1]);
        
        if (reallySubmit == JOptionPane.NO_OPTION)
        {
            return;
        }
        Boolean saved = DbAccess.saveResults(quiz.quizDBId, selectedAnswers, user.dbId);
        if (saved)
        {
            confirmSubmission();
        }
    }
    
    /*
    Displays quizzes available to the user 
    */
    private void ShowQuizChoice()
    {
        
        JPanel qcPanel = new JPanel();
        qcPanel.setSize(600, 600);
        
        TimeLbl.setVisible(false);
        qnumLbl.setVisible(false);
        Quiz[] availableQuizzes = getQuizzes();
        if (availableQuizzes.length > 0)
        {
            int l = 100;
            for (Quiz q : availableQuizzes)
            {
                JLabel qnLabel = new JLabel();
                qnLabel.setText(q.quizTitle);
                JButton qButton = new JButton("Take Quiz");
                qButton.setActionCommand(Integer.toString(q.quizDBId));
                qButton.addActionListener(quizChoiceListener);
                qnLabel.setLabelFor(qButton);
                qButton.setLocation(l, 50);
                l += 20;
                qcPanel.add(qButton);
                qcPanel.add(qnLabel);
                
            }
        }
        else 
        {
            JLabel noQLabel = new JLabel("No Quizzes Available");
            qcPanel.add(noQLabel);
        }
        QuestionPanel.add(qcPanel);
        QuestionPanel.setVisible(true);
        QuestionPanel.repaint();
        
            
            
            
    }
    
    /**
     * Returns quizzes not yet completed by the student
     * @return Array of quiz objects (not populated with questions yet)
     */
    private Quiz[] getQuizzes()
    {
        return DbAccess.getQuizzesbyUser(user.dbId, false);        
    }
    private Quiz quiz;
    private int qIndex;
    private User user;
    private int timeLeft;
    private Map<Integer, Integer> selectedAnswers = new HashMap<Integer, Integer>();
    Timer quiztimer;
    private Question practiceQuestion;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AnswerButtons = new javax.swing.ButtonGroup();
        LeftPanel = new javax.swing.JPanel();
        qnumLbl = new javax.swing.JLabel();
        TimeLbl = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        PrevBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        QuestionPanel = new javax.swing.JPanel();

        qnumLbl.setText("jLabel2");

        TimeLbl.setText("jLabel1");

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeLbl)
                    .addComponent(qnumLbl))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TimeLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qnumLbl)
                .addContainerGap(810, Short.MAX_VALUE))
        );

        PrevBtn.setText("jButton1");
        PrevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtnActionPerformed(evt);
            }
        });

        NextBtn.setText("jButton2");
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout QuestionPanelLayout = new javax.swing.GroupLayout(QuestionPanel);
        QuestionPanel.setLayout(QuestionPanelLayout);
        QuestionPanelLayout.setHorizontalGroup(
            QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        QuestionPanelLayout.setVerticalGroup(
            QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                .addContainerGap(614, Short.MAX_VALUE)
                .addComponent(PrevBtn)
                .addGap(18, 18, 18)
                .addComponent(NextBtn)
                .addGap(42, 42, 42))
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QuestionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QuestionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrevBtn)
                    .addComponent(NextBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LeftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LeftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action for the "previous question" button
     * @param evt 
     */
    private void PrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtnActionPerformed

        setResponse();
        if(qIndex > 0)
        {
            ShowQuestion( quiz.questionList.get(qIndex -1));
            qIndex--;
        }
    }//GEN-LAST:event_PrevBtnActionPerformed
    /**
     * Action for the "next question" button
     * @param evt 
     */
    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        // TODO add your handling code here:
        
        setResponse();
        String ac = evt.getActionCommand();
        //Show next question
        if (ac.equals("Next") && qIndex < quiz.questionList.size() - 1)
        {
            qIndex++;
            ShowQuestion( quiz.questionList.get(qIndex));
        }
        else if (ac.equals("Submit"))
        {
            saveResults();
        }
        else if (ac.equals("Start"))
        {
            startQuiz();
        }
    }//GEN-LAST:event_NextBtnActionPerformed
    /**
     * Informs the user of their results having been successfully saved
     */
    private void confirmSubmission()
    {
        QuestionPanel.removeAll();
        JLabel submitted = new JLabel();
        submitted.setText("Your results have been saved!");
        submitted.setSize(submitted.getPreferredSize());
        submitted.setLocation(100, 100);
        QuestionPanel.add(submitted);
        if (quiz.feedbackAvailable)
        {
            JButton viewFeedback = new JButton();
            viewFeedback.setText("View Feedback");
            viewFeedback.setSize(viewFeedback.getPreferredSize());
            viewFeedback.setLocation(100, 150);
            QuestionPanel.add(viewFeedback);
        }
        QuestionPanel.repaint();
        QuestionPanel.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup AnswerButtons;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JPanel QuestionPanel;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JLabel TimeLbl;
    private javax.swing.JLabel qnumLbl;
    // End of variables declaration//GEN-END:variables

    /**
     * Returns Question object to be used as the practice question
     * @return 
     */
    private Question getPracticeQuestion() {
       
        Question pq = new Question();
        pq.questionText = "What is the capital of the Republic of Ireland?";
        pq.answers.add(new Answer("Dublin", false));
        pq.answers.add(new Answer("Belfast", false));
        pq.answers.add(new Answer("Dubai", false));
        pq.answers.add(new Answer("New York", false));
        return pq; 
    }
}
