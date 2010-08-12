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

/** This class holds information about a path recieved from a requestpath command
 * 
 * @author Christos Sioutis
 */
public class UtPath implements Immutable, Serializable
{
	/** The list of path nodes to follow
	 */
	public List path;
        
        public UtPath(List path){
            this.path = path;
        }
	
	/** Reads a PTH message and initialises the path list accordingly
	 * 
	 * @param msg The PTH message
	 */
	public UtPath(Message msg)
	{
		String key;
		String property;
		String[] prop;
		
		Iterator keyIter = msg.getKeySet().iterator();
		
		path = new LinkedList();
		
		while (keyIter.hasNext())			
		{			
			key = (String) keyIter.next();			
			property = (String) msg.getProperty(key);
			prop = property.split("&");
			path.add(new UtNav(prop[0],prop[1],"True"));
		}
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtPath tmp = (UtPath) obj;
        return equivalent(tmp);
    }
    /** UtPath are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtPath other){
        if(path.equals(other.path))
            return true;
        return false;
    }
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        return path.hashCode();
    }
    public String toString(){
        return "UtPath{"+path+"}";
    }
}
