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

import aos.jack.jak.beliefset.*;
import edu.unisa.gamebots.utjackinterface.*;
import edu.unisa.gamebots.utjackinterface.caps.sync.*;
import edu.unisa.gamebots.utjackinterface.caps.async.*;
import java.util.Iterator;


/** This class holds information about a path recieved from a requestpath command
 * 
 * @author Christos Sioutis
 */
public class UtMessageProcessing implements UtJackView {
	AsyncMsgs async;
	Self self;
	Game game;
	Players players;
	Navs navs;
	Movs movs;
	Doms doms;
	Flags flags;
	Invs invs;
	
	public UtSyncInfo currSync, prevSync;
	
	
	public UtMessageProcessing(AsyncMsgs async, Self self, Game game,
	                    Players players, Navs navs, Movs movs,
		            Doms doms, Flags flags, Invs invs){
		this.async = async;		    
		this.self = self;
		this.game = game;
		this.players = players;
		this.navs = navs;
		this.movs = movs;
		this.doms = doms;
		this.flags = flags;
		this.invs = invs;
	}
	
	public void processAsyncMessage(UtAsyncInfo asyncm){
		try{
			async.add(	asyncm.msgType, 	asyncm.gameType, 	asyncm.level,
					asyncm.timeLimit, 	asyncm.fragLimit, 	asyncm.goalTeamScore,
					asyncm.maxTeams, 	asyncm.maxTeamSize, 	asyncm.id, 
					asyncm.strClass, 	asyncm.strString, 	asyncm.location,
					asyncm.velocity, 	asyncm.team, 		asyncm.weapon,
					asyncm.sender, 		asyncm.type, 		asyncm.normal,
					asyncm.fell, 		asyncm.player, 		asyncm.source,
					asyncm.rotation, 	asyncm.utPlayer, 	asyncm.impactTime,
					asyncm.direction, 	asyncm.killer, 		asyncm.damageType,
					asyncm.damage, 		asyncm.instigator, 	asyncm.path, 
					asyncm.reachable, 	asyncm.from		);
		}
		catch(BeliefSetException e){System.out.println("Error when adding beliefs in UtMessageProcessing: " + e );}
		catch(Exception e){System.out.println("Error in UtMessageProcessing.processAsyncMessage("+asyncm.msgType+") "+e);}
	}
	
	public void processSyncMessage(UtSyncInfo sync){
		currSync = sync;
		
		try{
			if(prevSync == null)
				processFirstSync();
			else{
				processSelf();
				processGame();
				processPlayers();
				processNavs();
				processMovs();
				processDoms();
				processFlags();
				processInvs();
			}
		}
		catch(BeliefSetException e){System.out.println("Error when adding beliefs in UtJackInterface: " + e );}
		catch(Exception e){System.out.println("Error in UtMessageProcessing.processSyncMessage() "+e);}

		prevSync = sync;
	}

	private void processFirstSync() throws BeliefSetException
	{
		Iterator list;
		
		if (currSync.self != null)
		{
			self.add(currSync.self.id, currSync.self.altFiring, currSync.self.velocity, currSync.self.name,
				 currSync.self.location, currSync.self.team, currSync.self.weapon, currSync.self.health,
				 currSync.self.armor, currSync.self.currentAmmo, currSync.self.rotation);
		}
		
		if (currSync.game != null)
		{			
			game.add(currSync.game.playerScores, currSync.game.teamScores, currSync.game.domPoints,
				 currSync.game.haveFlag, currSync.game.enemyHasFlag);
		}
		
		if (currSync.players != null)
		{
			list = currSync.players.iterator();
			UtPlayer player;
			while (list.hasNext())
			{
				player = (UtPlayer) list.next();
				players.add(player.id, player.altFiring, player.firing, player.name,
					    player.rotation, player.location, player.velocity,
					    player.team, player.reachable, player.weapon, true);
			}
		}
		
		if (currSync.navs != null)
		{
			list = currSync.navs.iterator();
			UtNav nav;
			while (list.hasNext())
			{
				nav = (UtNav) list.next();			
				navs.add(nav.id, nav.location, nav.reachable, true);
			}
		}
		
		if (currSync.movs != null)
		{
			list = currSync.movs.iterator();
			UtMov mov;
			while (list.hasNext())
			{
				mov = (UtMov) list.next();			
				movs.add(mov.id, mov.location, mov.reachable, mov.damageTrig, mov.moverClass, true);
			}
		}
		
		if (currSync.doms != null)
		{
			list = currSync.doms.iterator();
			UtDom dom;
			while (list.hasNext())
			{
				dom = (UtDom) list.next();
				doms.add(dom.id, dom.controller, dom.location, dom.reachable, true);
			}
		}
		
		if (currSync.flags != null)
		{
			list = currSync.flags.iterator();
			UtFlag flag;
			while (list.hasNext())
			{
				flag = (UtFlag) list.next();						
				flags.add(flag.id, flag.location, flag.holder, flag.team, flag.reachable, flag.state, true);
			}
		}
		
		if (currSync.invs != null)
		{
			list = currSync.invs.iterator();
			UtInv inv;
			while (list.hasNext())
			{
				inv = (UtInv) list.next();			
				invs.add(inv.id, inv.location, inv.reachable, inv.invClass, true);
			}
		}
		
	}

