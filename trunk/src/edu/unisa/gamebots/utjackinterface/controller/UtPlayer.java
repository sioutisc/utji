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
import java.util.*;
import aos.jack.jak.beliefset.*;
import java.io.*;

/** This class holds information about another player that the bot can see
 * 
 * @author Christos Sioutis
 */
public class UtPlayer implements Immutable, Serializable
{
	/** A boolean value indicating whether the bot is firing at anyone
	 */
	public boolean altFiring;
	/** A boolean value indicating whether the bot is alternate-firing at anyone
	 */
	public boolean firing;
	/** The unique id of the bot
	 */
	public String id;
	/** The name of the bot
	 */
	public String name;
	/** The absolute rotation of the bot in pitch, yaw and roll
	 */
	public UtCoordinate rotation;
	/** The absolute location of the bot in x, y and z coordinates
	 */
	public UtCoordinate location;
	/** The velocity vector of the bot
	 */
	public UtCoordinate velocity;
	/** The team that the bot belongs to
	 */
	public int team;
	/** A boolean value indicating whether the bot can be reached by directly walking to it
	 */
	public boolean reachable;
	/** The name of the weapon that the bot is holding
	 */
	public String weapon;
        
        public boolean viewable;
        
        public UtPlayer(boolean altFiring,boolean firing,String id,String name,UtCoordinate rotation,
                        UtCoordinate location,UtCoordinate velocity,int team,boolean reachable,String weapon, boolean viewable){
            this.altFiring = altFiring;
            this.firing = firing;
            this.id = id;
            this.name = name;
            this.rotation = rotation;
            this.location = location;
            this.velocity = velocity;
            this.team = team;
            this.reachable = reachable;
            this.weapon = weapon;
            this.viewable = viewable;
        }
	
	/** Reads a PLR message and initialises the fields accordingly
	 * 
	 * @param msg The PLR message
	 */
	public UtPlayer(Message msg)
	{
		String key;
		String property;
		String temp;
		Iterator keyIter = msg.getKeySet().iterator();
		while (keyIter.hasNext())
			
		{
			key = (String) keyIter.next();			
			property = (String) msg.getProperty(key);
			
			if(key.equals(UtControllerConstants.ALTFIRING))
			{
				if (property.equals("1"))
					altFiring = true;
				else
					altFiring = false;
			}
			else if(key.equals(UtControllerConstants.FIRING))
			{
				if (property.equals("1"))
					firing = true;
				else
					firing = false;
			}
			else if(key.equals(UtControllerConstants.REACHABLE))
				reachable = property.equalsIgnoreCase("true");
			else if(key.equals(UtControllerConstants.ID))
				id = property;
			else if(key.equals(UtControllerConstants.VELOCITY))
				velocity = new UtCoordinate(property);
			else if(key.equals(UtControllerConstants.NAME))
				name = property;
			else if(key.equals(UtControllerConstants.LOCATION))
				location = new UtCoordinate(property);
			else if(key.equals(UtControllerConstants.TEAM))
				team = Integer.parseInt(property);
			else if(key.equals(UtControllerConstants.WEAPON))
				weapon = property;
			else if(key.equals(UtControllerConstants.ROTATION))
				rotation = new UtCoordinate(property);
			else				
				System.out.println("Error: Unknown key '" + key + "=" + property + "' when decoding PLR sync message.");
		}
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtPlayer tmp = (UtPlayer) obj;
        return equivalent(tmp);
    }
    /** UtPlayer are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtPlayer other){
        if(altFiring == other.altFiring && firing == other.firing && id.equals(other.id) && name.equals(other.name) &&
           rotation.equals(other.rotation) && location.equals(other.location) && velocity.equals(other.velocity) &&
           reachable == other.reachable && team == other.team && weapon.equals(other.weapon))
            return true;
        return false;
    }
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        int toReturn = 0;
        if(altFiring)
            toReturn += 78;
        if(firing)
            toReturn += 26;
        if(reachable)
            toReturn += 22;
        toReturn += id.hashCode() + name.hashCode() + rotation.hashCode() + location.hashCode() + velocity.hashCode() + team + weapon.hashCode();
        return toReturn;
    }
    public String toString(){
        return "UtPlayer{"+altFiring+","+firing+","+id+","+name+","+rotation+","+location+","+velocity+
                         ","+team+","+reachable+","+weapon+","+viewable+"}";
    }
}
