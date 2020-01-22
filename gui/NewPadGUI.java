import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class NewPadGUI extends JFrame implements ActionListener
{
    private JTextField titleText;
    public NewPadGUI()
    {
        super("新規単語帳");

        setBounds(100,100,290,76);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout newPadLayout = new GridLayout(0,1);
        contentPane.setLayout(newPadLayout);

        titleText = new JTextField("タイトルを入力",20);
        titleText.setPreferredSize(new Dimension(200,15));
        contentPane.add(titleText);

        JButton createButton = new JButton("作成");
        createButton.addActionListener(this);
        contentPane.add(createButton);
    }
    public void actionPerformed(ActionEvent e)
    {
        String[] makeListProcArg = {"python3","../quizsystem/makeCard.py",titleText.getText()};
        SubProcessColler makeListProc = new SubProcessColler(makeListProcArg);
        makeListProc.runProcess();
        PadListGUI_ex padListGUI = new PadListGUI_ex();
        dispose();
    }
}
