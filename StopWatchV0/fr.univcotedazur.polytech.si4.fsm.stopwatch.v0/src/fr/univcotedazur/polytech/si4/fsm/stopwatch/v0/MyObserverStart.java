package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverStart implements Observer<Void> {
	//attribut IHM
	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverStart(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Void value) {
		System.out.println("this is a reaction to a start event");
		theGui.doStart();

		
	}

}
