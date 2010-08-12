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

/** This class contains a list of a number of individual messages that were recieved in a sync block
 * 
 * @author Christos Sioutis
 */
public class UtSyncInfo
{
	/** The original message block recieved from UT
	 */
	public MessageBlock msgBlk;
	/** The decoded SLF message if recieved, null if not recieved
	 */
	public UtSelf self;
	/** The decoded GAM message if recieved, null if not recieved
	 */
	public UtGame game;
	/** A list of the decoded PLR messages if recieved, null if not recieved
	 */
	public List players;
	/** A list of the decoded NAV messages if recieved, null if not recieved
	 */
	public List navs;
	/** A list of the decoded MOV messages if recieved, null if not recieved
	 */
	public List movs;
	/** A list of the decoded DOM messages if recieved, null if not recieved
	 */
	public List doms;
	/** A list of the decoded FLG messages if recieved, null if not recieved
	 */
	public List flags;
	/** A list of the decoded NAV messages if recieved, null if not recieved
	 */
	public List invs;
        
        public UtSyncInfo(UtSelf self,UtGame game,List players,List navs,List movs,List doms,List flags,List invs){
            this.self = self;
            this.game = game;
            this.players = players;
            this.navs = navs;
            this.movs = movs;
            this.doms = doms;
            this.flags = flags;
            this.invs = invs;
        }
	
	/** Reads the message block and initialises each variable depending on whether a message was recieved for that variable. Otherwise the variable initialises to "null"
	 * 
	 * @param mb The sync message block recieved form UT
	 */
	public UtSyncInfo(MessageBlock mb)
	{
		msgBlk = mb;
		self=null;
		game=null;
		players = null;
		navs = null;
		movs = null;
		doms = null;
		flags = null;
		invs = null;
		
		updateInfo();
	}
	
	
	/** Used by the constructor to make the initialisation process easier to debug and understand.
	 */
	private void updateInfo()
	{
		Iterator mbIter = msgBlk.getMessages();
		Message msg;
		String msgType;
		
		while(mbIter.hasNext())
		{
			msg = (Message) mbIter.next();
			msgType = msg.getType();
			
			if(msgType.equals(UtControllerConstants.END))
				return;
			else if(msgType.equals(UtControllerConstants.SELF))
				self = new UtSelf(msg);
			else if (msgType.equals(UtControllerConstants.GAMESTATE))
				game = new UtGame(msg);
			else if (msgType.equals(UtControllerConstants.PLAYER))
			{
				if(players==null) 
					players = new LinkedList();
				players.add(new UtPlayer(msg));
			}
			else if (msgType.equals(UtControllerConstants.NAV))
			{
				if(navs==null) 
					navs = new LinkedList();
				navs.add(new UtNav(msg));
			}	
			else if (msgType.equals(UtControllerConstants.MOV))
			{
				if(movs==null) 
					movs = new LinkedList();
				movs.add(new UtMov(msg));
			}
			else if (msgType.equals(UtControllerConstants.FLAG))
			{
				if(flags==null) 
					flags = new LinkedList();
				flags.add(new UtFlag(msg));
			}	
			else if (msgType.equals(UtControllerConstants.DOM))
			{
				if(flags==null) 
					doms = new LinkedList();
				doms.add(new UtDom(msg));
			}	
			
		else if (msgType.equals(UtControllerConstants.INV))
			{
				if(invs==null) 
					invs = new LinkedList();
				invs.add(new UtInv(msg));
			}
			else
				System.out.println("Error: Unknown message type " + msgType + " when building UtSyncInfo message block.");
		}
	}
    public String toString(){
        return "UtSyncInfo{"+self+","+game+","+players+","+navs+","+movs+","+doms+","+flags+","+invs+"}";
    }
}
