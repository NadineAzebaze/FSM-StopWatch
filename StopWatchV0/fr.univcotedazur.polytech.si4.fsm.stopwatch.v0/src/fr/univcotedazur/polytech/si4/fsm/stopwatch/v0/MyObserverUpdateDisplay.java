package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverUpdateDisplay  implements Observer {
	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverUpdateDisplay(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Object value) {
		// TODO Auto-generated method stub
		theGui.updateTimeValue();
		System.out.println("this is a reaction to a time event");
		
	}

	
}
