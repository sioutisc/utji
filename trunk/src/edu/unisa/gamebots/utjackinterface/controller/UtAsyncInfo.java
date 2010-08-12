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

package edu.unisa.gamebots.utjackinterface.controller;

import edu.unisa.gamebots.utjackinterface.UtConstants;

import java.util.*;
import edu.isi.gamebots.client.*;

/** Holds information from any async message recieved from the gamebots server.
 * 
 * @author Christos Sioutis
 */
public class UtAsyncInfo
{

	//each type of async message has different attributes. 
	//we use one object to parse all of the async messages.
	//it contains variables for all possible attributes.
	//the variables will be meaninful depending on what message it holds. 
	
	//here are the types of async messages and the variables they use
	/* *******************************************************************************
	NFO = gameType, level, timeLimit, fragLimit, goalTeamScore, maxTeams, maxTeamSize 
	AIN = id, strClass
	VMS = strString
	VMT = strString
	VMG = sender, type, id
	ZCF = id
	ZCH = id
	ZCB = id
	CWP = id, strClass
	WAL = id, normal, location
	FAL = fell, location
	BMP = id, location
	HRP = player
	HRN = source
	SEE = utPlayer
	PRJ = time, direction
	KIL = id, killer, damageType
	DIE = killer, damageType
	DAM = damage, damageType
	HIT = id, damage, damageType
	PTH = path
	RCH = id, reachable, from
	FIN = no attributes
	******************************************************************************** */

	/** The original message recieved in String format
	 */
	public Message msg;
	/** The type of message recieved
	 */
	public String msgType;
	//variables used for parameters, initialise with null values
	/** The type of game being played
	 */
	public String gameType = null;
	/** The name of the level being played
	 */
	public String level = null;
	/** The time limit of the game, when the limit is reached, the game finishes
	 */
	public int timeLimit = -1;
	/** The number of frags required to win. Used in DeatchMatch games.
	 */
	public int fragLimit = -1;
	/** The goal score for the team to win
	 */
	public double goalTeamScore = -1;
	/** The maximum number of teams possible in this game
	 */
	public int maxTeams = -1;
	/** The maximum number of players in a team
	 */
	public int maxTeamSize = -1;
	/** The ID field is used by multiple message types
	 */
	public String id = null; 
	/** The class of weapon being carried
	 */
	public String strClass = null;
	/** The strString field carries the text from a "UT text message" from another player
	 */
	public String strString = null;
	/** The absolute location of a bot / navpoint / weapon etc..
	 */
	public UtCoordinate location = null;
	/** The velocity of a bot or missile
	 */
	public UtCoordinate velocity = null;
	/** The team this bot is in, as an integer
	 */
	public int team = -1;
	/** The type of weapon refered to
	 */
	public String weapon = null;
	/** The sender of a message
	 */
	public String sender = null;
	/** The type of message recieved
	 */
	public String type = null;
	/** The normal (i.e. 90 degrees) of a specific rotation
	 */
	public UtCoordinate normal = null;
	/** Did the bot fall?
	 */
	public boolean fell = false;
	/** The name of the player
	 */
	public String player = null;
	/** The source of something that the bot heard
	 */
	public String source = null;
	/** The absolute rotation of the bot
	 */
	public UtCoordinate rotation = null;
	/** Information about a player that the bot can see
	 */
	public UtPlayer utPlayer = null;
	/** The time it will take for a the missile (targeted at me) will take to hit me
	 */
	public double impactTime = 0;
	/** The direction the missile is comming from
	 */
	public UtCoordinate direction = null;
	/** The bot the killed someone
	 */
	public String killer = null;
	/** The type of damage recieved
	 */
	public String damageType = null;
	/** The amount of damage recieved (where full health = 100)
	 */
	public int damage = -1;
	/** The bot the damaged me
	 */
	public String instigator = null;
	/** A path to a specific location (i.e. a list of pathnodes to follow)
	 */
	public UtPath path = null;
	/** Can I reach this location? true=yes!
	 */
	public boolean reachable = false;
	/** reachable FROM here
	 */
	public UtCoordinate from = null;
	/** The constructor uses a raw message from the gamebots server to fill in the appropriate fields in this class with useable information
	 * 
	 * @param message a raw String message from the gamebots server
	 */
	public UtAsyncInfo(Message message)
	{
		String key;
		String property;
		msg = message; //keep for debugging purposes
		msgType = msg.getType(); 
		Iterator keyIter = msg.getKeySet().iterator();		

		if(msgType.equals(UtConstants.SEE))
			utPlayer = new UtPlayer(msg);
		else if(msgType.equals(UtConstants.PATH_MSG))
			path = new UtPath(msg);
		else
		{
			while (keyIter.hasNext())			
			{
				key = (String) keyIter.next();			
				property = (String) msg.getProperty(key);
				if(key.equals(UtConstants.GAMETYPE))
					gameType = property;
				else if(key.equals(UtConstants.LEVEL))
					level = property;
				else if(key.equals(UtConstants.TIMELIMIT))
					timeLimit = Integer.parseInt(property);
				else if(key.equals(UtConstants.FRAGLIMIT))
					fragLimit = Integer.parseInt(property);
				else if(key.equals(UtConstants.GOALTEAMSCORE))
					goalTeamScore = Double.parseDouble(property);
				else if(key.equals(UtConstants.MAXTEAMS))
					maxTeams = Integer.parseInt(property);
				else if(key.equals(UtConstants.MAXTEAMSIZE))
					maxTeamSize = Integer.parseInt(property);
				else if(key.equals(UtConstants.CLASS))
					strClass = property;
				else if(key.equals(UtConstants.STRING))
					strString = property;
				else if(key.equals(UtConstants.SENDER))
					sender = property;
				else if(key.equals(UtConstants.TYPE))
					type = property;
				else if(key.equals(UtConstants.NORMAL))
					normal = new UtCoordinate(property);
				else if(key.equals(UtConstants.FELL))
					fell = property.equalsIgnoreCase("true");
				else if(key.equals(UtConstants.PLAYER_PARAM))
					player = property;
				else if(key.equals(UtConstants.SOURCECLASS))
					source = property;
				else if(key.equals(UtConstants.ROTATION))
					rotation = new UtCoordinate(property);
				else if(key.equals(UtConstants.IMPACT_TIME))		//not sure if this is int or double,
					impactTime = Double.parseDouble(property);   	//wait for error message to display it when it occurs
				else if(key.equals(UtConstants.DIRECTION))
					direction = new UtCoordinate(property);
				else if(key.equals(UtConstants.KILLER))
					killer = property;
				else if(key.equals(UtConstants.DAMAGETYPE))
					damageType = property;
				else if(key.equals(UtConstants.DAMAGE_PARAM))
					damage = Integer.parseInt(property);
				else if(key.equals(UtConstants.INSTIGATOR))
					instigator = property;
				else if(key.equals(UtConstants.REACHABLE))
					reachable = property.equalsIgnoreCase("true");
				else if(key.equalsIgnoreCase(UtConstants.ID))
					id = property;
				else if(key.equals(UtConstants.LOCATION))
					location = new UtCoordinate(property);
				else if(key.equals(UtConstants.VELOCITY))
					velocity = new UtCoordinate(property);
				else if(key.equals(UtConstants.TEAM))
					team = Integer.parseInt(property);
				else if(key.equals(UtConstants.WEAPON))
					weapon = property;
				else if(key.equals(UtConstants.FROM))
					from = new UtCoordinate(property);				
				else				
					System.out.println("Error: Unknown key '" + key + "=" + property + "' when decoding '"+msgType+"' Async message.");
			}
		}
	}
	
