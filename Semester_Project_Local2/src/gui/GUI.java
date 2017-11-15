package gui;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 * 
 * 
 * @author Frederick Javalera
 */
public class GUI
{
	// fields
	private JFrame frmWeatherStation;
	private RaspiCalendar calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI window = new GUI();
					window.frmWeatherStation.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// Registers all fonts needed and found in fonts package
		GraphicsEnvironment ge = null;
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try
		{
			ge.registerFont(
					Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("/fonts/digital-7Mono.ttf")));
			ge.registerFont(
					Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("/fonts/digital-7Italic.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("/fonts/FUTURISM.TTF")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("/fonts/NotoSans.ttf")));
		} catch (FontFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// Creates fonts for use
		Font digital7Italic = new Font("Digital-7 Italic", Font.TRUETYPE_FONT, 60);
		Font digital7Mono = new Font("Digital-7 Mono", Font.TRUETYPE_FONT, 99);
		Font futurism = new Font("Futurism", Font.TRUETYPE_FONT, 50);
		Font notoSans = new Font("Noto Sans", Font.TRUETYPE_FONT, 22);

		// Sets up GUI
		frmWeatherStation = new JFrame();
		frmWeatherStation.setTitle("Weather Station");
		frmWeatherStation.setIconImage(
				Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/images/weatherStationIcon.png")));
		frmWeatherStation.getContentPane().setBackground(new Color(0, 0, 0));
		frmWeatherStation.getContentPane().setLayout(null);
		Dimension screenSize = frmWeatherStation.getSize();
		frmWeatherStation.setSize(screenSize);

		JButton btnHistoricalData = new JButton("");
		btnHistoricalData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				calendar = new RaspiCalendar();
				calendar.main(null);
			}
		});
		btnHistoricalData.setToolTipText("Opens a calendar in which you can get historical data by date.");
		btnHistoricalData.setSelectedIcon(new ImageIcon(GUI.class.getResource("/images/historicalDataButton.png")));
		btnHistoricalData.setIcon(new ImageIcon(GUI.class.getResource("/images/historicalDataButton.png")));
		btnHistoricalData.setForeground(Color.BLACK);
		btnHistoricalData.setBackground(Color.BLACK);
		btnHistoricalData.setFont(new Font("notoSans", Font.PLAIN, 21));
		btnHistoricalData.setBounds(484, 416, 224, 70);
		btnHistoricalData.setBorderPainted(false);
		frmWeatherStation.getContentPane().add(btnHistoricalData);

		JLabel lblImage = new JLabel("");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBackground(Color.GREEN);
		lblImage.setBounds(587, 522, 135, 167);
		lblImage.setIcon(new ImageIcon(GUI.class.getResource("/images/piLogoGreen.png")));
		frmWeatherStation.getContentPane().add(lblImage);

