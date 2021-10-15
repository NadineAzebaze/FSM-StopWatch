package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
	 * my implementations
	 */
	enum State { STARTED, STOPPED,PAUSED,  RESUMED, RESET }
    enum Event {LeftButton, RightButton}

    private int nbrState = 5;
    private int nbrEvent = 2;
    private State currentState = State.RESET ;

    List<ArrayList<Method>> FSM = new ArrayList<ArrayList<Method>>(5);

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
	
	public void idle(){}

	protected void start() {
		System.out.println("etered");
		msTimer.start();
		updateTimeValue();
		leftButton.setText("stop");
	}
	protected void stop() {
		System.out.println("etered");
		msTimer.stop();
		updateTimeValue();
		leftButton.setText("start");
	}
    protected void pause(){
    	rightButton.setText("resume");
    }
    protected void reset(){
		mins=millis=secs=0;
		leftButton.setText("start");
	}
    protected void resume(){
    	rightButton.setText("pause");
    }
    
    
    public void fireReset(){
        reset();
        currentState = State.STARTED;
    }
    public void fireStartLeftButton(){
        start();
        currentState = State.STOPPED;
    }
    public void fireStartRightButton(){
        start();
        currentState = State.PAUSED;
    }
    public void fireStop(){
        stop();
        currentState = State.RESET;
    }
    public void firePauseLeftButton(){
        pause();
        currentState = State.STOPPED;
    }
    public void firePauseRightButton(){
        pause();
        currentState = State.RESUMED;
    }
    public void fireResumeLeftButton(){
        resume();
        currentState = State.STOPPED;
    }
    public void fireResumeRightButton(){
        resume();
        currentState = State.PAUSED;
    }
    
  //Launching
    void activation(Event newEvent){
       ArrayList<Method> myLine = FSM.get(currentState.ordinal());
       myLine.get(newEvent.ordinal());
    }
	
	/**
	 * construct the GUI and initialize the different value. Also initialize the {@link #msTimer}
	 * @param mn
	 * @param se
	 * @param ct
	 */
	public StopWatchGUI(int mn, int se, int ct) {
		/**
		 * my implementations
		 */
		
		List<ArrayList<Method>> FSM = new ArrayList<ArrayList<Method>>(5);
	        try {
	            //row0
	            ArrayList<Method> row0= new ArrayList<>(2);
	            row0.add( this.getClass().getMethod("fireStartLeftButton"));
	            row0.add(this.getClass().getMethod("fireStartRightButton"));
	            //row1
	            ArrayList<Method> row1= new ArrayList<>(2);
	            row1.add(this.getClass().getMethod("fireStop"));
	            row1.add(this.getClass().getMethod("idle"));
	            //row2
	            ArrayList<Method> row2= new ArrayList<>(2);
	            row2.add(this.getClass().getMethod("firePauseLeftButton"));
	            row2.add(this.getClass().getMethod("firePauseRightButton"));
	            //row3
	            ArrayList<Method> row3= new ArrayList<>(2);
	            row3.add(this.getClass().getMethod("fireResumeLeftButton"));
	            row3.add(this.getClass().getMethod("fireResumeRightButton"));
	            //row4
	            ArrayList<Method> row4= new ArrayList<>(2);
	            row4.add(this.getClass().getMethod("fireReset"));
	            row4.add(this.getClass().getMethod("idle"));

	            FSM.add(row0);
	            FSM.add(row1);
	            FSM.add(row2);
	            FSM.add(row3);
	            FSM.add(row4);
	            
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        }
	        
		mins = mn;
		secs = se;
		millis = ct;
		 System.out.println(FSM.size());
		State currentState = State.RESET;

		// graphics init and listener settings
		rootPanel = new JPanel();
		leftButton = new JButton("start");
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activation(Event.RightButton);
			}
		});

		rightButton = new JButton("pause");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activation(Event.LeftButton);
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
		StopWatchGUI watch = new StopWatchGUI(0, 0, 0);
		
	}
}
