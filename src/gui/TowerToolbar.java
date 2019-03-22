package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import engine.GameController;
import graphics.Sprite;
import tiles.*;
import tower.SimpleTower;
import tower.Tower;

public class TowerToolbar 
	extends JPanel
{

	private GameController controller;

	public TowerToolbar(GameController controller) 
	{
		this.controller = controller;
		Dimension dimension = new Dimension(32*8, 32);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setVisible(true);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEmptyBorder());
		setBackground(Color.BLACK);
		initComponents();
	}

	private void initComponents() 
	{
		Tower simpleTower = new SimpleTower(0, 0, null);
		Tile floorTile = new FloorTile(0, 0);
		Tile rockTile = new RockTile(0, 0);
		Tile deathTile = new DeathTile(0, 0);
		Tile spawnTile = new SpawnTile(0, 0);

		add(new TowerIconPanel(controller, simpleTower, simpleTower.getSpriteAsImage()));
		add(new TowerIconPanel(controller, floorTile, floorTile.getSpriteAsImage()));
		add(new TowerIconPanel(controller, rockTile, Sprite.rocks_mid.getSpriteAsImage()));
		add(new TowerIconPanel(controller, deathTile, Sprite.actor_friendly_1.getSpriteAsImage()));
		add(new TowerIconPanel(controller, spawnTile, Sprite.actor_enemy_1.getSpriteAsImage()));
	}
	
}
