import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class NewPadGUI extends JFrame implements ActionListener
{
    public NewPadGUI()
    {
        super("新規単語帳");

        setBounds(100,100,540,304);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout newPadLayout = new GridLayout(0,1);
        contentPane.setLayout(newPadLayout);

        JTextField titleText = new JTextField("タイトルを入力",20);
        contentPane.add(titleText);
    }
    public void actionPerformed(ActionEvent e)
    {
    }
}
