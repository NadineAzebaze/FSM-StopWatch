package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverCount implements Observer {

	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverCount(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Object value) {
		// TODO Auto-generated method stub
		theGui.count();
	}
}
