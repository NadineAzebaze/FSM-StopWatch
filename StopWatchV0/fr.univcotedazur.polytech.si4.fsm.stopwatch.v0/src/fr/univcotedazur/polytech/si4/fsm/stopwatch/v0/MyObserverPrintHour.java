package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverPrintHour implements Observer{

	//attribut IHM
	StopWatchGUI theGui;

	//constructeur pour l'IHM
	public MyObserverPrintHour(StopWatchGUI stopWatchGUI) {
		theGui = stopWatchGUI;
	}

	@Override
	public void next(Object value) {
		// TODO Auto-generated method stub
		System.out.println("this is a reaction to a print value");
		theGui.doPrintHour();
		
	}
}
