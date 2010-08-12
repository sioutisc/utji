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


import java.util.*;
import aos.jack.jak.beliefset.*;
import java.io.*;

/** This class holds information about the current scores in the game
 * 
 * @author Christos Sioutis
 */
public class UtScores implements Immutable, Serializable
{
	/** A list containing the scores of each player in the game
	 */
	private List scores;
	
        public UtScores(List scores){
            this.scores = scores;
        }
        
	/** Initualises the scores with an empty list
	 */
	public UtScores()
	{
		scores = new LinkedList();		
	}

	/** Initualises the scores list and adds an initial score with the parameter key and value
	 * 
	 * @param key A key corresponding to a bot/team
	 * @param value The score of the bot/team
	 */
	public UtScores(String key, String value)
	{
		scores = new LinkedList();
		Properties p = new Properties();
		p.setProperty(key,value);
		scores.add(p);
	}

	/** adds a score to the score list
	 * 
	 * @param key A key corresponding to a bot/team
	 * @param value The score of the bot/team
	 */
	public void addScore(String key, String value)
	{
		Properties p = new Properties();
		p.setProperty(key,value);
		scores.add(p);		
	}
	
	/** returns the score list
	 * 
	 * @return The list of scores
	 */
	public List getScores()
	{
		return scores;
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtScores tmp = (UtScores) obj;
        return equivalent(tmp);
    }
    /** UtPath are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtScores other){
        if(scores.equals(other.scores))
            return true;
        return false;
    }
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        return scores.hashCode();
    }        
    public String toString(){
        return "UtScores{"+scores+"}";
    }    
}
