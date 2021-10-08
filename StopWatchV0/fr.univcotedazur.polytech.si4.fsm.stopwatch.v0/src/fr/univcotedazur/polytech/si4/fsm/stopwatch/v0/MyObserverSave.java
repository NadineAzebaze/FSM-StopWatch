package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverSave implements Observer {
	//attribut IHM
	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverSave(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Object value) {
		// TODO Auto-generated method stub
		System.out.println("this is a reaction to a save event");
		theGui.saveTime();
		
	}


}
