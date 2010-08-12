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

/** This class is used to hold information about an inventory item that the bot can see
 * 
 * @author Christos Sioutis
 */
public class UtInv implements Immutable, Serializable
{
	/** The id of the inventory item
	 */
	public String id;
	/** The absolute location of the inventory item
	 */
	public UtCoordinate location;
	/** A boolean indicating whether the item can be reached by directly walking to it
	 */
	public boolean reachable;
	/** The class of inventory
	 */
	public String invClass;
        
        public boolean viewable;
        
        public UtInv(String id,UtCoordinate location,boolean reachable,String invClass, boolean viewable){
             this.id = id;
             this.location = location;
             this.reachable = reachable;
             this.invClass = invClass;
             this.viewable = viewable;
        }
 	
	/** Reads an INV message and it updates the corresponding fields accordingly
	 * 
	 * @param msg The INV message
	 */
	public UtInv(Message msg)
	{
		String key;
		String property;
		Iterator keyIter = msg.getKeySet().iterator();		
		
		while (keyIter.hasNext())			
		{
			key = (String) keyIter.next();			
			property = (String) msg.getProperty(key);
			
			if(key.equals(UtControllerConstants.ID))
				id = property;
			else if(key.equals(UtControllerConstants.LOCATION))
				location = new UtCoordinate(property);
			else if(key.equals(UtControllerConstants.REACHABLE))
				reachable = property.equalsIgnoreCase("true");
			else if(key.equals(UtControllerConstants.INVCLASS))
				invClass = property;
			else				
				System.out.println("Error: Unknown key " + key + " when decoding INV sync message.");
		}
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtInv tmp = (UtInv) obj;
        return equivalent(tmp);
    }
    /** UtFlag are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtInv other){
        if(id.equals(other.id) && location.equals(other.location) && reachable == other.reachable && invClass.equals(other.invClass))
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
            toReturn += 19;
        toReturn += id.hashCode() + location.hashCode() + invClass.hashCode();
        return toReturn;
    }
    
    public String toString(){
        return "UtInv{"+id+","+location+","+reachable+","+invClass+","+viewable+"}";
    }
}
