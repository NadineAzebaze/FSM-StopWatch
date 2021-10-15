package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * Simple old style GUI for the stopWatch used as a support for the {@see <a href="http://www.i3s.unice.fr/~deantoni/teaching_resources/SI4/FSM/">Finite State Machine course</a>}
 * @author Julien Deantoni, universite cote d'azur
 *
 */
public class StopWatchGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8682173885223592966L;

	protected int millis, secs, mins;
	protected JButton leftButton, rightButton;
	protected JPanel rootPanel;
	protected JLabel timeValue;
	protected Timer msTimer;

	
	/**
	 * add {@code nbMillisec} millisecondes to the current time encoded into mins, secs, millis.
	 * @param nbMillisec
	 */
	protected void count(int nbMillisec) {
		millis += nbMillisec;
		if (millis >= 1000) {
			secs++;
			millis = 1000 - millis;
		}
		if (secs >= 60) {
			mins++;
			secs = 60 - secs;
		}
	}

	/**
	 * construct a string that represents the current time and set the {@link #timeValue} label accordingly + repaint
	 */
	protected void updateTimeValue() {
		timeValue.setText((((mins / 10) == 0) ? "0" : "") + mins + ":" + (((secs / 10) == 0) ? "0" : "") + secs + ":"
				+ (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis);
		repaint();
	}

	protected void doStart() {
		msTimer.start();
		updateTimeValue();
		leftButton.setText("stop");
	}

	protected void doStop() {
		msTimer.stop();
		updateTimeValue();
		leftButton.setText("start");
	}

	
	/**
	 * construct the GUI and initialize the different value. Also initialize the {@link #msTimer}
	 * @param mn
	 * @param se
	 * @param ct
	 */
	public StopWatchGUI(int mn, int se, int ct) {
		mins = mn;
		secs = se;
		millis = ct;

		// graphics init and listener settings
		rootPanel = new JPanel();
		leftButton = new JButton("start");
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doStart();
			}
		});

		rightButton = new JButton("pause");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doStop();
			}
		});

		timeValue = new JLabel();
		timeValue.setFont(new Font("Courier", Font.BOLD, 25));
		updateTimeValue();
		rootPanel.add(timeValue);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(leftButton);
		buttonPanel.add(rightButton);
		rootPanel.add(buttonPanel);
		this.add(rootPanel);

		// init a msTimer which is ready to do an action every 7 ms
		ActionListener doCountEvery7 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				count(7);
			}
		};
		msTimer = new Timer(7, doCountEvery7);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(rootPanel);
		setSize(200, 110);
		setResizable(true);
		setTitle("stopwatch");
		setVisible(true);

	}

	public static void main(String args[]) {
		new StopWatchGUI(0, 0, 0);
	}
}
