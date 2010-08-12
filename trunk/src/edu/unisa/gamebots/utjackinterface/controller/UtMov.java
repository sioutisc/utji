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

/** This class holds information about a Mover that the bot can see in the game
 * 
 * @author Christos Sioutis
 */
public class UtMov implements Immutable, Serializable
{
	/** The unique id of the mover
	 */
	public String id;
	/** The absolute location of the mover
	 */
	public UtCoordinate location;
	/** A boolean indicating whether the mover is reachable by directly walking to it
	 */
	public boolean reachable;
	/** A boolean indicating whether the mover needs to be shot to be activated
	 */
	public boolean damageTrig;
	/** The class of mover
	 */
	public String moverClass;
        
        public boolean viewable;
        
        public UtMov(String id,UtCoordinate location,boolean reachable,boolean damageTrig,String moverClass,boolean viewable){
            this.id = id;
            this.location = location;
            this.reachable = reachable;
            this.damageTrig = damageTrig;
            this.moverClass = moverClass;
            this.viewable = viewable;
        }
	
	/** Reads an MOV message and and updates the corresponding fields accordingly
	 * 
	 * @param msg The MOV message
	 */
	public UtMov(Message msg)
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
			else if(key.equals(UtControllerConstants.DAMAGETRIG))
				damageTrig = property.equalsIgnoreCase("true");
			else if(key.equals(UtControllerConstants.MOVERCLASS))
				moverClass = property;
			else				
				System.out.println("Error: Unknown key " + key + " when decoding MOV sync message.");
		}

	}

/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtMov tmp = (UtMov) obj;
        return equivalent(tmp);
    }
    /** UtFlag are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtMov other){
        if(id.equals(other.id) && location.equals(other.location) && reachable == other.reachable && damageTrig == other.damageTrig && moverClass.equals(other.moverClass))
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
            toReturn += 78;
        if(damageTrig)
            toReturn += 26;
        toReturn += id.hashCode() + location.hashCode() + moverClass.hashCode();
        return toReturn;
    }
    public String toString(){
        return "UtMov{"+id+","+location+","+reachable+","+damageTrig+","+moverClass+","+viewable+"}";
    }
}
