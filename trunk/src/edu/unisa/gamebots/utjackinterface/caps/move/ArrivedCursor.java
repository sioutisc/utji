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

package edu.unisa.gamebots.utjackinterface.caps.move;
import aos.jack.util.cursor.Change;
import aos.jack.jak.beliefset.BeliefSetException;
import edu.unisa.gamebots.utjackinterface.controller.*;
import edu.unisa.gamebots.utjackinterface.caps.sync.*;

public class ArrivedCursor extends Change {
    Self self;
    UtCoordinate location;
    public ArrivedCursor(Self s, boolean test, UtCoordinate loc) {
        super(s, test);
        self=s;
        location=loc;
    }
    
    public boolean condition() {
        try {
            if(self.amIHere(location))
                return true;
            return false;
        }
        catch (BeliefSetException e) {
            System.out.println("Error accessing the SELF beliefset within ArrivedCursor, returning Arrived=TRUE to continue processing");
            
            return true;
        }
    }
}
