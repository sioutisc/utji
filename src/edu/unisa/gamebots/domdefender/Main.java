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
 
package edu.unisa.gamebots.domdefender;

import java.util.*;
import java.io.*;
import edu.unisa.gamebots.utjackinterface.*;
//import java.lang.Exception.*;

public class Main extends Thread
{
	DomDefender dfr;
	
	//stores references to the checkboxes for easy access
	public Main(String name, String server)
	{
		super();
		dfr = new DomDefender(name, UtConstants.TEAM_BLUE, server);
		dfr.connect();
		start();
	}
	
	
	public void run()
	{
		try{sleep(1000);}catch(InterruptedException e){System.out.println("Sleeping problem");}
		dfr.jumpAlways();
		dfr.exploreAndDefend(5,10);

                while(true) //check if the agent has finished and quit is yes
		{
			if(dfr.getFinished())
                            if(dfr.getMainGoalAchieved())
                                System.exit(0);
                            else
                                System.exit(1);
                        
			try{sleep(1000);}catch(InterruptedException e){System.out.println("Sleeping problem");}
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length < 2){
                    System.out.println("ERROR: Usage: java edu.unisa.gamebots.domdefender.Main agentName UTServer");
		} else{
			Main app = new Main(args[0],args[1]);
			try{app.join();}catch(InterruptedException e){System.out.println("Sleeping problem");}
		}
	}
}
