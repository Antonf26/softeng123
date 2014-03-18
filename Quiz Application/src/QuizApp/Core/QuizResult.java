

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
public class QuizResult {
    public QuizResult()
    {
      ResultRows = new ArrayList<QuizResultRow>();
    }
    
    public List<QuizResultRow> ResultRows;
    
    
    
}
