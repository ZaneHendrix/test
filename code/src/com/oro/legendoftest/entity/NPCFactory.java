package com.oro.legendoftest.entity;

import java.util.Random;

//import org.slf4j.Logger;

public enum NPCFactory
{	
	INSTANCE;
	Random random = new Random();	
	
	//****************************
	//Constructor
	//****************************
	private NPCFactory()
	{
	 
	}
	
	public Monster generateMonster()
	{
		Monster newMonster = null;
		int monsterSelector = random.nextInt(2);
		String monsterType;
		if(monsterSelector == 0)
		{
		 	monsterType = "Eye";
		}
		else
		{
			monsterType = "Worm";
		}
		try
		{
			Class<?> monsterClass  = Class.forName("com.oro.legendoftest.entity." + monsterType);
			newMonster = (Monster)monsterClass.newInstance();
		}
		catch (Exception e)
		{
			System.out.println("NPC " + monsterType + " cannot be instantiated: " + e.getMessage());
		 	return null;
		}
		return newMonster;
	}
}
