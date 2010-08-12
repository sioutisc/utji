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
import edu.isi.gamebots.client.*;
import aos.jack.jak.beliefset.*;
import java.io.*;

/** This is used to hold information about the current game being played
 * 
 * @author Christos Sioutis
 */
public class UtGame implements Immutable, Serializable
{

	/** The score of each player on the game
	 */
	public UtScores playerScores = null;
	/** The score of each team in the game
	 */
	public UtScores teamScores = null;
	/** The number of domination points of each team
	 */
	public UtScores domPoints = null;
	/** Indicates whether own team has the flag
	 */
	public boolean haveFlag = false;
	/** Indicates whether the opposing team has the flag
	 */
	public boolean enemyHasFlag = false;
        
        public UtGame(UtScores playerScores,UtScores teamScores,UtScores domPoints,boolean haveFlag,boolean enemyHasFlag){
            this.playerScores = playerScores;
            this.teamScores = teamScores;
            this.domPoints = domPoints;
            this.haveFlag = haveFlag;
            this.enemyHasFlag = enemyHasFlag;            
        }
	
	/** Reads a GAM message and initialises the corresponding fields accordingly
	 * 
	 * @param msg The FLG message
	 */
	public UtGame(Message msg)
	{
		String key;
		String value;
		String temp;
		
		Iterator keyIter = msg.getKeySet().iterator();

		while (keyIter.hasNext())
			
		{
			key = (String) keyIter.next();
			value = (String) msg.getProperty(key);
	
			if(key.startsWith(UtControllerConstants.PLAYERSCORES))
			{
				if (playerScores == null)
					playerScores = new UtScores();
				
				key = key.substring(UtControllerConstants.PLAYERSCORES.length()+1); //remove the header PlayerScores@ equal to 12 characters
				playerScores.addScore(key, value);
			}
			else if(key.startsWith(UtControllerConstants.TEAMSCORES))
			{
				if (teamScores == null)
					teamScores = new UtScores();
				
				key = key.substring(UtControllerConstants.TEAMSCORES.length()+1); //remove the header TeamScores@ equal to 12 characters
				teamScores.addScore(key, value);
			}
			else if(key.startsWith(UtControllerConstants.DOMPOINTS))
			{
				if (domPoints == null)
					domPoints = new UtScores();
				
				key = key.substring(UtControllerConstants.DOMPOINTS.length()+1); //remove the header TeamScores@ equal to 12 characters
				domPoints.addScore(key, value);
			}
			else if(key.equals(UtControllerConstants.HAVEFLAG))
				haveFlag = true;
			else if(key.equals(UtControllerConstants.ENEMYHASFLAG))
				enemyHasFlag = true;
			else				
				System.out.println("ERROR: Unknown key-value pair '"+key+"="+value+"' when decoding a GAM sync message");
		}
		
	}
        
/** Compares this instance to another instance. Should not need to change this method,
    just override the equivalent method as required. */    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtGame tmp = (UtGame) obj;
        return equivalent(tmp);
    }
    /** UtPlayer are equivalent if and only if
        they contain the same class members and the members have the same values*/
    public boolean equivalent(UtGame other){
        if(playerScores.equals(other.playerScores) && teamScores.equals(other.teamScores) &&
           domPoints.equals(other.domPoints) && haveFlag == other.haveFlag && enemyHasFlag == other.enemyHasFlag)
            return true;
        return false;
    }
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        int toReturn = 0;
        if(haveFlag)
            toReturn += 78;
        if(enemyHasFlag)
            toReturn += 60;
        toReturn += playerScores.hashCode() + teamScores.hashCode() + domPoints.hashCode();
        return toReturn;
    }
    
    public String toString(){
        return "UtGame{"+playerScores+","+teamScores+","+domPoints+","+haveFlag+","+enemyHasFlag+"}";
    }
}
