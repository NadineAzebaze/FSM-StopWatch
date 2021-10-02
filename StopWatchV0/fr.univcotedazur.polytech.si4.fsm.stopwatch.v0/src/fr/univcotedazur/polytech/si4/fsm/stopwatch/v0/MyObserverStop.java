package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer; 

public class MyObserverStop implements Observer<Void>{
	//attribut IHM
	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverStop(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Void value) {
		// TODO Auto-generated method stub
		System.out.println("this is a reaction to a stop event");
		theGui.doStop();
		
		
	}

}
