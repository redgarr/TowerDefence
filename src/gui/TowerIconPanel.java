package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.GameController;
import tiles.Tile;
import tower.SimpleTower;
import tower.Tower;

public class TowerIconPanel<T extends Tower>
	extends JButton
{

	private Tile tile;

	public TowerIconPanel(GameController controller, Tile tile, BufferedImage icon)
	{
		this.tile = tile;
		Dimension dimension = new Dimension(32, 32);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setBackground(Color.RED);
		setIcon(new ImageIcon(icon));
		setBorder(BorderFactory.createEmptyBorder());
		addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.setActiveTile(tile);
			}
		});
	}
	

	
	
}
