/* ********************************************************************* *
 *                                                                       *
 *   =============================================================       *
 *   Copyright 2002-2010,                                                *
 *   Christos Sioutis <christos.sioutis@gmail.com>                       *
 *   =============================================================       *
 *   This software was developed during my PhD studies at:               *
 *                                                                       *
 *   Knowledge Based Intelligent Engineering Systems Centre (KES)        *
 *   School of Electrical and Information Engineering                    *
 *   University of South Australia                                       *
 *   =============================================================       *
 *                                                                       *
 *   This file is part of utji.                                          *
 *                                                                       *
 *   utji is free software: you can redistribute it and/or               *
 *   modify it under the terms of the GNU Lesser General Public License  *
 *   as published by the Free Software Foundation, either version 3 of   *
 *   the License, or (at your option) any later version.                 *
 *                                                                       *
 *   utji is distributed in the hope that it will be useful,             *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the       *
 *   GNU Lesser General Public License for more details.                 *
 *                                                                       *
 *   You should have received a copy of the GNU Lesser General Public    *
 *   License along with utji.                                            *
 *   If not, see <http://www.gnu.org/licenses/>.                         *
 *                                                                       *
 * ********************************************************************* */

package edu.unisa.gamebots.utjackinterface;

import edu.unisa.gamebots.utjackinterface.controller.*;
import java.util.*;
import java.io.*;
import aos.jack.jak.agent.Agent;

public class UtJackBot extends Agent implements UtCommands
{	
	public UtJackInterface ut;
	public String utServer;
	public String utName;
	public int utTeam;
        public boolean finished;
        public boolean mainGoalAchieved;
	
	public UtJackBot(String name, int team)
	{
		super(name);		
		utName = name;
		utTeam = team;
                finished = false;
                mainGoalAchieved = false;
	}
	
	public void connect(UtJackInterface utJackInterface, String server)
	{
		ut = utJackInterface;
		utServer = server;
		if(!ut.connectToServer(utServer, utName, utTeam))
			System.exit(1);
	}
	
	public void setWalk(boolean walk)
	{
		ut.setWalk(walk);
	}
	
	public void stop()
	{
		ut.stop();
	}
	
	public void jump()
	{
		ut.jump();
	}
	
	public void runTo(String target)
	{
		ut.runTo(target);
	}
	
	public void runTo(UtCoordinate location)
	{
		ut.runTo(location);
	}
	
	public void strafe(UtCoordinate location, String target)
	{
		ut.strafe(location, target);
	}

	public void turnTo(UtCoordinate position)
	{
		ut.turnTo(position);
	}
	
	public void turnToLoc(UtCoordinate position)
	{
		ut.turnToLoc(position);
	}
		
	public void rotate(double amount)
	{
		ut.rotate(amount);
	}

	public void shoot()
	{
		ut.shoot();
	}
	
	public void shoot(UtCoordinate location, String target, boolean alt)
	{
		ut.shoot(location, target, alt);
	}

	public void shoot(String target, boolean alt)
	{
		ut.shoot(target, alt);
	}

	public void changeWeapon(String id)
	{
		ut.changeWeapon(id);
	}
	
	public void stopShoot()
	{
		ut.stopShoot();
	}
	
	public void checkReach(String id, UtCoordinate from)
	{
		ut.checkReach(id, from);
	}

	public void checkReach(String target, String id)
	{
		ut.checkReach(id, target);
	}
	
	public void getPath(UtCoordinate location, String id)
	{
		ut.getPath(location, id);
	}
	
	public void say(String msg, boolean global)
	{
		ut.say(msg, global);
	}	
        
        public boolean getFinished()
        {
            return finished;
        }
        
        public boolean getMainGoalAchieved()
        {
            return mainGoalAchieved;
        }
        
        
        public void setFinishedTrue(boolean mainGoalAchieved)
        {
            finished = true;
            this.mainGoalAchieved = mainGoalAchieved;
            finish();    
            ut.disconnect();
        }
        
}
