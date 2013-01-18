package com.oro.legendoftest.level;

import java.util.ArrayList;
import java.util.Random;

import com.oro.legendoftest.IUsable;
import com.oro.legendoftest.entity.Hero;
import com.oro.legendoftest.view.HUD;

public enum LevelManager
{
	// Enum Singleton:  http://en.wikipedia.org/wiki/Singleton_pattern#The_Enum_way
	
	INSTANCE;
	
	// ***********************************
	// Member variables
	// ***********************************
	private ArrayList<Level> levels = new ArrayList<Level>();
	private int currentLevelIndex;
	private Level currentLevel;
	private Hero hero;
	
	public static Random random = new Random();

	
	// ***********************************
	// Constructor
	// ***********************************
	LevelManager()
	{
	}
	
	// ***********************************
	// Public Member Functions (methods)
	// ***********************************
	public void init(Hero hero)
	{
		this.hero = hero;
		
		// Generate first level.
		currentLevelIndex = 0;
		currentLevel = new Level(currentLevelIndex, hero);
		levels.add(currentLevel);
		currentLevel.enterForward();
	}
	
	
	public Level getCurrentLevel()
	{
		return currentLevel;
	}
	
	public boolean isAtStart()
	{
		return currentLevelIndex == 0;
	}
	
	public void goBack()
	{
		currentLevelIndex -= 1;
		currentLevel = levels.get(currentLevelIndex);
		currentLevel.enterBack();
		HUD.INSTANCE.label1.setText("Max/Current Level " + levels.size() + "/" + (currentLevelIndex + 1));
	}
	
	public void goForward()
	{
		currentLevelIndex += 1;
		if (currentLevelIndex == levels.size())
		{
			currentLevel = new Level(currentLevelIndex, hero);
			levels.add(currentLevel);
			System.out.println("Tunnel Value = " + currentLevel.tunnel_Value);
		}
		else
		{
			currentLevel = levels.get(currentLevelIndex);
		}
		
		currentLevel.enterForward();
		HUD.INSTANCE.label1.setText("Max/Current Level " + levels.size() + "/" + (currentLevelIndex + 1));
	}
	public void Use()
	{
		for (int x = (int)Math.floor(hero.getX()); x <= (int)Math.ceil(hero.getX()); x++)
		{
			for (int y = (int)Math.floor(hero.getY()); y <= (int)Math.ceil(hero.getY()); y++)
			{
				IUsable usable = currentLevel.getUsableAt(x, y);
				if(usable != null)
				{
					usable.use();
					return;
				}
			}
		}
	}
}
