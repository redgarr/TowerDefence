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
import events.CurrencyChangeEvent;
import events.CurrencyListener;
import events.TileChangeListener;
import events.TileSelectionChangedEvent;
import graphics.Sprite;
import input.InputKeyboardListener;
import net.miginfocom.swing.MigLayout;
import tiles.Tile;
import tower.Tower;

public class GuiPanel 
	extends JPanel
{
	private GameController controller;
	private Game game;
	
	private TileChangeListener tileChangeListener;
	private CurrencyListener currencyListener;
	private Dimension size;
	private JPanel tileInfoPanel;
	
	private JLabel tileSpriteLabel;
	private JLabel tileText;
	private JPanel panel;
	private JLabel tileDamage;
	private JLabel tileAtkSpeed;
	private JLabel tileRange;
	private JLabel tileCoords;
	private JLabel currentCurrencyLabel;
	
	public GuiPanel(Dimension size, Game game, GameController gameController) 
	{
		this.size = size;
		this.game = game;
		this.controller = gameController;
		initComponents();
		
		tileChangeListener = new TileChangeListener(game, controller)
		{
			@Override
			public void tileSelectionChangedEvent(TileSelectionChangedEvent e) {
				Tile tile = (Tile) e.getData();
				if(tile == null)
				{
					return;
				}
				
				BufferedImage image = tile.getSpriteAsImage();
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
		
		currencyListener = new CurrencyListener(game, controller)
		{
				@Override
				public void currencyChangeEvent(CurrencyChangeEvent e) {
					currentCurrencyLabel.setText(e.getData().toString());
				}
		};
		
		controller.registerTileChangeListener(tileChangeListener);
		controller.registerCurrencyListener(currencyListener);
		
		setPreferredSize(size);
		setMinimumSize(size);
		setSize(size);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.add(spawnEnemyB);
		buttonPanel.add(makeFloorLineB);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new MigLayout(	"",
										"[]40[][]",//columns
										"[][]" //rows
		));
		
		tileSpriteLabel = new JLabel();
		JPanel tilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tilePanel.setBackground(Color.BLACK);
		tileInfoPanel = new JPanel();
		tileInfoPanel.setBorder(BorderFactory.createEmptyBorder());
		tileInfoPanel.setLayout(new MigLayout("", "[grow]", "[13]0[13!]0[13!]0[13!]0[13!]0[13!]"));
		tileInfoPanel.setBackground(Color.BLACK);
		
		Font font = new Font("HELVETICA", Font.PLAIN, 12);
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
		
		tileInfoPanel.add(tileSpriteLabel, "wrap");
		tileInfoPanel.add(tileText, "wrap");
		tileInfoPanel.add(tileCoords, "wrap");
		tileInfoPanel.add(tileDamage, "wrap");
		tileInfoPanel.add(tileAtkSpeed, "wrap");
		tileInfoPanel.add(tileRange, "wrap");
		
		tilePanel.add(tileInfoPanel);
		
		TowerToolbar towerToolbar = new TowerToolbar(controller);
		
		JPanel currencyPanel = new JPanel(new MigLayout("","[]","50[top][]"));
		currencyPanel.setBackground(Color.black);
		
		JLabel currencyLabel = new JLabel("Currency: ");
		currencyLabel.setForeground(Color.WHITE);
		currentCurrencyLabel = new JLabel("0");
		currentCurrencyLabel.setForeground(Color.WHITE);
		
		currencyPanel.add(currencyLabel);
		currencyPanel.add(currentCurrencyLabel, "wrap");
		
		panel.add(towerToolbar, "cell 0 0");
		panel.add(buttonPanel, "cell 0 1");
		panel.add(tilePanel, "cell 1 0 1 2");
		panel.add(currencyPanel, "cell 2 0 1 2, dock east");
		
		
		add(panel);
		
	}
}
