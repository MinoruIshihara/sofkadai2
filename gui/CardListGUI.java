import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class CardListGUI extends JFrame implements ActionListener
{
    Container contentPane;
    CardLayout rootCardLayout;
    public CardListGUI(String title)
    {
        super(title);

        String[] getPadInfoProcessArg = {"python3","../quizsystem/getPadInfo.py",title};
        SubProcessColler getPadInfoProcess = new SubProcessColler(getPadInfoProcessArg);
        ArrayList<String> padInfoArray = getPadInfoProcess.runProcess();
        int maxCardNum = Integer.parseInt(padInfoArray.get(0));

        ArrayList<JPanel> cardArray = new ArrayList<JPanel>(0);
        for(int cardNum=0;cardNum<maxCardNum;cardNum++)
        {
            String[] getCardProcessArg = {"python3","../quizsystem/getCard.py",title,String.valueOf(cardNum)};
            SubProcessColler getCardListProcess = new SubProcessColler(getCardProcessArg);

            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());

            JPanel cardTextPanel = new JPanel();
            cardTextPanel.setLayout(new CardLayout());
            JLabel quizTextLabel = new JLabel(getCardListProcess.runProcess().get(0));
            quizTextLabel.setPreferredSize(new Dimension(360,160));
            quizTextLabel.setBorder(new LineBorder(Color.blue,10,true));
            quizTextLabel.setFont(new Font("MS ゴシック",Font.BOLD,16));
            quizTextLabel.setHorizontalAlignment(JLabel.CENTER);
            cardTextPanel.add(quizTextLabel);

            JButton returnButton = new JButton("裏返す");
            returnButton.setActionCommand("return:"+String.valueOf(cardNum));

            cardPanel.add(cardTextPanel,BorderLayout.CENTER);
            cardPanel.add(returnButton,BorderLayout.LINE_END);

            cardArray.add(cardPanel);
        }

        setBounds(100,100,540,960);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        contentPane = getContentPane();
        rootCardLayout = new CardLayout();
        contentPane.setLayout(rootCardLayout);

        ArrayList<JPanel> cardListPanels = new ArrayList<JPanel>(0);
        GridLayout cardListLayout = new GridLayout(6,1);
        for(int panelNum=0;panelNum<(maxCardNum+1)/5;panelNum++)
        {
            cardListPanels.add(new JPanel());
            cardListPanels.get(panelNum).setLayout(cardListLayout);
            for(int cardNum=0;cardNum<5;cardNum++)
            {
                cardListPanels.get(panelNum).add(cardArray.get(panelNum*5+cardNum));
                System.out.println(cardArray.get(panelNum*5+cardNum).getAccessibleContext());
            }

            JPanel nextBackPanel = new JPanel();
            nextBackPanel.setLayout(new GridLayout(1,2));
            JButton backButton = new JButton("Back");
            backButton.addActionListener(this);
            backButton.setActionCommand("Back");
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(this);
            nextButton.setActionCommand("Next");
            nextBackPanel.add(backButton);
            nextBackPanel.add(nextButton);            
            cardListPanels.get(panelNum).add(nextBackPanel);

            contentPane.add(cardListPanels.get(panelNum));
        }

    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Next"))
        {
            rootCardLayout.next(contentPane);
            System.out.println("Next");
        }
        else if(e.getActionCommand().equals("Back"))
        {
            rootCardLayout.previous(contentPane);
            System.out.println("Back");
        }
    }
}