	private void processGame() throws BeliefSetException
	{	
		if (currSync.game != null)
		{			
			game.add(currSync.game.playerScores, currSync.game.teamScores, currSync.game.domPoints,
				 currSync.game.haveFlag, currSync.game.enemyHasFlag);
		}
	}
	
	private void processSelf() throws BeliefSetException
	{
		if (currSync.self != null)
		{
			self.add(currSync.self.id, currSync.self.altFiring, currSync.self.velocity, currSync.self.name,
				 currSync.self.location, currSync.self.team, currSync.self.weapon, currSync.self.health,
				 currSync.self.armor, currSync.self.currentAmmo, currSync.self.rotation);
		}
	}
	
	private void processPlayers() throws BeliefSetException
	{
    		Iterator previousPlayers, currentPlayers;	
    		UtPlayer previousPlayer, currentPlayer;
		boolean found;
	
    		if (prevSync.players != null)	//check whether any players were seen before
    		{
    			previousPlayers = prevSync.players.iterator();   //extract the previous player list
			
    			if (currSync.players != null)			//check whether any players can be seen at the moment
    			{
				/* ********************** reset any previous unseen players   *********************************/
    				while (previousPlayers.hasNext()) 		//for each previous player seen
    				{
					previousPlayer = (UtPlayer) previousPlayers.next();	//extract the player from the list

					currentPlayers = currSync.players.iterator();	//extract the current player list
				
    					found = false;
    				    
				    	while (currentPlayers.hasNext() &&  found == false)		//for each current player seen
					{
						currentPlayer = (UtPlayer) currentPlayers.next();	//extract the player from the list
						if(previousPlayer.id.equals(currentPlayer.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other player is gone  
					{
						players.add(previousPlayer.id, previousPlayer.altFiring, previousPlayer.firing, previousPlayer.name,
								previousPlayer.rotation, previousPlayer.location, previousPlayer.velocity,
								previousPlayer.team, false, previousPlayer.weapon, false);  //resets 'viewable' to false in the beliefset

					}
    				}
				/* ********************** add the currently seen players   *********************************/
				currentPlayers = currSync.players.iterator();	//extract the current player list
    				while (currentPlayers.hasNext()) 
    				{
    					currentPlayer = (UtPlayer) currentPlayers.next();
					players.add(currentPlayer.id, currentPlayer.altFiring, currentPlayer.firing, currentPlayer.name,
						    	currentPlayer.rotation, currentPlayer.location, currentPlayer.velocity,
							currentPlayer.team, currentPlayer.reachable, currentPlayer.weapon, true);  //also sets 'viewable' to true in the beliefset
    				}
    			}
    			else	//this else is executed when we saw one or more players before and none after
    			{
    				while (previousPlayers.hasNext()) 
    				{
    					previousPlayer = (UtPlayer) previousPlayers.next();
					players.add(previousPlayer.id, previousPlayer.altFiring, previousPlayer.firing, previousPlayer.name,
						    	previousPlayer.rotation, previousPlayer.location, previousPlayer.velocity,
							previousPlayer.team, false, previousPlayer.weapon, false);  //resets 'viewable' to false in the beliefset
    				}
    			}
    		}
	}//end of processPlayers

	
	private void processNavs() throws BeliefSetException
	{
    		Iterator previousNavs, currentNavs;	
    		UtNav previousNav, currentNav;
		boolean found;
	
    		if (prevSync.navs != null)	//check whether any navs were seen before
    		{
    			previousNavs = prevSync.navs.iterator();   //extract the previous nav list
    			if (currSync.navs != null)			//check whether any navs can be seen at the moment
    			{
				/* ********************** reset any previous unseen navs   *********************************/
    				while (previousNavs.hasNext()) 		//for each previous nav seen				
    				{
					previousNav = (UtNav) previousNavs.next();	//extract the nav from the list
    
    					currentNavs = currSync.navs.iterator();	//extract the current nav list
					
    					found = false;
    				    
				    	while (currentNavs.hasNext() &&  found == false)		//for each current nav seen
					{
						currentNav = (UtNav) currentNavs.next();	//extract the nav from the list
						if(previousNav.id.equals(currentNav.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other nav is gone  
					{
						navs.add(previousNav.id, previousNav.location, false, false);
					}
    				}
				/* ********************** add the currently seen navs   *********************************/
				currentNavs = currSync.navs.iterator();	//extract the current nav list
    				while (currentNavs.hasNext()) 
    				{
    					currentNav = (UtNav) currentNavs.next();
					navs.add(currentNav.id, currentNav.location, currentNav.reachable, true);
    				}
    			}
    			else	//this else is executed when we saw one or more navs before and none after
    			{
    				while (previousNavs.hasNext()) 
    				{
    					previousNav = (UtNav) previousNavs.next();
					navs.add(previousNav.id, previousNav.location, false, false);
    				}
    			}
    		}
	}//end of processNavs


	private void processMovs() throws BeliefSetException
	{
    		Iterator previousMovs, currentMovs;	
    		UtMov previousMov, currentMov;
		boolean found;
	
    		if (prevSync.movs != null)	//check whether any movs were seen before
    		{
    			previousMovs = prevSync.movs.iterator();   //extract the previous mov list
    			if (currSync.movs != null)			//check whether any movs can be seen at the moment
    			{
				/* ********************** reset any previous unseen movs   *********************************/
    				while (previousMovs.hasNext()) 		//for each previous mov seen				
    				{
					previousMov = (UtMov) previousMovs.next();	//extract the mov from the list
    
    					currentMovs = currSync.movs.iterator();	//extract the current mov list
					
    					found = false;
    				    
				    	while (currentMovs.hasNext() &&  found == false)		//for each current mov seen
					{
						currentMov = (UtMov) currentMovs.next();	//extract the mov from the list
						if(previousMov.id.equals(currentMov.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other mov is gone  
					{
						movs.add(previousMov.id, previousMov.location, false, previousMov.damageTrig, previousMov.moverClass, false);
					}
    				}
				/* ********************** add the currently seen movs   *********************************/
				currentMovs = currSync.movs.iterator();	//extract the current mov list
    				while (currentMovs.hasNext()) 
    				{
    					currentMov = (UtMov) currentMovs.next();
					movs.add(currentMov.id, currentMov.location, currentMov.reachable, currentMov.damageTrig, currentMov.moverClass, true);
    				}
    			}
    			else	//this else is executed when we saw one or more movs before and none after
    			{
    				while (previousMovs.hasNext()) 
    				{
    					previousMov = (UtMov) previousMovs.next();
					movs.add(previousMov.id, previousMov.location, false, previousMov.damageTrig, previousMov.moverClass, false);
    				}
    			}
    		}
	}//end of processMovs

	private void processDoms() throws BeliefSetException
	{
    		Iterator previousDoms, currentDoms;	
    		UtDom previousDom, currentDom;
		boolean found;
	
    		if (prevSync.doms != null)	//check whether any doms were seen before
    		{
    			previousDoms = prevSync.doms.iterator();   //extract the previous dom list
    			if (currSync.doms != null)			//check whether any doms can be seen at the moment
    			{
				/* ********************** reset any previous unseen doms   *********************************/
    				while (previousDoms.hasNext()) 		//for each previous dom seen				
    				{
					previousDom = (UtDom) previousDoms.next();	//extract the dom from the list
    
    					currentDoms = currSync.doms.iterator();	//extract the current dom list
					
    					found = false;
    				    
				    	while (currentDoms.hasNext() &&  found == false)		//for each current dom seen
					{
						currentDom = (UtDom) currentDoms.next();	//extract the dom from the list
						if(previousDom.id.equals(currentDom.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other dom is gone  
					{
						doms.add(previousDom.id, previousDom.controller, previousDom.location, false, false);
					}
    				}
				/* ********************** add the currently seen doms   *********************************/
				currentDoms = currSync.doms.iterator();	//extract the current dom list
    				while (currentDoms.hasNext()) 
    				{
    					currentDom = (UtDom) currentDoms.next();
					doms.add(currentDom.id, currentDom.controller, currentDom.location, currentDom.reachable, true);
    				}
    			}
    			else	//this else is executed when we saw one or more doms before and none after
    			{
    				while (previousDoms.hasNext()) 
    				{
    					previousDom = (UtDom) previousDoms.next();
					doms.add(previousDom.id, previousDom.controller, previousDom.location, false, false);
    				}
    			}
    		}
	}//end of processDoms


	private void processFlags() throws BeliefSetException
	{
    		Iterator previousFlags, currentFlags;	
    		UtFlag previousFlag, currentFlag;
		boolean found;
	
    		if (prevSync.flags != null)	//check whether any flags were seen before
    		{
    			previousFlags = prevSync.flags.iterator();   //extract the previous flag list
    			if (currSync.flags != null)			//check whether any flags can be seen at the moment
    			{
				/* ********************** reset any previous unseen flags   *********************************/
    				while (previousFlags.hasNext()) 		//for each previous flag seen				
    				{
					previousFlag = (UtFlag) previousFlags.next();	//extract the flag from the list
    
    					currentFlags = currSync.flags.iterator();	//extract the current flag list
					
    					found = false;
    				    
				    	while (currentFlags.hasNext() &&  found == false)		//for each current flag seen
					{
						currentFlag = (UtFlag) currentFlags.next();	//extract the flag from the list
						if(previousFlag.id.equals(currentFlag.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other flag is gone  
					{
						flags.add(previousFlag.id, previousFlag.location, previousFlag.holder, previousFlag.team, false, previousFlag.state, false);
					}
    				}
				/* ********************** add the currently seen flags   *********************************/
				currentFlags = currSync.flags.iterator();	//extract the current flag list
    				while (currentFlags.hasNext()) 
    				{
    					currentFlag = (UtFlag) currentFlags.next();
					flags.add(currentFlag.id, currentFlag.location, currentFlag.holder, currentFlag.team, currentFlag.reachable, currentFlag.state, true);
    				}
    			}
    			else	//this else is executed when we saw one or more flags before and none after
    			{
    				while (previousFlags.hasNext()) 
    				{
    					previousFlag = (UtFlag) previousFlags.next();
					flags.add(previousFlag.id, previousFlag.location, previousFlag.holder, previousFlag.team, false, previousFlag.state, false);
    				}
    			}
    		}
	}//end of processFlags


	private void processInvs() throws BeliefSetException
	{
    		Iterator previousInvs, currentInvs;	
    		UtInv previousInv, currentInv;
		boolean found;
	
    		if (prevSync.invs != null)	//check whether any invs were seen before
    		{
    			previousInvs = prevSync.invs.iterator();   //extract the previous inv list
    			if (currSync.invs != null)			//check whether any invs can be seen at the moment
    			{
				/* ********************** reset any previous unseen invs   *********************************/
    				while (previousInvs.hasNext()) 		//for each previous inv seen				
    				{
					previousInv = (UtInv) previousInvs.next();	//extract the inv from the list
    
    					currentInvs = currSync.invs.iterator();	//extract the current inv list
					
    					found = false;
    				    
				    	while (currentInvs.hasNext() &&  found == false)		//for each current inv seen
					{
						currentInv = (UtInv) currentInvs.next();	//extract the inv from the list
						if(previousInv.id.equals(currentInv.id))	//check whether the previous ID and current ID match
					    		found = true;	//we have found a match
					}    				    
					
					if(found == false)   //if we did not find a match then it means the other inv is gone  
					{
						invs.add(previousInv.id, previousInv.location, false, previousInv.invClass, false);
					}
    				}
				/* ********************** add the currently seen invs   *********************************/
				currentInvs = currSync.invs.iterator();	//extract the current inv list
    				while (currentInvs.hasNext()) 
    				{
    					currentInv = (UtInv) currentInvs.next();
					invs.add(currentInv.id, currentInv.location, currentInv.reachable, currentInv.invClass, true);
    				}
    			}
    			else	//this else is executed when we saw one or more invs before and none after
    			{
    				while (previousInvs.hasNext()) 
    				{
    					previousInv = (UtInv) previousInvs.next();
					invs.add(previousInv.id, previousInv.location, false, previousInv.invClass, false);
    				}
    			}
    		}
	}//end of processInvs

}
