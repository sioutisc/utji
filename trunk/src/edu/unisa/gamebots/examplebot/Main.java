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
 
package edu.unisa.gamebots.examplebot;

import java.util.*;
import java.io.*;
import edu.tamu.gamebots.humanbot.*;
import edu.isi.gamebots.client.*;
import edu.unisa.gamebots.utjackinterface.*;
//import java.lang.Exception.*;

public class Main extends Thread
{
	ExampleBot eb;
	
	//stores references to the checkboxes for easy access
	public Main(String name, String server)
	{
		super();
		eb = new ExampleBot(name, UtConstants.TEAM_BLUE, server);
		start();
	}
	
	
	public void run()
	{
		while(true)
		{
			eb.performJump();
			try{sleep(5000);}catch(InterruptedException e){System.out.println("Sleeping problem");}
		}
	}
	
	public static void main(String[] args)
	{
		Main app;
		if(args.length < 2){
			System.out.println("Error: Usage: java edu.unisa.gamebots.examplebot.Main agentName server");
		}
		else{
			app = new Main(args[0],args[1]);
			try{app.join();}catch(InterruptedException e){System.out.println("Sleeping problem");}		
		}
		
	}
}
