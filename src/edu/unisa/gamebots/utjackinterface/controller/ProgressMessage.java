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


/** This class is used to display a message to the user with an indicator showing an "OK" or "FAILED" message. This functionality is useful when showing the a user that different stages in an task have been successful or failed.
 * 
 * @author Christos Sioutis
 */
public abstract class ProgressMessage
{
	/** Use this method to print out a message with an "OK" status on the end.
	 * 
	 * @param message the message to display
	 */
	public static void messageOk(String message)
	{
		StringBuffer st = new StringBuffer("                                                           [       ]");		
		st.replace(0,message.length(),message);
		st.replace(60,67,"  OK  ");
		System.out.println(st);
		
	}
	
	/** Use this method to print out a message with an "FAILED" status on the end.
	 * 
	 * @param message the message to display
	 */
	public static void messageFailed(String message)
	{
		StringBuffer st = new StringBuffer("                                                           [       ]");		
		st.replace(0,message.length(),message);
		st.replace(60,67,"FAILED");
		System.out.println(st);
	}
        
        public static void messageInfo(String message){
            System.out.println("["+message+"]");
        }
        
        public static void messageUniSALearning(){
            System.out.println("[Uses UtJackInterface, Copyright 2002-2010, Christos Sioutis");
        }
        
	
	/** This main method is used for testing the class's code. It is not used by another application making use of this class.
	 * 
	 * Expected arguments: [0,1] message
	 * this refers to:    [OK,FAILED] message
	 * 
	 * @param args command line arguments
	 */
	public static void main(String args[])
	{
		if (args.length > 0)
		{
			if(args[0].equals("0"))
				ProgressMessage.messageFailed(args[1]);
			else if(args[0].equals("1"))
				ProgressMessage.messageOk(args[1]);
			else
				System.out.println("Progress Message Test: Usage -  '[0,1] Message' without the square brackets");
		}
	}

}
