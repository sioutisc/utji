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

/** This class contains information about a navigation point that the bot can see
 * 
 * @author Christos Sioutis
 */
public class UtNav implements Immutable, Serializable
{
	/** The unique id of the navigation point
	 */
	public String id;
	/** The absolute location of the navigation point
	 */
	public UtCoordinate location;
	/** A boolean indicating whether the navigation point is reachable by directly walking to it
	 */
	public boolean reachable;
        
        public boolean viewable;
        
        public UtNav(String id,UtCoordinate location,boolean reachable){
            this.id = id;
            this.location = location;
            this.reachable = reachable;            
        }

        public UtNav(String id,UtCoordinate location,boolean reachable,boolean viewable){
            this.id = id;
            this.location = location;
            this.reachable = reachable;            
            this.viewable = viewable;
        }

	
	/** Reads a NAV message and initialises the corresponding fields
	 * 
	 * @param msg The NAV message
	 */
	public UtNav(Message msg)
	{
		String key;
		String property;
		Iterator keyIter = msg.getKeySet().iterator();		
		
		while (keyIter.hasNext())			
		{
			key = (String) keyIter.next();			
			property = (String) msg.getProperty(key);
			
			if(key.equals(UtControllerConstants.REACHABLE))
				reachable = property.equalsIgnoreCase("true");
			else if(key.equals(UtControllerConstants.ID))
				id = property;
			else if(key.equals(UtControllerConstants.LOCATION))
				location = new UtCoordinate(property);
			else				
				System.out.println("Error: Unknown key " + key + " when decoding NAV sync message.");
		}
	}

	public double distanceFrom(UtNav other)
	{
		double magX = other.location.x - location.x;
		double magY = other.location.y - location.y;
		double magZ = other.location.z - location.z;
		return(Math.sqrt(Math.pow(magX,2) + Math.pow(magY,2) +Math.pow(magZ,2)));  // |v|=sqrt(x^2+y^2+z^2)
	}
	
	
	/** Initialises the fields using information passed explixitly from function parameters
	 * 
	 * @param newId The id of the NAV point
	 * @param newLocation The absolute location of the NAV point
	 * @param newReachable Is the NAV point reachable directly?
	 */
	public UtNav(String newId, String newLocation, String newReachable)
	{
		id = newId;
		location = new UtCoordinate(newLocation);
		reachable = newReachable.equalsIgnoreCase("true");
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtNav tmp = (UtNav) obj;
        return equivalent(tmp);
    }
    /** UtFlag are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtNav other){
        if(id.equals(other.id) && location.equals(other.location) && reachable == other.reachable)
            return true;
        return false;
    }
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        int toReturn = 0;
        if(reachable)
            toReturn += 26;
        toReturn += id.hashCode() + location.hashCode();
        return toReturn;
    }
    
    public String toString(){
        return "UtNav{"+id+","+location+","+reachable+","+viewable+"}";
    }
}
