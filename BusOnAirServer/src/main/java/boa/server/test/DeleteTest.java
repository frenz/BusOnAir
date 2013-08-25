package boa.server.test;


import boa.server.domain.DbConnection;
import boa.server.domain.Station;
import boa.server.domain.Stations;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;



public class DeleteTest {


    public static void main(String[] args) {     
		DbConnection.createEmbeddedDbConnection();
        GraphDatabaseService db = DbConnection.getDb();
		//Stations.getStations().deleteAllStations();
		
//		Transaction tx = DbConnection.getDb().beginTx();
//		try{
//			//	Runs.getRuns().deleteRun(run);
//			Runs.getRuns().deleteRun(Runs.getRuns().getRunById(137));
//
//			tx.success();
//		}finally{
//			tx.finish();			
//		}    	
		
		
		Station s = Stations.getStations().getStationById(16);
//		Route route = Routes.getRoutes().getRouteById(7);
//		for(Run r : route.getAllRuns()){
		System.out.println(s);	
//		}

//		Route route = Routes.getRoutes().getRouteById(1);
//		Transaction tx = DbConnection.getDb().beginTx();
//		try{
//			//	Runs.getRuns().deleteRun(run);
//			Routes.getRoutes().deleteRoute(route);
//			tx.success();
//		}finally{
//			tx.finish();			
//		}    	

		
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			//	Runs.getRuns().deleteRun(run);
			Stations.getStations().deleteAllStations();
			tx.success();
		}finally{
			tx.finish();			
		}    	

		
		
//		
////		Run run = Runs.getRuns().getRunById(286);
//
//		Transaction tx = DbConnection.getDb().beginTx();
//		try{
////			Runs.getRuns().deleteRun(run);
//			Routes.getRoutes().deleteRoute(route);
//			tx.success();
//		}finally{
//			tx.finish();			
//		}    	

//		
//		for(Run r : route.getAllRuns()){
//			System.out.println(r);	
//		}
		
		DbConnection.turnoff();
	}

}
