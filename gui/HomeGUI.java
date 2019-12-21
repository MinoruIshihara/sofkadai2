import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeGUI extends JFrame
{
    public HomeGUI()
    {
		super("HOME");
		setBounds(100,100,540,960);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		GridLayout homeLayout = new GridLayout(6,1);
		contentPane.setLayout(homeLayout);

		JButton wordCardPadButton = new JButton("単語帳");
		wordCardPadButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);

		JButton learningHistoryButton = new JButton("学習記録");
		learningHistoryButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);
		JButton todayLearningButton = new JButton("今日の学習");
		todayLearningButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);
		JButton todayScheduleButton = new JButton("スケジュール");
		todayScheduleButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);
		JButton examButton = new JButton("試験");
		examButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);
		JButton exitButton = new JButton("終了");
		exitButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			}
		);

		contentPane.add(wordCardPadButton);
		contentPane.add(learningHistoryButton);
		contentPane.add(todayLearningButton);
		contentPane.add(todayScheduleButton);
		contentPane.add(examButton);
		contentPane.add(exitButton);

		contentPane.add(learningHistoryButton);
		contentPane.add(todayLearningButton);
		contentPane.add(todayScheduleButton);
		contentPane.add(examButton);
		contentPane.add(exitButton);

    }
	public void homeShow()
	{
		setVisible(true);
	}
	public void homeHide()
	{
		setVisible(false);
	}
}
