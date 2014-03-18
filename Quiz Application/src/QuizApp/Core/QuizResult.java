

package QuizApp.Core;
import QuizApp.Core.Quiz;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import Users.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Anton
 */
public class QuizResult implements Comparable<QuizResult>{
    @Override    
    public int compareTo(QuizResult qr)
    {
        return (int)(PercentScore() - qr.PercentScore());
    }
    
    
    public QuizResult()
    {
      ResultRows = new ArrayList<QuizResultRow>();
    }
    
    public List<QuizResultRow> ResultRows;
    
    public int NumberCorrect()
    {
        int result = 0;
        for(QuizResultRow rr : ResultRows)
        {
            if(rr.isCorrect)
            {
                result += 1;
            }
        }
        return result;
    }
    public int TotalQuestions()
    {
        return ResultRows.size();
    }

    public double PercentScore()
    {
        double percScore =  (double)this.NumberCorrect() / (double)this.TotalQuestions() * 100.0;
        return percScore;
    }
    
 

    
}
