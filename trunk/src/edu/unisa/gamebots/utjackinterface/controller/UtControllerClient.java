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
import java.io.*;
import java.net.*;
import java.util.*;
import edu.isi.gamebots.client.*;


/** This class was extended from the JavaBot GamebotsClient mainly to provide an enhanced message parser.
 *
 * @author Christos Sioutis
 */
public class UtControllerClient extends GamebotsClient {
    InetAddress serverAddress = null;
    Socket socket = null;
    Bot bot = null;
    BufferedReader input = null;
    OutputStream output = null;
    int serverPort = UtControllerConstants.DEFAULT_BOT_PORT;
    
    /** simply calls the super constructor
     */
    public UtControllerClient() {
        super();
    }
    
    public InetAddress getServerAddress() {
        return serverAddress;
    }
    
    public void setServerAddress( InetAddress serverAddress ) {
        this.serverAddress = serverAddress;
    }
    
    
    public int getServerPort() {
        return serverPort;
    }
    
    public void setServerPort( int port ) {
        this.serverPort = port;
    }
    
    public boolean isConnected() {
        if(socket != null)
            return socket.isConnected();
        return false;
    }
    
    public void connect() throws IOException{
        if( !isConnected() ) {
            if( serverAddress == null )
                throw new IllegalStateException( "Server address not set" );
            socket = new Socket( serverAddress, serverPort );
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = socket.getOutputStream();
            
            start();
        }
    }
    
