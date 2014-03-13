package printDoc;

import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @author User
 */
public class printDoc {
    public printDoc(JTextPane e){
        try{
            boolean complete = e.print();
            if (complete){
                JOptionPane.showMessageDialog(null,"Done Printing","Information", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"Printing","Printer",JOptionPane.ERROR_MESSAGE);
            }
        }catch(PrinterException i){
            JOptionPane.showMessageDialog(null, i);

        }
  }
}
