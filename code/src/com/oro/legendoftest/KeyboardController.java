package com.oro.legendoftest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import com.oro.legendoftest.entity.Hero;
import com.oro.legendoftest.level.LevelManager;

public class KeyboardController extends KeyAdapter
{
	BitSet northSet = new BitSet(8);
	BitSet eastSet = new BitSet(8);
	BitSet southSet = new BitSet(8);
	BitSet westSet = new BitSet(8);
	
	private Hero hero;
	
	BitSet moveKeys = new BitSet(8);	
	
	public KeyboardController(Hero hero)
	{
		this.hero = hero;
		
		northSet.set(Direction.NW.ordinal() - 1);
		northSet.set(Direction.N.ordinal() - 1);
		northSet.set(Direction.NE.ordinal() - 1);
		
		eastSet.set(Direction.NE.ordinal() - 1);
		eastSet.set(Direction.E.ordinal() - 1);
		eastSet.set(Direction.SE.ordinal() - 1);
	
		southSet.set(Direction.SE.ordinal() - 1);
		southSet.set(Direction.S.ordinal() - 1);
		southSet.set(Direction.SW.ordinal() - 1);

		westSet.set(Direction.SW.ordinal() - 1);
		westSet.set(Direction.W.ordinal() - 1);
		westSet.set(Direction.NW.ordinal() - 1);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		boolean moveKey = false;
		switch(evt.getKeyCode())
		{
		case KeyEvent.VK_RIGHT: moveKeys.set(Direction.E.ordinal() - 1);
								moveKey = true;
								break;
		case KeyEvent.VK_LEFT:  moveKeys.set(Direction.W.ordinal() - 1);
							    moveKey = true;
							    break;
		case KeyEvent.VK_UP:	moveKeys.set(Direction.N.ordinal() - 1);
								moveKey = true;
								break;
		case KeyEvent.VK_DOWN:	moveKeys.set(Direction.S.ordinal() - 1);
								moveKey = true;
								break;
								
		default:				moveKeys.isEmpty();
								break;
		}
		if (moveKey)
		{
			hero.setMoving(getMoveDirection());
		}
	}

	@Override
	public void keyReleased(KeyEvent evt)
	{
		boolean moveKey = false;
		switch(evt.getKeyCode())
		{
		case KeyEvent.VK_RIGHT: moveKeys.clear(Direction.E.ordinal() - 1);
								moveKey = true;
								break;
		case KeyEvent.VK_LEFT:  moveKeys.clear(Direction.W.ordinal() - 1);
							    moveKey = true;
							    break;
		case KeyEvent.VK_UP:	moveKeys.clear(Direction.N.ordinal() - 1);
								moveKey = true;
								break;
		case KeyEvent.VK_DOWN:	moveKeys.clear(Direction.S.ordinal() - 1);
								moveKey = true;
								break;
		case KeyEvent.VK_SPACE:	LevelManager.INSTANCE.Use();
								break;
								
		default:				moveKeys.isEmpty();
								break;
		}
		/*if (evt.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveKeys.clear(Direction.E.ordinal() - 1);
			moveKey = true;
		}
		if (evt.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveKeys.clear(Direction.W.ordinal() - 1);
			moveKey = true;
		}
		if (evt.getKeyCode() == KeyEvent.VK_UP)
		{
			moveKeys.clear(Direction.N.ordinal() - 1);
			moveKey = true;
		}

		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moveKeys.clear(Direction.S.ordinal() - 1);
			moveKey = true;
		}*/		
		if (moveKey)
		{
			hero.setMoving(getMoveDirection());
		}
	}
	
	private Direction getMoveDirection()
	{
		if (moveKeys.isEmpty())
		{
			return Direction.NONE;
		}
		
		// This crazy logic will cancel out opposing directions, and combine keys to do
		// diagonal movement.
		
		boolean north = moveKeys.intersects(northSet) && !moveKeys.intersects(southSet);
		boolean east = moveKeys.intersects(eastSet) && !moveKeys.intersects(westSet);
		boolean south = moveKeys.intersects(southSet) && !moveKeys.intersects(northSet);
		boolean west = moveKeys.intersects(westSet) && !moveKeys.intersects(eastSet);
		
		if (north)
		{
			if (west)
			{
				return Direction.NW;
			}
			else if (east)
			{
				return Direction.NE;
			}
			else
			{
				return Direction.N;
			}
		}
		else if (south)
		{
			if (west)
			{
				return Direction.SW;
			}
			else if (east)
			{
				return Direction.SE;
			}
			else
			{
				return Direction.S;
			}
		}
		else if (east)
		{
			return Direction.E;
		}
		else if (west)
		{
			return Direction.W;
		}
		return Direction.NONE;
	}
}