    public boolean sendMessage(String msg){
        try{
            if(output == null) return false;
            output.write(msg.concat("\r\n").getBytes());
            output.flush();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }
    
    
    public boolean sendMessage( String type, Properties properties ) {
        StringBuffer buffer = new StringBuffer( type );
        
        if(properties != null){
            java.util.Map.Entry entry;
            Iterator i = properties.entrySet().iterator();
            while( i.hasNext() ) {
                buffer.append( ' ' );
                entry = (java.util.Map.Entry) i.next();
                buffer.append( " {"+entry.getKey()+" "+entry.getValue()+"}" );
            }
        }
        return sendMessage(buffer.append("\r\n").toString());
    }
    
    public void ping() {
        sendMessage("PING");
    }
    
    public void disconnect() {
        try{
            socket.shutdownOutput();
            socket.close();
        }
        catch(IOException e){
            System.out.println("Error when disconnecting");
        }
    }
    
    public void setBot(Bot bot){
        this.bot = bot;
    }
    
    public void run() {
        Message message;
        MessageBlock syncMessage;
        Iterator i;
        try {
            
            MESSAGE_LOOP: while( socket != null) {
                message = parseMessage(input);
                if( message == null )
                    break MESSAGE_LOOP;
                
                if( message instanceof MessageBlock ) {
                    syncMessage = (MessageBlock) message;
                    
                    if(bot != null)
                        bot.receivedSyncMessage( syncMessage );
                } else {
                    
                    if(bot != null)
                        bot.receivedAsyncMessage( message );
                    if( message.getType().equalsIgnoreCase( UtControllerConstants.FINISHED ) )
                        break MESSAGE_LOOP;
                }
                Thread.currentThread().yield();
            }
        } catch( Exception error ){
            if(bot != null)
                bot.disconnected();
            else
                System.out.println("Bot has been disconnected");
        } finally {
            try {
                if( socket != null )
                    socket.close();
            } catch ( IOException error ) {
                // ignore...
            }
            try {
                socket.close();
            } catch ( IOException error ) {
                // ignore...
            }
        }
    }
    
    
    
    
    /** This parser ovverides the gamebostclient parser. It reads a whole message at a time instead of individual characters. It then uses the new parseProperties method to parse the properties for each line.
     *
     * @exception IOException when the bot is disconnected from the server
     * @param in a String message recieved from UT, it can be an individual
     *     message (async message) or a block of messages (sync messageblock)
     * @return a Message object with all relavant info parsed into it.
     */
    protected Message parseMessage(Reader in) throws IOException {
        StringTokenizer st;
        String msgType;
        String strLine;
        
        Properties props = null;
        boolean complete = false;
        boolean isVoiceMessage = false;
        
        //for sync messages
        List messages = new LinkedList();
        
        Message toReturn = null;
        
        try {
            BufferedReader br = (BufferedReader) in;
            strLine = br.readLine();
            
            if (strLine.endsWith("}"))
                complete = true;
            
            //text-based voice messages have an unpredictable number of space separated tokens and therefore need to be parsed differently
            if(strLine.startsWith(UtControllerConstants.VOICE_MSG) || strLine.startsWith(UtControllerConstants.VOICE_MSG_TEAM))
                isVoiceMessage = true;
            
            st = new StringTokenizer(strLine, "{");  //read a line from the stream and tokenize it using '{' character
            
            msgType = st.nextToken().trim();
            
            if (msgType.equals(UtControllerConstants.BEG)) //this is a sync message, decode each part in a loop
            {
                //read the first line of syn block
                strLine = br.readLine();
                st = new StringTokenizer(strLine, "{");
                msgType = st.nextToken().trim(); //get the message type which is the 1st token of the line
                while (!msgType.equals(UtControllerConstants.END)) {
                    props = parseProperties(st, isVoiceMessage);
                    messages.add(new Message(this, msgType, props, complete));
                    
                    //read the next line
                    strLine = br.readLine();
                    st = new StringTokenizer(strLine, "{");
                    msgType = st.nextToken().trim(); //get the message type which is the 1st token of the line
                }
                toReturn = new MessageBlock(this, UtControllerConstants.BEG, props, messages, complete);
            }
            else {
                //System.out.println("numtokens="+st.countTokens());
                props = parseProperties(st, isVoiceMessage);
                toReturn = new Message(this, msgType, props, complete);
            }
            
        }catch(Exception e){
            throw new IOException("Invalid input when parsing information");
        }
        return(toReturn);
    }
    
    
    /** This method recieves a line of text corresponding to a single message recieved from the UT server.
     * It is parses the line into key-value pairs and adds them into a Properties object which is returned at the end.
     * This method is enhanced from the original gamebotsclient parser because it supports propery lists and pathnodes. The gamebotsclient parser does not handle these correctly. See the detailed method info for more info on these.
     *
     * The property lists as parsed as:
     * key = "propertyListName" + "@" + "propertyKey";
     * value = propertyValue;
     * The pathnodes are parsed as:
     * key = pathNodeIndex;
     * value = "pathNodeId" + "&" + "pathNodeLocation";
     *
     * @param st the message recieved from UT as a string
     * @return a Properties object containing key-value pairs corresponding to UT
     *      properties
     */
    private Properties parseProperties(StringTokenizer st, boolean isVoiceMessage) {
        //the properties were trying to create
        Properties props = new Properties();
        
        //properties temporary variables
        String key;
        String value;
        String value2;
        String[] prop;
        String tmp;
        
        //used for multi valued properties
        boolean bMultiProp = false;
        String strMultiProp = null;
        
        
        while(st.hasMoreTokens()) {
            if(isVoiceMessage) {
                tmp = st.nextToken();
                key = UtControllerConstants.STRING;
                value = tmp.substring(UtControllerConstants.STRING.length()+1, tmp.length()-1);
                props.setProperty(key,value);
                return(props);
            }
            
            prop = st.nextToken().trim().split(" "); //get the property which could be several values such as a PlayerScores property
            if(prop.length == 2) {
                //this is a key-property pair
                key = prop[0].trim();  //get the property key
                value = prop[1].trim(); //get the property value
                if(bMultiProp == true) {
                    if(value.endsWith("}}"))  //this is the end of a property with a list of key-value pairs
                    {
                        value = value.substring(0,value.length()-2); //remove the '}}' from the end of the property value
                        props.setProperty(strMultiProp+"@"+key,value); //add to the properties list
                        bMultiProp = false;
                    }
                    else  //intermediate of multi key-value property list
                    {
                        value = value.substring(0,value.length()-2); //remove the '}}' from the end of the property value
                        props.setProperty(strMultiProp+"@"+key,value); //add to the properties list
                    }
                }
                
                else {
                    value = value.substring(0,value.length()-1); //remove the '}' from the end of the property value
                    props.setProperty(key,value); //add to the properties list
                }
            }
            else if(prop.length == 1) {
                //this is a key only, which means that a list of key-value pairs follow.
                bMultiProp = true;
                strMultiProp = prop[0].trim();  //the key corresponding to the following key-value pairs
                
            }
            else if(prop.length == 3) //used for pathnodes
            {
                key = prop[0].trim();  	//get the pathnode index
                value = prop[1].trim(); //get the pathnode id
                value2 = prop[2].trim().substring(0,value.length()-1); //remove the '}' from the end of the pathnode location
                props.setProperty(key, value+"&"+value2); //add to the properties list
            }
        }//while loop
        return(props);
    }//parseProperties
    
    
}
