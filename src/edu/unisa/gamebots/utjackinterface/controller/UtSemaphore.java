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

// Note: This code is based from an example Semaphore class obtained - 
// from http://www.dcs.napier.ac.uk/~shaun/rtse/labs/lab04.html

public class UtSemaphore 
{
	private int mutex;
	
	public UtSemaphore(int n)
	{
		if(n < 0)
			mutex = 0;  //a semaphore cannot be negative
		else
			mutex = n;
	}

	public synchronized void down()
	{
		while(mutex == 0)
		{
			try {wait();} catch (InterruptedException e) {System.out.println("Error: cannot wait when using ut semaphore");}
		}
		mutex--;
	}
	
	public synchronized void up()
	{
		mutex++;
		notify(); //alert a thread that's blocking on this semaphore
	}
        
        public synchronized int value(){
            return mutex;
        }
}
