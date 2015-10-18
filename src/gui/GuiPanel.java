package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;
import engine.GameController;
import events.TileChangeListener;
import events.TileSelectionChangedEvent;
import graphics.Sprite;
import input.InputKeyboardListener;
import tiles.Tile;
import tower.Tower;

public class GuiPanel 
	extends JPanel
{
	private GameController controller;
	private Game game;
	
	private TileChangeListener tileChangeListener;
	private Dimension size;
	private JPanel tileInfoPanel;
	
	private JLabel tileSpriteLabel;
	private JLabel tileText;
	private JPanel panel;
	private JLabel tileDamage;
	private JLabel tileAtkSpeed;
	private JLabel tileRange;
	private JLabel tileCoords;
	
	public GuiPanel(Dimension size, Game game, GameController gameController) 
	{
		this.size = size;
		this.game = game;
		this.controller = gameController;
		
		tileChangeListener = new TileChangeListener(game, controller)
		{
			@Override
			public void tileSelectionChangedEvent(TileSelectionChangedEvent e) {
				Tile tile = (Tile) e.getData();
				if(tile == null)
				{
					return;
				}
				
				Sprite sprite = tile.getSprite();
				BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
				int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
				
				for(int i=0; i < pixels.length; i++)
				{
					if(i<32)
					{
						continue;
					}
					if(sprite != null)
					{
						pixels[i] = sprite.pixels[i];
					}
				}
				tileSpriteLabel.setIcon(new ImageIcon(image.getScaledInstance(96, 96, BufferedImage.TYPE_INT_RGB)));
				tileText.setText(tile.getClass().getSimpleName());
				if(tile instanceof Tower)
				{
					tileAtkSpeed.setText("Atk Speed: " + Double.toString(((Tower) tile).getAttackSpeed()));
					tileDamage.setText("Damage: " + Double.toString(((Tower) tile).getDamage()));
					tileRange.setText("Range:" + Double.toString(((Tower)tile).getRange()));
					tileCoords.setText("(" + (int)tile.getCoordinates().getX() + ", " + (int)tile.getCoordinates().getY() + ")");
				}
				else
				{
					tileAtkSpeed.setText("");
					tileDamage.setText("");
					tileRange.setText("");
					tileCoords.setText("(" + (int)tile.getCoordinates().getX() + ", " + (int)tile.getCoordinates().getY() + ")");
				}
				
			}
		};
		
		controller.registerTileChangeListener(tileChangeListener);
		
		initComponents();
		setPreferredSize(size);
		setMinimumSize(size);
		setSize(size);
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	private void initComponents() 
	{
		Dimension buttonSize = new Dimension(120, 40);
		JButton spawnEnemyB = new JButton("Spawn enemy");
		spawnEnemyB.setPreferredSize(buttonSize);
		spawnEnemyB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.generateAIActor();
			}
		});
		
		JButton makeFloorLineB = new JButton("Make floor line");
		makeFloorLineB.setPreferredSize(buttonSize);
		makeFloorLineB.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.makeFloorLine();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		buttonPanel.add(spawnEnemyB);
		buttonPanel.add(makeFloorLineB);
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setPreferredSize(size);
		panel.setMinimumSize(size);
		panel.setSize(size);
		panel.setBackground(Color.BLACK);
		
		tileSpriteLabel = new JLabel();
		JPanel tilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tilePanel.setBackground(Color.black);
		
		Dimension tilePanelSize = new Dimension(400, (int)size.getHeight());
		tileInfoPanel = new JPanel();
		tileInfoPanel.setLayout(new BoxLayout(tileInfoPanel, BoxLayout.Y_AXIS));
		tileInfoPanel.setBackground(Color.BLACK);
		tileInfoPanel.setSize(tilePanelSize);
		tileInfoPanel.setMinimumSize(tilePanelSize);
		tileInfoPanel.setMaximumSize(tilePanelSize);
		tileInfoPanel.setPreferredSize(tilePanelSize);
		
		Font font = new Font("HELVETICA", Font.PLAIN, 14);
		tileText = new JLabel();
		tileText.setForeground(Color.WHITE);
		tileText.setFont(font);
		tileDamage = new JLabel();
		tileDamage.setForeground(Color.WHITE);
		tileDamage.setFont(font);
		tileAtkSpeed = new JLabel();
		tileAtkSpeed.setForeground(Color.WHITE);
		tileAtkSpeed.setFont(font);
		tileRange = new JLabel();
		tileRange.setForeground(Color.WHITE);
		tileRange.setFont(font);
		tileCoords = new JLabel();
		tileCoords.setForeground(Color.WHITE);
		tileCoords.setFont(font);
		
		tileInfoPanel.add(tileSpriteLabel);
		tileInfoPanel.add(tileText);
		tileInfoPanel.add(tileDamage);
		tileInfoPanel.add(tileAtkSpeed);
		tileInfoPanel.add(tileRange);
		tileInfoPanel.add(tileCoords);
		
		tilePanel.add(tileInfoPanel);
		
		panel.add(buttonPanel);
		panel.add(Box.createHorizontalGlue());
		panel.add(tilePanel);
		panel.setBorder(BorderFactory.createEmptyBorder());
		add(panel);
	}
}