		JLabel lblWind = new JLabel("WIND");
		lblWind.setFont(notoSans);
		lblWind.setForeground(new Color(0, 255, 0));
		lblWind.setBounds(22, 33, 115, 35);
		frmWeatherStation.getContentPane().add(lblWind);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(0, 255, 0));
		separator_2.setBounds(22, 21, 754, 2);
		frmWeatherStation.getContentPane().add(separator_2);

		JLabel label = new JLabel("000.0");
		label.setFont(digital7Mono);
		label.setForeground(new Color(0, 255, 0));
		label.setBounds(22, 62, 225, 98);
		frmWeatherStation.getContentPane().add(label);

		JLabel lblMph = new JLabel("MPH");
		lblMph.setFont(notoSans);
		lblMph.setHorizontalAlignment(SwingConstants.CENTER);
		lblMph.setForeground(Color.GREEN);
		lblMph.setBounds(96, 160, 88, 29);
		frmWeatherStation.getContentPane().add(lblMph);

		JLabel lblDate = new JLabel("DATE");
		lblDate.setFont(notoSans);
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setForeground(Color.GREEN);
		lblDate.setBounds(672, 33, 104, 35);
		frmWeatherStation.getContentPane().add(lblDate);

		JLabel lblTime = new JLabel("TIME");
		lblTime.setFont(notoSans);
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setForeground(Color.GREEN);
		lblTime.setBounds(672, 214, 104, 35);
		frmWeatherStation.getContentPane().add(lblTime);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.GREEN);
		separator_3.setBounds(22, 195, 282, 7);
		frmWeatherStation.getContentPane().add(separator_3);

		JLabel lblTemperature = new JLabel("TEMPERATURE");
		lblTemperature.setForeground(Color.GREEN);
		lblTemperature.setFont(notoSans);
		lblTemperature.setBounds(22, 214, 282, 35);
		frmWeatherStation.getContentPane().add(lblTemperature);

		JLabel label_1 = new JLabel("000");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(digital7Mono);
		label_1.setForeground(Color.GREEN);
		label_1.setBounds(22, 243, 191, 98);
		frmWeatherStation.getContentPane().add(label_1);

		JLabel lblNewLabel = new JLabel("°F");
		lblNewLabel.setFont(notoSans);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(68, 335, 43, 42);
		frmWeatherStation.getContentPane().add(lblNewLabel);

		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.GREEN);
		separator_4.setBounds(22, 375, 278, 7);
		frmWeatherStation.getContentPane().add(separator_4);

		JLabel lblIpsum = new JLabel("RAINFALL");
		lblIpsum.setForeground(Color.GREEN);
		lblIpsum.setFont(notoSans);
		lblIpsum.setBounds(22, 394, 191, 35);
		frmWeatherStation.getContentPane().add(lblIpsum);

		JLabel label_2 = new JLabel("00");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(digital7Mono);
		label_2.setForeground(Color.GREEN);
		label_2.setBounds(22, 423, 131, 98);
		frmWeatherStation.getContentPane().add(label_2);

		JLabel lblNewLabel_1 = new JLabel("CM");
		lblNewLabel_1.setForeground(Color.GREEN);
		lblNewLabel_1.setFont(notoSans);
		lblNewLabel_1.setBounds(53, 515, 53, 42);
		frmWeatherStation.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("00");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(digital7Mono);
		lblNewLabel_2.setForeground(Color.GREEN);
		lblNewLabel_2.setBounds(165, 423, 135, 98);
		frmWeatherStation.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("IN");
		lblNewLabel_3.setFont(notoSans);
		lblNewLabel_3.setForeground(Color.GREEN);
		lblNewLabel_3.setBounds(244, 515, 33, 42);
		frmWeatherStation.getContentPane().add(lblNewLabel_3);

		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.GREEN);
		separator_5.setBounds(22, 555, 278, 7);
		frmWeatherStation.getContentPane().add(separator_5);

		JLabel lblHumidity = new JLabel("HUMIDITY");
		lblHumidity.setFont(notoSans);
		lblHumidity.setForeground(Color.GREEN);
		lblHumidity.setBounds(22, 569, 202, 35);
		frmWeatherStation.getContentPane().add(lblHumidity);

		JLabel label_3 = new JLabel("%");
		label_3.setFont(notoSans);
		label_3.setForeground(Color.GREEN);
		label_3.setBounds(81, 697, 25, 42);
		frmWeatherStation.getContentPane().add(label_3);

		JLabel label_4 = new JLabel("000");
		label_4.setFont(digital7Mono);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setForeground(Color.GREEN);
		label_4.setBounds(22, 602, 191, 98);
		frmWeatherStation.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("00/00/00");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(digital7Mono);
		label_5.setForeground(Color.GREEN);
		label_5.setBounds(317, 62, 459, 98);
		frmWeatherStation.getContentPane().add(label_5);

		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.GREEN);
		separator_6.setBounds(418, 375, 358, 7);
		frmWeatherStation.getContentPane().add(separator_6);

		JLabel lblNewLabel_4 = new JLabel("00:00:00");
		lblNewLabel_4.setFont(digital7Mono);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setForeground(Color.GREEN);
		lblNewLabel_4.setBounds(336, 243, 440, 98);
		frmWeatherStation.getContentPane().add(lblNewLabel_4);

		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.GREEN);
		separator_7.setBounds(418, 195, 358, 7);
		frmWeatherStation.getContentPane().add(separator_7);

		JLabel lblRaspi = new JLabel("RasPi");
		lblRaspi.setFont(futurism);
		lblRaspi.setForeground(Color.GREEN);
		lblRaspi.setBounds(336, 599, 458, 116);
		frmWeatherStation.getContentPane().add(lblRaspi);

		JLabel lblRevolutionaries = new JLabel("Revolutionaries");
		lblRevolutionaries.setFont(digital7Italic);
		lblRevolutionaries.setForeground(Color.GREEN);
		lblRevolutionaries.setBounds(366, 649, 440, 107);
		frmWeatherStation.getContentPane().add(lblRevolutionaries);

		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(22, 767, 755, 2);
		frmWeatherStation.getContentPane().add(separator_8);

		frmWeatherStation.setBounds(0, 0, 825, 900);
		frmWeatherStation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWeatherStation.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		frmWeatherStation.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("notoSans", Font.PLAIN, 22));
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setIcon(new ImageIcon(GUI.class.getResource("/images/open_black_icon.png")));
		mntmOpen.setHorizontalAlignment(SwingConstants.LEFT);
		mntmOpen.setFont(new Font("notoSans", Font.PLAIN, 18));
		mnFile.add(mntmOpen);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(GUI.class.getResource("/images/save_black_icon.png")));
		mntmSave.setHorizontalAlignment(SwingConstants.LEFT);
		mntmSave.setFont(new Font("notoSans", Font.PLAIN, 18));
		mnFile.add(mntmSave);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		mntmExit.setIcon(new ImageIcon(GUI.class.getResource("/images/close_red_icon.png")));
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		mntmExit.setFont(new Font("notoSans", Font.PLAIN, 18));
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("notoSans", Font.PLAIN, 22));
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(GUI.class.getResource("/images/about_black_icon.png")));
		mntmAbout.setHorizontalAlignment(SwingConstants.LEFT);
		mntmAbout.setFont(new Font("notoSans", Font.PLAIN, 18));
		mnHelp.add(mntmAbout);
	}
}