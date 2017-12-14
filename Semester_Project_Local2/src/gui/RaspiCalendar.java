package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RaspiCalendar {

	private static final int WIDTH = 1300;
	private static final int HEIGHT = 650;
	private static TIMESOFDAY timeofday;
	private static JFrame raspiRevolutionaries = new JFrame();

	public static void main(String[] args) {
		timeofday = TIMESOFDAY.MORNING;
		calendarSetup();
		controlSetup();

		try {
			DataRetrieval.establishConnection();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		raspiRevolutionaries.setTitle("Raspi Revolutionaries Calendar");
		raspiRevolutionaries.getContentPane().setLayout(new FlowLayout());
		raspiRevolutionaries.getContentPane().setBackground(Color.BLACK);
		raspiRevolutionaries.setSize(WIDTH, HEIGHT);
		raspiRevolutionaries.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		raspiRevolutionaries.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					DataRetrieval.closeConnection();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				raspiRevolutionaries.dispose();
				System.exit(0);
			}
		});
		raspiRevolutionaries.setVisible(true);
		raspiRevolutionaries.pack();

	}

	private static void calendarSetup() {
		JPanel calendarPane = new JPanel();
		JPanel buttonPane = new JPanel();
		GregorianCalendar calendar = new GregorianCalendar();
		CalendarView calendarView = new CalendarView(calendar, 450, 450);

		JButton view = new JButton("View Selection");
		view.setFont(new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 34));
		view.setBackground(Color.black);
		view.setForeground(Color.green);
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DataRetrieval.retrieveData();
					Data grab = new Data(calendar.get(1), calendar.get(2) + 1, calendar.get(5), timeofday);
					if(grab.selAct == false){		
						JOptionPane.showMessageDialog(null, "Insufficient data to average selected timeframe\nShowing available data only-");
					}
					new fredsGUI(grab, timeofday);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});

		JButton left = new JButton("<<");
		left.setBackground(Color.black);
		left.setForeground(Color.green);
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calendarView.prevMonth();
			}
		});

		JButton right = new JButton(">>");
		right.setBackground(Color.black);
		right.setForeground(Color.green);
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calendarView.nextMonth();
			}
		});
		
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setBackground(Color.BLACK);
		buttonPane.add(left);
		buttonPane.add(view);
		buttonPane.add(right);
		buttonPane.setPreferredSize(new Dimension(0, 70));
		buttonPane.setVisible(true);

		calendarView.setBackground(Color.BLACK);

		calendarPane.setLayout(new BorderLayout());
		calendarPane.setBackground(Color.BLACK);
		calendarPane.add(calendarView, BorderLayout.CENTER);
		calendarPane.add(buttonPane, BorderLayout.SOUTH);

		raspiRevolutionaries.add(calendarPane);
	}

	private static void controlSetup() {
		JPanel controlPane = new JPanel();
		controlPane.setLayout(new GridLayout(5, 1));
		controlPane.setBackground(Color.BLACK);

		JRadioButton morning = new JRadioButton("Morning");
		morning.setFont(new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 14));
		morning.setForeground(Color.green);
		morning.setBackground(Color.BLACK);
		morning.setSelected(true);

		JRadioButton afternoon = new JRadioButton("Afternoon");
		afternoon.setFont(new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 14));
		afternoon.setForeground(Color.green);
		afternoon.setBackground(Color.BLACK);

		JRadioButton evening = new JRadioButton("Evening");
		evening.setFont(new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 14));
		evening.setForeground(Color.green);
		evening.setBackground(Color.BLACK);

		JRadioButton night = new JRadioButton("Night");
		night.setFont(new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 14));
		night.setForeground(Color.green);
		night.setBackground(Color.BLACK);

		morning.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (morning.isSelected()) {
					afternoon.setSelected(false);
					evening.setSelected(false);
					night.setSelected(false);
					timeofday = TIMESOFDAY.MORNING;
				}

			}

		});
		afternoon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (afternoon.isSelected()) {
					morning.setSelected(false);
					evening.setSelected(false);
					night.setSelected(false);
					timeofday = TIMESOFDAY.AFTERNOON;
				}

			}

		});
		evening.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (evening.isSelected()) {
					afternoon.setSelected(false);
					morning.setSelected(false);
					night.setSelected(false);
					timeofday = TIMESOFDAY.EVENING;
				}

			}

		});
		night.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (night.isSelected()) {
					afternoon.setSelected(false);
					evening.setSelected(false);
					morning.setSelected(false);
					timeofday = TIMESOFDAY.NIGHT;
				}

			}

		});

		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(RaspiCalendar.class.getResource("/images/piLogoGreenXS.png")));
		logo.setBackground(Color.green);

		controlPane.add(morning);
		controlPane.add(afternoon);
		controlPane.add(evening);
		controlPane.add(night);
		controlPane.add(logo);

		raspiRevolutionaries.add(controlPane);
	}
}
