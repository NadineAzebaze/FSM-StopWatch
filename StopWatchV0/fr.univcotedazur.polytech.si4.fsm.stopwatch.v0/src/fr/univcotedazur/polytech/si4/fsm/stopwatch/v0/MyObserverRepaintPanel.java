package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import com.yakindu.core.rx.Observer;

public class MyObserverRepaintPanel implements Observer{
	//attribut IHM
		StopWatchGUI theGui;

		//constructeur pour l'IHM
		public MyObserverRepaintPanel(StopWatchGUI stopWatchGUI) {
			theGui = stopWatchGUI;
		}

		@Override
		public void next(Object value) {
			// TODO Auto-generated method stub
			theGui.repaintPanel();		
		}
		
}
