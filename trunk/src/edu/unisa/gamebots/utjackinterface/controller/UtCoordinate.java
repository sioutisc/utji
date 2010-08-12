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

/** Used to hold an absolute coordinate in UT
 * 
 * @author Christos Sioutis
 */
public class UtCoordinate implements Immutable, Serializable
{
	/** The x coordinate
	 */
	public double x;
	/** The y coordinate
	 */
	public double y;
	/** The z coordinate
	 */
	public double z;
	
	/** Initialises the object using values from the parameters.
	 * 
	 * @param px the x value
	 * @param py the y value
	 * @param pz the z value
	 */
	public UtCoordinate(double px, double py, double pz)
	{
		x=px;
		y=py;
		z=pz;
	}
	
	/** Initialises the object by parsing a string containing coordinate values in the form of: {"double" + "," + double + "," + double} where these correspond to {x,y,z}
	 * 
	 * @param strCoordinate the coordinate in String format
	 */
	public UtCoordinate(String strCoordinate)
	{
		StringTokenizer st = new StringTokenizer(strCoordinate, ",");
		x = Double.parseDouble(st.nextToken());
		y = Double.parseDouble(st.nextToken());
		z = Double.parseDouble(st.nextToken());		
	}
	
	
	public boolean equalTo(UtCoordinate other)
	{
		if (x == other.x && y == other.y && z == other.z)
			return true;
		
		return false;
	}


	public double distanceFrom(UtCoordinate other)
	{
		double magX = other.x - x;
		double magY = other.y - y;
		double magZ = other.z - z;
		return(Math.sqrt(Math.pow(magX,2) + Math.pow(magY,2) +Math.pow(magZ,2)));  // |v|=sqrt(x^2+y^2+z^2)
	}
	
	public String toString()
	{
		return(x+","+y+","+z);
	}
	public boolean closeTo(UtCoordinate other)
	{
		double diffX = x - other.x;
		double diffY = y - other.y;
		double diffZ = z - other.z;
		boolean closeX = false, closeY = false, closeZ = false;
		
		//System.out.println("--> X=" + diffX + ", Y=" + diffY + ", Z=" + diffZ);
		
		//checking x
		if (diffX < 30 && diffX > -30)
		{
			closeX = true;
		}

		if (diffY < 30 && diffY > -30)
		{
			closeY = true;
		}

		if (diffZ < 60 && diffZ > -60)	//should be 30, have changed to 60 to overcome intermediate inaccuracy due to continuous jumping
		{
			closeZ = true;
		}
		
		//return false;
		return (closeX && closeY && closeZ);
	}
        
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        UtCoordinate tmp = (UtCoordinate) obj;
        return equalTo(tmp);
    }
    
    /** returns a unique integer value depending on the contents state
     *   implementations need to override this method for the ValueFunction
     *  objects to work properly
     */    
    public int hashCode(){
        return (int)((x+y+z)*1000);
    }
	
}
