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
    ArrayList<CardLayout> cardPanelLayoutArray;
    ArrayList<JPanel> cardTextPanelList;

    public CardListGUI(String title)
    {
        super(title);

        String[] getPadInfoProcessArg = {"python3","../quizsystem/getPadInfo.py",title};
        SubProcessColler getPadInfoProcess = new SubProcessColler(getPadInfoProcessArg);
        ArrayList<String> padInfoArray = getPadInfoProcess.runProcess();
        int maxCardNum = Integer.parseInt(padInfoArray.get(0));

        cardPanelLayoutArray = new ArrayList<CardLayout>(0);
        ArrayList<JPanel> cardArray = new ArrayList<JPanel>(0);
        cardTextPanelList = new ArrayList<JPanel>(0);

        for(int cardNum=0;cardNum<maxCardNum;cardNum++)
        {
            String[] getCardProcessArg = {"python3","../quizsystem/getCard.py",title,String.valueOf(cardNum)};
            SubProcessColler getCardProcess = new SubProcessColler(getCardProcessArg);

            String[] getAnswerProcessArg = {"python3","../quizsystem/getAnswer.py",title,String.valueOf(cardNum)};
            SubProcessColler getAnswerProcess = new SubProcessColler(getAnswerProcessArg);

            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());

            JPanel cardTextPanel = new JPanel();
            CardLayout cardPanelLayout = new CardLayout();
            cardPanelLayoutArray.add(cardPanelLayout);
            cardTextPanel.setLayout(cardPanelLayoutArray.get(cardNum));

            JLabel quizTextLabel = new JLabel(getCardProcess.runProcess().get(0));
            quizTextLabel.setPreferredSize(new Dimension(360,160));
            quizTextLabel.setBorder(new LineBorder(Color.blue,10,true));
            quizTextLabel.setFont(new Font("MS ゴシック",Font.BOLD,16));
            quizTextLabel.setHorizontalAlignment(JLabel.CENTER);
            JLabel answerTextLabel = new JLabel(getAnswerProcess.runProcess().get(0));
            answerTextLabel.setPreferredSize(new Dimension(360,160));
            answerTextLabel.setBorder(new LineBorder(Color.blue,10,true));
            answerTextLabel.setFont(new Font("MS ゴシック",Font.BOLD,16));
            answerTextLabel.setHorizontalAlignment(JLabel.CENTER);
            cardTextPanel.add(quizTextLabel);
            cardTextPanel.add(answerTextLabel);

            cardTextPanelList.add(cardTextPanel);

            JButton returnButton = new JButton("裏返す");
            returnButton.setActionCommand("return:"+String.valueOf(cardNum));
            returnButton.addActionListener(this);

            cardPanel.add(cardTextPanelList.get(cardNum),BorderLayout.CENTER);
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
        else
        {
            int cardNum = Integer.parseInt(e.getActionCommand().split(":",0)[1]);
            System.out.println(cardNum);
            cardPanelLayoutArray.get(cardNum).next(cardTextPanelList.get(cardNum));
        }
    }
}
