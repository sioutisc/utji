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

================================================
UTJackInterface: Interfacing JACK agents with UT
================================================

UTJackInterface (utji) provides a framework for a JACK agent to control
a Bot within Unreal Tournament (UT), the very first version of UT, or UT99.

Although you can probably find UT99 very cheap these days, you do not actually
need it unless you want to join the game and blast away at your Agent. Simply
do a google search for ut-server-436.tar.gz (for linux only) and use that.

You need a JACK license to build this code. 
See http://www.agent-software.com.au for more info.

You also need to configure and use the gamebots plugin with UT: 
See http://gamebots.sourceforge.net for more info.

UtJackInterface builds upon "javabots" a java client for gamebots
See http://utbot.sourceforge.net for more info. 
A file containing javabots called "javabots.jar" is already included
with utji in the lib directory.

The UTJackInterface source code is built using an Apache Ant script
for ease. See http://code.google.com/p/simplejt for more info.

To compile 
==========
Ensure you have Apache Ant installed and set the CLASSPATH  to include the
following files:.
- jack.jar
- ant-jackuild.jar
- javabots.jar

To run
=======

Add the generated file utji.jar in the CLASSPATH and try the two examples.

java edu.unisa.gamebots.examplebot.Main agentname gamebots_server

or

java edu.unisa.gamebots.explorer.Main agentname gamebots_server


