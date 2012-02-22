/*
 * Output atteso:
 * dt: 563	(1014:ID574:STAZID70:TIME795)-->(2321:ID1705:STAZID70:TIME810)-->(2333:ID1715:STAZID70:TIME810)-->(2335:ID1716:STAZID83:TIME815)-->(5250:ID4267:STAZID83:TIME820)-->(1433:ID937:STAZID83:TIME845)-->(1434:ID938:STAZID72:TIME850)-->(1435:ID939:STAZID9:TIME860)-->(2537:ID1900:STAZID9:TIME860)-->(6304:ID5195:STAZID9:TIME860)-->(6305:ID5196:STAZID8:TIME868)-->(6306:ID5197:STAZID7:TIME873)-->(6307:ID5198:STAZID5:TIME878)-->(6308:ID5199:STAZID4:TIME883)-->(6309:ID5200:STAZID53:TIME888)-->(6310:ID5201:STAZID95:TIME890)-->(6311:ID5202:STAZID1:TIME893)
 * 
(1014:ID574:STAZID70:TIME795)-->(1017:ID575:STAZID71:TIME800)-->(5251:ID4268:STAZID71:TIME823)-->(5252:ID4269:STAZID72:TIME825)-->(5253:ID4270:STAZID3:TIME830)-->(594:ID215:STAZID3:TIME833)-->(451:ID87:STAZID3:TIME840)-->(3755:ID2970:STAZID3:TIME843)-->(3756:ID2971:STAZID15:TIME850)-->(4051:ID3222:STAZID15:TIME850)-->(4362:ID3488:STAZID15:TIME850)-->(4859:ID3923:STAZID15:TIME850)-->(5932:ID4866:STAZID15:TIME850)-->(1703:ID1167:STAZID15:TIME855)-->(1706:ID1168:STAZID55:TIME858)-->(1707:ID1169:STAZID54:TIME865)-->(WALK: t:11|arrtime:876|distwalk:0.9406955539146882|numchanges:3)

(1014:ID574:STAZID70:TIME795)-->(1017:ID575:STAZID71:TIME800)-->(5251:ID4268:STAZID71:TIME823)-->(5252:ID4269:STAZID72:TIME825)-->(5253:ID4270:STAZID3:TIME830)-->(594:ID215:STAZID3:TIME833)-->(451:ID87:STAZID3:TIME840)-->(452:ID88:STAZID4:TIME845)-->(4369:ID3493:STAZID4:TIME857)-->(6852:ID5665:STAZID4:TIME870)-->(6853:ID5666:STAZID53:TIME875)-->(6854:ID5667:STAZID2:TIME877)-->(6855:ID5668:STAZID95:TIME879)-->(WALK: t:11|arrtime:890|distwalk:0.9818784824271852|numchanges:3)

(1014:ID574:STAZID70:TIME795)-->(1017:ID575:STAZID71:TIME800)-->(5251:ID4268:STAZID71:TIME823)-->(5252:ID4269:STAZID72:TIME825)-->(5253:ID4270:STAZID3:TIME830)-->(594:ID215:STAZID3:TIME833)-->(451:ID87:STAZID3:TIME840)-->(452:ID88:STAZID4:TIME845)-->(4369:ID3493:STAZID4:TIME857)-->(6852:ID5665:STAZID4:TIME870)-->(6853:ID5666:STAZID53:TIME875)-->(WALK: t:9|arrtime:884|distwalk:0.7577758502916099|numchanges:3)

(1014:ID574:STAZID70:TIME795)-->(2321:ID1705:STAZID70:TIME810)-->(2333:ID1715:STAZID70:TIME810)-->(2335:ID1716:STAZID83:TIME815)-->(5250:ID4267:STAZID83:TIME820)-->(1433:ID937:STAZID83:TIME845)-->(1434:ID938:STAZID72:TIME850)-->(1435:ID939:STAZID9:TIME860)-->(2537:ID1900:STAZID9:TIME860)-->(6304:ID5195:STAZID9:TIME860)-->(6305:ID5196:STAZID8:TIME868)-->(6306:ID5197:STAZID7:TIME873)-->(6307:ID5198:STAZID5:TIME878)-->(6308:ID5199:STAZID4:TIME883)-->(6309:ID5200:STAZID53:TIME888)-->(6310:ID5201:STAZID95:TIME890)-->(6311:ID5202:STAZID1:TIME893)-->(WALK: t:0|arrtime:893|distwalk:0.0013822123004936543|numchanges:2)

 */

