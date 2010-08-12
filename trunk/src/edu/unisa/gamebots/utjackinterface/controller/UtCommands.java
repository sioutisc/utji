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


public interface UtCommands
{
	public void setWalk(boolean walk);
	public void stop();
	public void jump();
	public void runTo(String target);
	public void runTo(UtCoordinate location);
	public void strafe(UtCoordinate location, String target);
	public void turnTo(UtCoordinate position);
	public void turnToLoc(UtCoordinate location);
	public void rotate(double amount);
	public void shoot();
	public void shoot(UtCoordinate location, String target, boolean alt);
	public void shoot(String target, boolean alt);
	public void changeWeapon(String id);
	public void stopShoot();
	public void checkReach(String id, UtCoordinate from);
	public void checkReach(String target, String id);
	public void getPath(UtCoordinate location, String id);
	public void say(String msg, boolean global);
      
    //used instead of System.exit for a more clean system.
        public void setFinishedTrue(boolean mainGoalAchieved);
}
