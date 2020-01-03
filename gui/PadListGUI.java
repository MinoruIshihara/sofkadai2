import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class PadListGUI extends JFrame 
{
    public PadListGUI()
    {
        super("単語帳");
        ArrayList<String> padArray = new ArrayList<String>();
        ProcessBuilder getPadListPB = new ProcessBuilder("python3","../quizsystem/getPadList.py");
        try
        {
            Process getPadListProcess = getPadListPB.start();
            try
            {
                int errCode = getPadListProcess.waitFor();
                Scanner padListScanner = new Scanner(getPadListProcess.getInputStream());
                while(padListScanner.hasNextLine())
                {
                    padArray.add(padListScanner.nextLine());
                }
            }
            catch(InterruptedException e){System.out.println("Cannot Start Quiz System");}
        }
        catch(IOException e){System.out.println("Cannot Open file");}

        setBounds(100,100,540,960);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout padListLayout = new GridLayout(padArray.size()+1,1);
        contentPane.setLayout(padListLayout);

        JButton padButtons[] = new JButton[padArray.size()+1];

        for(int GridNum = 0; GridNum < padArray.size(); GridNum++)
        {
            padButtons[GridNum] = new JButton(padArray.get(GridNum));
            contentPane.add(padButtons[GridNum]);
        }
            padButtons[padArray.size()] = new JButton("＋新規作成");

            padButtons[padArray.size()].addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        NewPadGUI newPadGUI = new NewPadGUI();
                        dispose();
                    }
                }
            );

            contentPane.add(padButtons[padArray.size()]);
    }
}

