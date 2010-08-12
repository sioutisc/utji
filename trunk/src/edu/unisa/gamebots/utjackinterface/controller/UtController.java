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

import edu.unisa.gamebots.utjackinterface.*;
import edu.isi.gamebots.client.*;
import java.net.*;
import java.util.*;
import java.io.*;
import us.ca.la.anm.util.io.*;


/** This class implements a bot. The idea is that a UtController object handles all the 'bot controlling' stuff while an external application does all the thinking and uses the UtController.
 *
 * To understand this, a good analogy is a HUMAN driving a CAR. The driver's compartment provides interface for driving the car much like UtController provides an interface for moving in UT.
 *
 * @author Christos Sioutis
 */
public class UtController extends Bot {
    /** The gamebots client object
     */
    UtControllerClient utc;
    /** the DebugLog object
     */
    UtControllerDebugLog ucbdb;
    /** A boolean value indicating whether the bot has initialised the connection.
     *
     * Used by connect method, DO NOT CHANGE IT, you do not need to.
     */
    boolean didInit = false;
    /** A boolean value indicating whether the bot has recieved an initial sync message block
     *
     * Used by connect method, DO NOT CHANGE IT, you do not need to.
     */
    boolean gotFirstSync = false;
    /** The actual sync messageblock recieved
     */
    private MessageBlock recievedScMsg;
    /** The actual async message recieved
     */
    private Message recievedAscMsg;
    /** The SyncInfo object containing the latest information that came from the server. Note..this is updated every 10ms or so when a new set of data arrives. May need to consider synchronisation.
     */
    private UtSyncInfo latestSyncInfo;
    /** The AsyncInfo object containing the latest async message that came from the server. Note..this is updated every time a new set a new message is recieved. May need to consider synchronisation.
     */
    private UtAsyncInfo latestAsyncInfo;
    
    /** The INFO message that details information about the current game
     */
    public UtAsyncInfo gameInfo;
    
    /** Used to block the agent initialisation until UtController has recieved the first sync message.
     */
    private UtSemaphore mutex;
    
    /** A handle to the JACK interface view, used only when it has been initialised by a JACK application.
     */
    UtJackView jack = null;
    
    /** Constructs and connects new bot with the given name and team number.
     *
     * @param bn The name of the bot
     * @param ti The team of the bot
     */
    public UtController(String agentName, int agentTeam) {
        super();
	initialize(agentName,agentTeam);
    }
    
    public UtController(String agentName, int agentTeam, int port) {
        super();
	initialize(agentName,agentTeam);
	utc.setServerPort(port);
    }
    
    private void initialize(String agentName, int agentTeam) {
        name = agentName;
        initialTeam = agentTeam;
        ucbdb = new UtControllerDebugLog();
        mutex = new UtSemaphore(0);
        utc= new UtControllerClient();
        utc.setBot(this);
        client = utc;
    }

    
    /** Used by a JACK application to set the internal JACK handle. The handled is initialised to NULL be default.
     *
     * @param view The JACK view
     */
    public void setJackView(UtJackView view) {
        jack = view;
    }
    
    /** Used to connect to a UT gamebots server
     *
     * @param serverName The ip/network_name of the UT server
     * @return true if connection was successful
     */
    public boolean connectToServer(String serverName) {
        boolean status = true;
        //resolving the remote host name
        try {
            utc.setServerAddress(InetAddress.getByName(serverName));
            ProgressMessage.messageOk("Resolving remote host");
        }
        catch(UnknownHostException e) {
            //did not connect
            status=false;
            ProgressMessage.messageFailed("Resolving remote host");
        }
        
        
        if (status) {
            
            //used for BotRunnerApp only ***
           // utc.init(botName, gc, teamId, (DebugLog) ucbdb);
            // ***
            
            
            status = myConnect();
            if (status)
                ProgressMessage.messageOk("Connecting to UT server");
            else
                ProgressMessage.messageFailed("Connecting to UT server");
        }
        
        if(status) //waiting for the connection to be initialised by recieving the first sync message
        {
            System.out.print("Waiting for the bot to initialise connection...");
            mutex.down(); //waits for a signal
        }
        
        return status;
    }
    
    /** This connect method does some additional tasks than the original connect method. THerefore it is used instead. Use connectToServer instead for an easier way.
     *
     * @return true if connected
     * @see connectToServer
     */
    private boolean myConnect() {
        boolean status=true;
        try {
            utc.connect();
        }
        catch( IOException error ) {
            status=false;
            ucbdb.logError( error );
            ucbdb.flush();
        }
        return(status);
    }
    
    
    /** The callback method when a sync message has been recieved
     *
     * @param message the messageblock that was recieved
     */
    public void receivedSyncMessage(MessageBlock message) {
        recievedScMsg = message;
        latestSyncInfo = new UtSyncInfo(recievedScMsg);
        
        //wake the connectToServer method if this is the first sync block recieved
        if(gotFirstSync == false) {
            System.out.println("done");
            mutex.up();
            gotFirstSync = true;
        }
        else {
            processSyncMessage(latestSyncInfo);
        }
    }
    
    /** Subclasses need to override this method in order to define what happens when a new message is recieved */
    public void processSyncMessage(UtSyncInfo newSyncInfo){
            if (jack != null)
                jack.processSyncMessage(newSyncInfo);        
    }
    
    /** The callback method when an async message has been recieved
     *
     * @param message the message that was recieved
     */
    public void receivedAsyncMessage(Message message) {
        if( didInit && gotFirstSync == true) {
            recievedAscMsg = message;
            latestAsyncInfo = new UtAsyncInfo(message);
            processAcyncMessage(latestAsyncInfo);
        }
        else if( message.getType().equals( INFO ) ) {
            // Init
            latestAsyncInfo = new UtAsyncInfo(message);
            gameInfo = latestAsyncInfo;  //save the game info into a public variable for later use as required
            
            //INIT {Name chris} {Team teamId}
            String tempst ="INIT" + " {Name "+ name +"}"  +" {Team "+ initialTeam +"}";
            didInit = true;
            
            //sending the INIT message
            utc.sendMessage(tempst);
            
        }
    }
    
    /** Subclasses need to override this method in order to define what happens when a new message is recieved */
    public void processAcyncMessage(UtAsyncInfo newAsyncInfo){
        if (jack != null)
            jack.processAsyncMessage(newAsyncInfo);        
    }
    
    
    /** Prints a message when the bot is destroyed
     */
    public void destroyImpl() {
        System.out.println("Bot " + getName() + " destroyed by user's request");
    }
    
    /** prints a message when the bot is disconnected
     */
    public void disconnected() {
        System.out.println("Bot " + getName() + " disconnected from UT server");
    }
    
    /** Prints a message when an error is recieved
     *
     * @param error the error recieved
     */
    public void recievedError(Throwable error) {
        System.out.println("Bot " + getName() + " recieved the error: ");
        System.out.println(error);
        System.out.println("******************************************");
    }
    
    public static void main(String args[]){
        boolean connected = false;
	UtController ut;
        if(args.length > 1)        
		ut = new UtController("chris",0,Integer.parseInt(args[1]));
	else
		ut = new UtController("chris",0);
        if(args.length > 0)        
            connected = ut.connectToServer(args[0]);
        else
            connected = ut.connectToServer("localhost");
        
        if(connected ==false)
            System.exit(1);
    }
    
}
