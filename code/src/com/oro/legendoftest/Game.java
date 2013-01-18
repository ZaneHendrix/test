package com.oro.legendoftest;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.malkyne.game2d.SpriteManager;

import com.oro.legendoftest.entity.Hero;
import com.oro.legendoftest.level.Level;
import com.oro.legendoftest.level.LevelChunk;
import com.oro.legendoftest.level.LevelManager;
import com.oro.legendoftest.view.LevelSceneView;
import com.oro.legendoftest.view.HUD;

public class Game extends JFrame
{
	// *****************************
	// Constants
	// *****************************
	private static final long serialVersionUID = 0L;
	
	// ***********************************
	// Constructor
	// ***********************************
	
	public Game()
	{
		final HUD hud = HUD.INSTANCE;
		//For testing exact map changes
		//Level.random.setSeed(31415);
		final Hero hero = Hero.INSTANCE();
		final LevelManager levelMgr = LevelManager.INSTANCE;
		levelMgr.init(hero);
		
		// Make an empty panel.
		JPanel gamePanel = new JPanel();

		// Create our scene view for the game.
		final LevelSceneView sceneView = new LevelSceneView(hero);
		
		
		// Size the scene view to fit our chunk size.
		sceneView.setPreferredSize(new Dimension(
				LevelSceneView.PIXELS_PER_UNIT * LevelChunk.WIDTH_IN_TILES, 
				LevelSceneView.PIXELS_PER_UNIT * LevelChunk.HEIGHT_IN_TILES));
		
		//Display the HUD
		hud.add(this, hud.label1, BorderLayout.PAGE_START);
		
		// Add our keyboard input controller to the scene view.
		KeyboardController keyboard = new KeyboardController(hero);
		addKeyListener(keyboard);
		sceneView.addKeyListener(keyboard);
		
		// Mash everything together.
		gamePanel.add(sceneView);
		add(gamePanel);
		pack();
		
		// Handle close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Show the game.
		setVisible(true);
		
		// Initialize the back buffer.
		sceneView.initBufferStrategy();
		
		// Start render loop.
		Timer renderTimer = new Timer();
		TimerTask renderTask = new TimerTask()
		{
			long lastTime = System.currentTimeMillis();
			@Override
			public void run()
			{
				long currentTime = System.currentTimeMillis();
				Level currentLevel = levelMgr.getCurrentLevel();
				currentLevel.updateMovement((int)(currentTime - lastTime));		
				sceneView.render();
				lastTime = currentTime;
			}
		};
		
	
		renderTimer.schedule(renderTask, 0, 33); // About 30 fps.
		
		// Start simulation loop.
		// Start render loop.
		Timer simTimer = new Timer();
		TimerTask simTask = new TimerTask()
		{
			long lastTime = System.currentTimeMillis();
			@Override
			public void run()
			{
				long currentTime = System.currentTimeMillis();
				levelMgr.getCurrentLevel().updateSimulation((int)(currentTime - lastTime));
				lastTime = currentTime;
			}
		};

		simTimer.schedule(simTask, 0, 1000); // Update simulation about 1 times per second.
	}
	// ***********************************
	// Main
	// ***********************************	
	public static void main(String[] args)
	{
		SpriteManager.INSTANCE.setResourcePath("/resources");
		SpriteManager.INSTANCE.loadTextureAtlas("sprites.xml");
		new Game();
	}
	
}
