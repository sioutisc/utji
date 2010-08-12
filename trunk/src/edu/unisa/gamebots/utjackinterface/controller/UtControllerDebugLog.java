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

import us.ca.la.anm.util.io.*;

//do not support loggin at this stage

/** Used mainly so that we can create UtControllerClient objects. The logging methods simple print messages to the screen
 * 
 * @author Christos Sioutis
 */
public class UtControllerDebugLog implements DebugLog
{
	
	/** Logging a note
	 * 
	 * @param message the note to log
	 */
	public void logNote( Object message )
	{
		//System.out.println("log note: debug");
	}
	
	/** 
	 * Appends the error message object to the log.
	 * 
	 * @param message the message to log
	 */
	public void logError( Object message )
	{
		//System.out.println("log error: debug");
	}
	
	/** 
	 * Appends the error message object and error to the log.  Implementations
	 * may withhold autoflushing on this method in order to group messages of the
	 * same error.  Therefore, developers are encouraged to call {@link #flush()}
	 * if the Throwable is not going to be thrown again after this method, it is
	 * not going to be thrown again.
	 * 
	 * @param message the message to log
	 * @param error the type of error
	 */
	public void logError( Object message, Throwable error )
	{
		//System.out.println("log error: debug");
	}
	
	/** 
	 * Flushses all output.  It is a good thing to call between errors.
	 */
	public void flush()
	{
		//System.out.println("log flush: debug");		
	}
}
