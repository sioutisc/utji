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
 
package edu.unisa.gamebots.domdefender.caps.explore2;

import java.util.*;
import java.io.*;
import edu.unisa.gamebots.utjackinterface.controller.*;

public class WorldMap
{	
	UtCoordinate root = null;
	UtNavList explored;
	UtNavList toExplore;
	UtNavList unexplored;
	boolean firstRepopulation = true;
	
	double rootRadius = 0.0;
	
	UtNav currentNode = null; //holds the current node being explored
	
	int EXPLORE_LIMIT = 5;
	
	//stores references to the checkboxes for easy access
	

	public synchronized void setRoot(UtCoordinate r)
	{
		if (root == null)
		{
			System.out.println("Setting initial location as root");
			root = r;
			explored = new UtNavList(root);
			toExplore = new UtNavList(root);
			unexplored = new UtNavList(root);
		}
	}
		
	public synchronized void addNav(UtNav n)
	{
		double tmp;
				
		if (root == null)
		{
			System.out.println("Setting "+ n.id + " as root location");
			root = n.location;
			explored = new UtNavList(root);
			toExplore = new UtNavList(root);
			unexplored = new UtNavList(root);
		}
		else
		{
			
//		tmp = root.distanceFrom(n.location);		 
//		if (!explored.containsNav(n) && !toExplore.containsNav(n) && !unexplored.containsNav(n))
//		{
			//System.out.println("Adding nav to explore list " + n.id);
			unexplored.add(n);
//		}
		}
		
		

	}

	
	public synchronized UtNav getNav()
	{
		UtNav tmp;
		if(toExplore.size() == 0)
		{
			if(!repopulateLists())
				return null;			
		}

		if(currentNode != null)
			explored.add(currentNode);

		currentNode = toExplore.get();
		return(currentNode);
	}
	
	
	public boolean repopulateLists()
	{
		NavListNode tmp;
		//System.out.println("***** DEBUG toexplore=" + toExplore.size()+ " unexplored="+unexplored.size());
		
		if(toExplore.size() == 0)
		{
			if(firstRepopulation)
				toExplore.setStart(root);
			else
				toExplore.setStart(currentNode.location);
			tmp = unexplored.getListNode();
			rootRadius = tmp.startDistance;
			while( unexplored.size() > 0 && toExplore.size() < EXPLORE_LIMIT)
				toExplore.add(unexplored.get());
		}
		if(toExplore.size() > 0)
			return true;
			
		return false;
	}
	
	//a sorted list of navs with respect to a start position
	class UtNavList
	{
		UtCoordinate root;
		UtCoordinate start;
		LinkedList list;
		public double minRadius;
		public double maxRadius;
		
		UtNavList(UtCoordinate r)
		{
			list = new LinkedList();
			root = r;
			start = root;
		}		
		
		
		public void setStart(UtCoordinate s)
		{
			start = s;
		}
		
		public UtNav get()
		{
			if (list.size() > 0)
			{
				UtNav toReturn = ((NavListNode)list.removeFirst()).nav;
				if (list.size() > 0) //check again because the previous line removes a list item
					updateRadiusInfo();
				return toReturn;
			}
			return null;
		}
		
		public NavListNode getListNode()
		{
			if (list.size() > 0)
			{
				NavListNode toReturn = (NavListNode) list.removeFirst();
				updateRadiusInfo();
				return toReturn;
			}
			return null;
		}
				
		public int size()
		{
			return list.size();
		}
		
		//adds a node by sorting it according to the distance from the start node
		public void add(UtNav nav)
		{
			int index = 0;
			double startDistance = start.distanceFrom(nav.location);
			
			if(list.size() == 0)
				list.add(new NavListNode(nav, startDistance));
			else
			{
				double tmp = ((NavListNode)list.get(index)).startDistance;
				for(index=0; index < list.size() && startDistance > tmp; index++)
				{
					tmp = ((NavListNode)list.get(index)).startDistance;
					
				}
				if (index == list.size()) //have i reached the end of the list?
					list.add(new NavListNode(nav, startDistance)); //add to the end of the list 
				else
					list.add(index, new NavListNode(nav, startDistance)); //insert within the list
			}
			updateRadiusInfo();
		}
				
				
		void updateRadiusInfo()
		{
			//System.out.println("****DEBUG2 " + minRadius + " "+ maxRadius);
			minRadius = ((NavListNode)list.getFirst()).startDistance;
			maxRadius = ((NavListNode)list.getLast()).startDistance;
		}
				
		public boolean containsNav(UtNav nav)
		{
			String tmp;
			int index = 0;
			double navDistance = root.distanceFrom(nav.location);
			
			if(navDistance < maxRadius && navDistance > minRadius)
			{
				while(index < list.size())
				{
					tmp = ((NavListNode)list.get(index)).nav.id;
					if(tmp.equals(nav.id))
						return true;
					index++;
				}
			}
			return false;
		}
	
	}

	class NavListNode
	{
		public UtNav nav;
		public double rootDistance;
		public double startDistance;
		NavListNode(UtNav n, double d)
		{
			nav = n;
			startDistance = d;
		}
	}

}



