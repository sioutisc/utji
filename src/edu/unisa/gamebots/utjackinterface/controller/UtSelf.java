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

/** This class contains information about the bot status
 * 
 * @author Christos Sioutis
 */
public class UtSelf implements Immutable, Serializable
{
	/** A boolean value indicating whether the bot is alternate-firing
	 */
	public boolean altFiring;
	/** The id of the bot
	 */
	public String id;
	/** The velocity vector of the bot
	 */
	public UtCoordinate velocity;
	/** The name of the bot
	 */
	public String name;
	/** The absolute location of the bot
	 */
	public UtCoordinate location;
	/** The team that the bot belongs to
	 */
	public int team;
	/** The weapon that the bot is holding
	 */
	public String weapon;
	/** The health percentage of the bot
	 */
	public int health;
	/** The armor percentage of the bot
	 */
	public int armor;
	/** The ammo of the current weapon that the bot is holding
	 */
	public int currentAmmo;
	/** The absolute rotation of the bot
	 */
	public UtCoordinate rotation;	
	
	
        public UtSelf(boolean altFiring,String id,UtCoordinate velocity,
                      String name,UtCoordinate location,int team,
                      String weapon,int health,int armor,int currentAmmo,UtCoordinate rotation){
            this.altFiring = altFiring;
            this.id = id;
            this.velocity = velocity;
            this.name = name;
            this.location = location;
            this.team = team;
            this.weapon = weapon;
            this.health = health;
            this.armor = armor;
            this.currentAmmo = currentAmmo;
            this.rotation = rotation;	
        }
        
        
	/** Reads a SLF message and initialises the fields accoordingly
	 * 
	 * @param msg The SLF message
	 */
	public UtSelf(Message msg)
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
			else if(key.equals(UtControllerConstants.HEALTH))
				health = Integer.parseInt(property);
			else if(key.equals(UtControllerConstants.ARMOR))
				armor = Integer.parseInt(property); 	
			else if(key.equals(UtControllerConstants.CURRENTAMMO))
				currentAmmo = Integer.parseInt(property); 	
			else if(key.equals(UtControllerConstants.ROTATION))
				rotation = new UtCoordinate(property);
			else				
				System.out.println("Error: Unknown key '" + key+"="+property + "' when decoding SELF sync message.");
		}
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtSelf tmp = (UtSelf) obj;
        return equivalent(tmp);
    }
    /** UtPlayer are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtSelf other){
        if(altFiring == other.altFiring && id.equals(other.id) && velocity.equals(other.velocity) && 
           name.equals(other.name) && location.equals(other.location)  && team == other.team &&
           weapon.equals(other.weapon) && health == other.health && armor == other.armor &&
           currentAmmo == other.currentAmmo && rotation.equals(other.rotation))
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
        toReturn += id.hashCode() + velocity.hashCode() + name.hashCode() + location.hashCode() + team + weapon.hashCode() + health + armor + currentAmmo + rotation.hashCode();
        return toReturn;
    }
    public String toString(){
        return "UtSelf{"+altFiring+","+id+","+velocity+","+name+","+location+","+team+","+weapon+
                         ","+health+","+armor+","+currentAmmo+","+rotation+"}";
    }
}
