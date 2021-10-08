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

import com.yakindu.core.TimerService;

import fr.unice.polytech.si4.StopWatchStateMachine;




/**
 * Simple old style GUI for the stopWatch used as a support for the {@see <a href="http://www.i3s.unice.fr/~deantoni/teaching_resources/SI4/FSM/">Finite State Machine course</a>}
 * @author Julien Deantoni, universite cote d'azur
 *
 */
public class StopWatchGUI extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8682173885223592966L;

	protected int millis, secs, mins, counter;
	protected JButton leftButton, rightButton, modeButton, saveButton;
	protected JPanel rootPanel;
	protected JLabel timeValue, modeValue, saveValue, label1, label2, label3, label4, label5;
	protected Timer msTimer;
	protected StopWatchStateMachine theFSM;
	protected JLabel[] tabLabel= {label1, label2, label3, label4, label5};
	
	
	

	
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
	
	protected void count() {
		// TODO Auto-generated method stub
		count(7);
	}
	
	protected void updateDisplay() {
		updateTimeValue();
	}
	

	protected void saveTime() {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++) {
			counter++;
			if(tabLabel[i].getText().isBlank()) {
				tabLabel[i].setText(timeValue.getText());
				rootPanel.add(tabLabel[i]);
				return;
			}
		}
	}

	protected void resetLabels() {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++) {
			tabLabel[i] = new JLabel();
			rootPanel.add(tabLabel[i]);
		}
	
		
	}

	protected void doPrintDate() {
		modeButton.setText("time");
		modeValue.setText(java.time.LocalDate.now().toString());
	}

	protected void doPrintHour() {
		modeButton.setText("date");
		modeValue.setText(java.time.LocalTime.now().toString());
	}
	
	protected void doStart() {
		//msTimer.start();
		//updateTimeValue();
		leftButton.setText("stop");
	}

	protected void doStop() {
		//msTimer.stop();
		//updateTimeValue();
		leftButton.setText("reset");
	}

	protected void doPause() {
		//msTimer.start();
		//updateTimeValue();
		rightButton.setText("resume");
	}

	protected void doResume() {
		//msTimer.stop();
		//updateTimeValue();
		rightButton.setText("pause");
	}
	
	protected void doReset() {
		//msTimer.start();
		//updateTimeValue();
		mins=millis=secs=0;
		leftButton.setText("start");
	}
	
	public void repaintPanel() {
		// TODO Auto-generated method stub
		timeValue.setVisible(true);
		modeValue.setVisible(false);
		rootPanel.repaint();
		
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
		counter = 0;
		theFSM = new StopWatchStateMachine(); 

		TimerService timer = new TimerService();
		theFSM.setTimerService(timer);
		
		theFSM.getDoStart().subscribe(new MyObserverStart(this));
		theFSM.getDoStop().subscribe(new MyObserverStop(this));
		theFSM.getDoPause().subscribe(new MyObserverPause(this));
		theFSM.getDoResume().subscribe(new MyObserverResume(this));
		theFSM.getDoReset().subscribe(new MyObserverReset(this));
		theFSM.getUpdateDisplay().subscribe(new MyObserverUpdateDisplay(this));
		theFSM.getPrintDate().subscribe(new MyObserverPrintDate(this));
		theFSM.getPrintHour().subscribe(new  MyObserverPrintHour(this));
		theFSM.getCount().subscribe(new MyObserverCount(this));
		theFSM.getSaveTime().subscribe(new MyObserverSave(this));
		theFSM.getRepaintPanel().subscribe(new MyObserverRepaintPanel(this));
		
		
		theFSM.enter();


		// graphics init and listener settings
		rootPanel = new JPanel();
		leftButton = new JButton("start");
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseLeftButton();
				timeValue.setVisible(true);
				modeValue.setVisible(false);
				rootPanel.repaint();

			}
		});

		rightButton = new JButton("pause");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseRigthButton();
				timeValue.setVisible(true);
				modeValue.setVisible(false);
				rootPanel.repaint();
				
				
			}
		});
		
		modeButton = new JButton("time");
		modeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseModeButton();
				timeValue.setVisible(false);
				modeValue.setVisible(true);
				rootPanel.repaint();
			}
		});
		
		saveButton = new JButton("save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseSaveButton();
			}
		});

		timeValue = new JLabel();
		timeValue.setFont(new Font("Courier", Font.BOLD, 25));
		updateTimeValue();
		rootPanel.add(timeValue);
		
		modeValue = new JLabel();
		modeValue.setFont(new Font("Courier", Font.BOLD, 25));
		updateTimeValue();
		rootPanel.add(modeValue);
		
		//adding Labels to the Panel
		for(int i=0; i<5; i++) {
			tabLabel[i] = new JLabel();
			tabLabel[i].setFont(new Font("Courier", Font.BOLD, 25));
			updateTimeValue();
			rootPanel.add(tabLabel[i]);
		}
		

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(leftButton);
		buttonPanel.add(rightButton);
		buttonPanel.add(modeButton);
		buttonPanel.add(saveButton);
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
		setSize(400, 250);
		setResizable(true);
		setTitle("stopwatch");
		setVisible(true);

	}

	public static void main(String args[]) {
		new StopWatchGUI(0, 0, 0);
	}

	


}
