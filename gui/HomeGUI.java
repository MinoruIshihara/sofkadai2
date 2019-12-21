import javax.swing.*;
import java.awt.*;

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
		//wordCardPadButton.setPreferredSize(new Dimension(180,160));
		JButton learningHistoryButton = new JButton("学習記録");
		JButton todayLearningButton = new JButton("今日の学習");
		JButton todayScheduleButton = new JButton("スケジュール");
		JButton exsamButton = new JButton("試験");
		JButton exitButton = new JButton("終了");

		contentPane.add(wordCardPadButton);
		contentPane.add(learningHistoryButton);
		contentPane.add(todayLearningButton);
		contentPane.add(todayScheduleButton);
		contentPane.add(exsamButton);
		contentPane.add(exitButton);

		contentPane.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			}
		);
		contentPane.add(learningHistoryButton);
		contentPane.add(todayLearningButton);
		contentPane.add(todayScheduleButton);
		contentPane.add(exsamButton);
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
