import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class NewCardGUI extends JFrame implements ActionListener
{
    private JTextField titleText;
    JTextField quizTextField;
    JTextField ansTextField;
    String title;
    public NewCardGUI(String title)
    {
        super("新規単語帳");
        this.title = title;

        setBounds(100,100,290,76);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout newPadLayout = new GridLayout(0,2);
        contentPane.setLayout(newPadLayout);

        quizTextField = new JTextField("問題を入力");
        ansTextField = new JTextField("回答を入力");
        contentPane.add(quizTextField);
        contentPane.add(ansTextField);

        JButton createButton = new JButton("作成");
        createButton.addActionListener(this);
        contentPane.add(createButton);
    }
    public void actionPerformed(ActionEvent e)
    {
        String[] makeListProcArg = {"python3","../quizsystem/makeCard.py",quizTextField.getText(),ansTextField.getText()};
        SubProcessColler makeListProc = new SubProcessColler(makeListProcArg);
        makeListProc.runProcess();
        EditCardListGUI padListGUI = new EditCardListGUI(title);
        dispose();
    }
}
