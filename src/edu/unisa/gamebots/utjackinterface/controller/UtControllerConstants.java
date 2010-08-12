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

import edu.isi.gamebots.client.*;

/** This interface provides constants used by the software. The constants are places here so that they are easy to change in case the gamebots designers decide to change them on the server.
 * 
 * @author Christos Sioutis
 */
public interface UtControllerConstants extends GamebotsConstants
{
	//sync message key constants --- extra
	
	//SLF keys
	public final static String ALTFIRING = "AltFiring";
	public final static String FIRING = "Firing";
	public final static String ID = "Id";
	public final static String VELOCITY = "Velocity";
	public final static String NAME = "Name";
	public final static String LOCATION = "Location";
	public final static String TEAM = "Team";
	public final static String WEAPON = "Weapon";
	public final static String HEALTH = "Health";
	public final static String ARMOR = "Armor";
	public final static String CURRENTAMMO = "CurrentAmmo";
	public final static String ROTATION = "Rotation";

	//GAM keys
	public final static String PLAYERSCORES = "PlayerScores";
	public final static String TEAMSCORES = "TeamScores";
	public final static String DOMPOINTS = "DomPoints";	
	public final static String HAVEFLAG = "HaveFlag";
	public final static String ENEMYHASFLAG = "EnemyHasFlag";
	
	//NAV keys
	public final static String REACHABLE = "Reachable";
	
	//MOV keys
	public final static String DAMAGETRIG = "DamageTrig";
	public final static String MOVERCLASS = "Class";

	//DOM keys
	public final static String CONTROLLER = "Controller";
	
	//FLG keys
	public final static String FLAGHOLDER = "Holder";
	public final static String FLAGSTATE = "State";
	public final static String FLAGHELD = "Held";
	public final static String FLAGDROPPED = "Dropped";
	public final static String FLAGHOME = "Home";
	
	//INV keys
	public final static String INVCLASS = "Class";  //although same as MOVERCLASS, we redifine for future compatibility
	
	
	//constants from JavaBot's GameBotsConstants.java *********************************  
	//copied here for easy reference and ensure future compatibility
	public final static String BEG = "BEG"; // Begin vision update
	public final static String SELF   = "SLF"; // Info about self
	public final static String GAMESTATE = "GAM"; //game status message
	public final static String PLAYER = "PLR"; // Other Player
	public final static String NAV = "NAV"; // Navigation point
	public final static String MOV = "MOV"; // world mobile [srs]
	public final static String INV = "INV"; // Inventory item
	//*********************************************************************************
	//required constants not defined in GamebotsConstants
	public final static String FLAG = "FLG"; // flag
	public final static String END = "END"; // end of sync block

	//ASYNC message types
	public final static String INFO = "NFO"; 
	public final static String ADD_INV = "AIN"; 
	public final static String VOICE_MSG = "VMS";
	public final static String VOICE_MSG_TEAM= "VMT";
	public final static String VOICE_COMMAND= "VMG";
	public final static String ZONE_CHANGE_FEET= "ZCF";
	public final static String ZONE_CHANGE_HEAD= "ZCH";
	public final static String ZONE_CHANGE_BOT= "ZCB";
	public final static String CHANGE_WEAPON = "CWP";
	public final static String WALL = "WAL";
	public final static String FALL = "FAL";	
	public final static String BUMP = "BMP";
	public final static String HEAR_PICKUP = "HRP";
	public final static String HEAR_NOISE = "HRN";
	public final static String SEE = "SEE";
	public final static String PROJECTILE = "PRJ";
	public final static String KILLED = "KIL";
	public final static String DIED = "DIE";
	public final static String DAMAGE = "DAM";
	public final static String HIT = "HIT";
	public final static String PATH_MSG = "PTH";
	public final static String REACH = "RCH";
	public final static String GAME_OVER = "FIN";
	
	
	//ASYNC parameter constants 
	public final static String GAMETYPE = "Gametype";	
	public final static String LEVEL = "Level";
	public final static String TIMELIMIT = "TimeLimit";
	public final static String FRAGLIMIT = "FragLimit";
	public final static String GOALTEAMSCORE = "GoalTeamScore";
	public final static String MAXTEAMS = "MaxTeams";
	public final static String MAXTEAMSIZE = "MaxTeamSize";
	public final static String CLASS = "Class";
	public final static String STRING = "String";
	public final static String SENDER = "Sender";
	public final static String TYPE = "Type";
	public final static String NORMAL = "Normal";
	public final static String FELL = "Fell";
	public final static String PLAYER_PARAM = "Player";
	public final static String SOURCECLASS = "SourceClass";
	public final static String IMPACT_TIME = "Time";
	public final static String DIRECTION = "Direction";
	public final static String KILLER = "Killer";
	public final static String DAMAGETYPE = "DamageType";
	public final static String DAMAGE_PARAM = "Damage";
	public final static String INSTIGATOR = "Instigator";
	public final static String FROM = "From";
}
