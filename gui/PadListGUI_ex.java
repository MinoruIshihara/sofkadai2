import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class PadListGUI_ex extends JFrame implements ActionListener
{
    Container contentPane;
    CardLayout rootPadLayout;
    ArrayList<JPanel> padPanelList;
    ArrayList<GridLayout> padPanelLayoutList;
    ArrayList<JButton> padButtonList;
    ArrayList<JButton> editButtonList;

    public PadListGUI_ex()
    {
        super("単語帳");
        setBounds(100,100,540,960);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        padPanelList = new ArrayList<JPanel>(0);
        padPanelLayoutList = new ArrayList<GridLayout>(0);
        padButtonList = new ArrayList<JButton>(0);
        editButtonList = new ArrayList<JButton>(0);

        String[] getPadProcessArg = {"python3","../quizsystem/getPadList.py"};
        SubProcessColler getPadProcess = new SubProcessColler(getPadProcessArg);
        ArrayList<String> padNameList = getPadProcess.runProcess();

        for(int padNum=0;padNum<padNameList.size();padNum++)
        {
            JPanel padPanel = new JPanel();
            GridLayout padPanelLayout = new GridLayout(1,2);
            padPanel.setLayout(padPanelLayout);

            JButton padButton = new JButton(padNameList.get(padNum));
            padButton.addActionListener(this);
            padButton.setActionCommand(padNameList.get(padNum));
            padButton.setPreferredSize(new Dimension(360,160));

            JButton editButton = new JButton("edit");
            editButton.addActionListener(this);
            editButton.setActionCommand("edit:"+padNameList.get(padNum));

            padPanel.add(padButton);
            padPanel.add(editButton);

            padPanelList.add(padPanel);
            padPanelLayoutList.add(padPanelLayout);
            padButtonList.add(padButton);
            editButtonList.add(editButton);
        }

        contentPane = getContentPane();
        rootPadLayout = new CardLayout();
        contentPane.setLayout(rootPadLayout);

        ArrayList<JPanel> padListPanels = new ArrayList<JPanel>(0);

        for(int panelNum=0;panelNum<(padNameList.size()+1)/5;panelNum++)
        {
            padListPanels.add(new JPanel());
            padListPanels.get(panelNum).setLayout(new GridLayout(6,1));

            for(int padNum=0;padNum<5;padNum++)
            {
                padListPanels.get(panelNum).add(padPanelList.get(panelNum*5+padNum));
                System.out.println(padPanelList.get(panelNum*5+padNum).getAccessibleContext());
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
            padListPanels.get(panelNum).add(nextBackPanel);

            contentPane.add(padListPanels.get(panelNum));
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Next"))
        {
            rootPadLayout.next(contentPane);
            System.out.println("Next");
        }
        else if(e.getActionCommand().equals("Back"))
        {
            rootPadLayout.previous(contentPane);
            System.out.println("Back");
        }
        else
        {
            String padName = e.getActionCommand().split(":",0)[1];
            System.out.println(padName);
            //padPanelLayoutArray.get(padNum).next(padTextPanelList.get(padNum));
        }
    }
}
