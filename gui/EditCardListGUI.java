import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class EditCardListGUI extends JFrame implements ActionListener
{
    Container contentPane;
    CardLayout rootCardLayout;
    ArrayList<JPanel> cardListPanels;
    ArrayList<JPanel> cardArray;
    ArrayList<GridLayout> cardPanelLayoutArray;
    ArrayList<JPanel> cardTextPanelList;
    ArrayList<JTextField> textFieldArrAns;
    ArrayList<JTextField> textFieldArr;
    String title;

    public EditCardListGUI(String title)
    {
        super(title);
        this.title = title;

        String[] getPadInfoProcessArg = {"python3","../quizsystem/getPadInfo.py",title};
        SubProcessColler getPadInfoProcess = new SubProcessColler(getPadInfoProcessArg);
        ArrayList<String> padInfoArray = getPadInfoProcess.runProcess();
        int maxCardNum = Integer.parseInt(padInfoArray.get(0));

        cardPanelLayoutArray = new ArrayList<GridLayout>(0);
        cardArray = new ArrayList<JPanel>(0);
        cardTextPanelList = new ArrayList<JPanel>(0);
        textFieldArr = new ArrayList<JTextField>(0);
        textFieldArrAns = new ArrayList<JTextField>(0);

        for(int cardNum=0;cardNum<maxCardNum;cardNum++)
        {
            String[] getCardProcessArg = {"python3","../quizsystem/getCard.py",title,String.valueOf(cardNum)};
            SubProcessColler getCardProcess = new SubProcessColler(getCardProcessArg);

            String[] getAnswerProcessArg = {"python3","../quizsystem/getAnswer.py",title,String.valueOf(cardNum)};
            SubProcessColler getAnswerProcess = new SubProcessColler(getAnswerProcessArg);

            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());

            JPanel cardTextPanel = new JPanel();
            GridLayout cardPanelLayout = new GridLayout(1,2);
            cardPanelLayoutArray.add(cardPanelLayout);
            cardTextPanel.setLayout(cardPanelLayout);

            JTextField quizTextField = new JTextField(getCardProcess.runProcess().get(0));
            textFieldArr.add(quizTextField);
            JTextField ansTextField = new JTextField(getAnswerProcess.runProcess().get(0));
            textFieldArrAns.add(ansTextField);
            cardTextPanel.add(quizTextField);
            cardTextPanel.add(ansTextField);

            cardTextPanelList.add(cardTextPanel);

            JButton returnButton = new JButton("保存");
            returnButton.setActionCommand("save:"+String.valueOf(cardNum));
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

        cardListPanels = new ArrayList<JPanel>(0);
        GridLayout cardListLayout = new GridLayout(6,1);
        for(int panelNum=0;panelNum<(maxCardNum+5)/5;panelNum++)
        {
            cardListPanels.add(new JPanel());
            cardListPanels.get(panelNum).setLayout(cardListLayout);
            for(int cardNum=0;cardNum<5&&panelNum*5+cardNum<cardArray.size();cardNum++)
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
        JButton newCardButton = new JButton("新規作成");
        newCardButton.setActionCommand("NEW");
        newCardButton.addActionListener(this);
        cardListPanels.get((maxCardNum+5)/5-1).add(newCardButton);
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
        else if(e.getActionCommand().matches("save:.*"))
        {
            int cardNum = Integer.parseInt(e.getActionCommand().split(":",0)[1]);
            System.out.println(cardNum);
            System.out.println(textFieldArr.get(cardNum).getText());
            System.out.println(textFieldArrAns.get(cardNum).getText());
        }
        else if(e.getActionCommand().equals("NEW"))
        {
            NewCardGUI newCardGUI = new NewCardGUI(title);
            dispose();
        }
    }
}