package test;

import domain.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import json.Directions;
import myShortest.StopMediator;
import myShortest.myShortest;
import myShortest.myShortestGeo;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Expander;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.kernel.Traversal;


/**
 *
 * @author rashta
 */
public class ShortestPath8 {
		private static GraphDatabaseService db;
    

        public static void main(String[] args) {     
    		DbConnection.createEmbeddedDbConnection();
    		db = DbConnection.getDb();
    		
            int time = 670;     //9h00
            Station s1 = Stations.getStations().getStationById(70);
            Station s2 = Stations.getStations().getStationById(1);
                
            System.out.print("\ns1: " + s1);
            System.out.print("\ns2: " + s2);
            
//            shortestpath.BreadthTraverser.shortestPath(s1, s2, time);
//            Path foundPath = shortestpath.ShortestPath.shortestPath(s1, s2, time);
//            
//                
//            if(foundPath != null){
//            System.out.println( "ShortestPath: "
//                                + Traversal.simplePathToString( foundPath) );
//            } else {
//            System.out.println( "ShortestPath: NULL");
//
//            }
            Stop firstStop = s1.getFirstStopsFromTime(time);
            int prevTime = time;
            
                
                
                //System.out.print(firstStop);
//                myShortest mysp = new myShortest(firstStop, s2, 1440);
//                myShortestGeo mysp = new myShortestGeo(firstStop, s2, 1440);
  
        	double lat1 = 42.3799;
        	double lon1 = 13.298555;
        	
        	double lat2 = 42.34300;
        	double lon2 = 13.46300;

        	myShortestGeo mysp = new myShortestGeo(time, 1400, lat1, lon1, lat2, lon2, 1000);
            mysp.shortestPath(); 
            json.Directions directs = mysp.getDirections();
            for(json.Direction d : directs.getDirectionsList()){
            	System.out.print(d);
            }
            
            

                
//                Stop arrivo = mysp.getShortestPath();
                //System.out.print("\n\nSTOP ARRIVO" + arrivo);
//                System.out.print("\n-------\ndt: " + (arrivo.getTime() - prevTime));


                System.out.println( "\n\n" + mysp.toString());
//                String outPath = "";
//                for(Stop s : mysp.getWeightedPath()){
//                    outPath = "(" + s.getUnderlyingNode().getId() + ":ID" + s.getId()  + ":STAZID" + s.getStazione().getId() + ":TIME" + s.getTime() + ")-->" + outPath;                
//
//                }
                

                
                

//            Stop arrivo = s2.getFirstStopsFromTime(time);
//            
//            while(arrivo != null && !cache.check(arrivo)){
//                arrivo = arrivo.getNextInStation();
////                System.out.println( "\n\nNot reachable node: " + arrivo);
//            }
//
//            arrivo = cache.get(arrivo);
//            while(arrivo != null){
//                arrivo = cache.get(arrivo);
//
//                System.out.println("\n\n#cambi: " + arrivo.numeroCambi);
//
//                String outPath = "(" + arrivo.getUnderlyingNode().getId() + ":ID" + arrivo.getId() + ":STAZID" + arrivo.getStazione().getId() + ":TIME" + arrivo.getTime() + ")";
//                
//
//                Stop arr = arrivo;
//                while(!arr.equals(firstStop)){
//                    arr = arr.prevSP;
//                    outPath = "(" + arr.getUnderlyingNode().getId() + ":ID" + arr.getId()  + ":STAZID" + arr.getStazione().getId() + ":TIME" + arr.getTime() + ")-->" + outPath;                
//                }
//
//                System.out.println( "myShortestPath: " + outPath);
//
//   
//                do{
//                    arrivo = arrivo.getNextInStation();   
//                    if(arrivo != null)
//                        arrivo = cache.get(arrivo);
//                } while(arrivo != null && arrivo.prevSP == null);
//            }
//            
            
            DbConnection.turnoff();
        }
    
    
}