	public UtAsyncInfo(String msgType, String gameType, String level,
			   int timeLimit, int fragLimit, double goalTeamScore,
			   int maxTeams, int maxTeamSize, String id,
			   String strClass, String strString,
			   UtCoordinate location, UtCoordinate velocity,
			   int team, String weapon, String sender, String type,
			   UtCoordinate normal, boolean fell, String player,
			   String source, UtCoordinate rotation,
			   UtPlayer utPlayer, double impactTime,
			   UtCoordinate direction, String killer,
			   String damageType, int damage, String instigator,
			   UtPath path, boolean reachable, UtCoordinate from){
		this.msgType = msgType;
		this.gameType = gameType;
		this.level = level;
		this.timeLimit = timeLimit;
		this.fragLimit = fragLimit;
		this.goalTeamScore = goalTeamScore;
		this.maxTeams = maxTeams;
		this.maxTeamSize = maxTeamSize;
		this.id = id;
		this.strClass = strClass;
		this.strString = strString;
		this.location = location;
		this.velocity = velocity;
		this.team = team;
		this.weapon = weapon;
		this.sender = sender;
		this.type = type;
		this.normal = normal;
		this.fell = fell;
		this.player = player;
		this.source = source;
		this.rotation = rotation;
		this.utPlayer = utPlayer;
		this.impactTime = impactTime;
		this.direction = direction;
		this.killer = killer;
		this.damageType = damageType;
		this.damage = damage;
		this.instigator = instigator;
		this.path = path;
		this.reachable = reachable;
		this.from = from;
	}
        
        public String toString(){
            Iterator keyIter = msg.getKeySet().iterator();
            StringBuffer sb = new StringBuffer("UtAsyncInfo{");
            String key=null;
            for(;keyIter.hasNext();key=(String)keyIter.next()){
                if(key!=null)
                    sb.append("("+key+","+msg.getProperty(key)+")");
            }
            return sb.toString();
        }
}
