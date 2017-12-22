package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ControleurMap implements MouseListener {
	
	private int i;
	private int j;
	private PannelMap pannelMap;
	private ScreenMap screen;
	
	public ControleurMap(int i,int j, PannelMap pannelMap, ScreenMap screenMap) {
		this.i = i;
		this.j = j;
		this.pannelMap = pannelMap;
		this.screen = screenMap;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Territoire : " + this.screen.getGame().getTableTerritory()[this.i][this.j].toString());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.pannelMap.getListlabel()[this.i][this.j].setBackground(Color.RED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.pannelMap.getListlabel()[this.i][this.j].setBackground(Color.GRAY);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



}
