package boa.server.plugin.backend;


import boa.server.domain.*;
import org.apache.commons.configuration.Configuration;
import org.neo4j.graphdb.Transaction;
import org.neo4j.server.database.Database;
import org.neo4j.server.rest.domain.JsonHelper;
import org.neo4j.server.rest.repr.OutputFormat;
import org.neo4j.server.webadmin.rest.SessionFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



@Path( "/backend" )
public class BackEndResource{
    private final Database database;
    private final Configuration config;
    private BufferedWriter log;

    public BackEndResource( @Context Database database,
            @Context HttpServletRequest req, @Context OutputFormat output, @Context Configuration config ) throws IOException 
    {
        this( new SessionFactoryImpl( req.getSession( true ) ), database,
                output, config );

        StringBuffer fullURL = req.getRequestURL();
        StringBuffer queryString = new StringBuffer();
        queryString.append(req.getQueryString());
        if(!queryString.toString().equals("null"))
        	fullURL.append("?").append(queryString);
        
        log.write("\nHttpServletRequest(" + fullURL.toString() +")");
        log.flush(); 
    }

    public BackEndResource( SessionFactoryImpl sessionFactoryImpl,
            Database database, OutputFormat output, Configuration config ) throws IOException
    {
        FileWriter logFile = new FileWriter("/tmp/trasportaqbackend.log");
        log = new BufferedWriter(logFile);
        this.database = database;
        this.config = config;
        DbConnection.createDbConnection(database);
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/runs/{id}/delete" )
    public Response deleteRun(@PathParam("id") Integer id) {

        Run run = Runs.getRuns().getRunById(id);

        if(run == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No run having the specified id value.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
	        Runs.getRuns().deleteRun(run);
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stations/{id}/delete" )
    public Response deleteStation(@PathParam("id") Integer id) {

        Station staz = Stations.getStations().getStationById(id);

        if(staz == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No station having the specified id value.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stations.getStations().deleteStation(staz);
			tx.success();
		}finally{
			tx.finish();			
		}    	
		
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/routes/{id}/delete" )
    public Response deleteRoute(@PathParam("id") Integer id) {

        Route route = Routes.getRoutes().getRouteById(id);

        if(route == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No route having the specified id value.")).build();
        	
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Routes.getRoutes().deleteRoute(route);
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/routes/deleteall" )
    public Response deleteAllRoutes() {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Routes.getRoutes().deleteAllRoutes();
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stops/{id}/delete" )
    public Response deleteStop(@PathParam("id") Integer id) {

        Stop stop = Stops.getStops().getStopById(id);

        if(stop == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No station having the specified id value.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stops.getStops().deleteStop(stop);
			tx.success();
		}finally{
			tx.finish();			
		}    	
		
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/runs/deleteall" )
    public Response deleteAllRuns() {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Runs.getRuns().deleteAllRuns();
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stops/deleteall" )
    public Response deleteAllStops() {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stops.getStops().deleteAllStops();
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stations/deleteall" )
    public Response deleteAllStations() {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stations.getStations().deleteAllStations();
			tx.success();
		}finally{
			tx.finish();			
		}       	
    	
    	boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/runs/{id}/checkpoints/{idcp}/delete")
    public Response deleteCheckPoint(@PathParam("id") Integer id, @PathParam("idcp") Integer idcp) {
        boa.server.domain.Run run = boa.server.domain.Runs.getRuns().getRunById(id);
        
        if(run == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No run having the specified id.")).build();
    	
        CheckPoint cp = run.getCheckPointById(idcp);
        
        if(cp == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No CheckPoint having the specified id.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
			run.deleteCheckPoint(cp);
			tx.success();
		}finally{
			tx.finish();			
		}                   

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();          
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/runs/{id}/checkpoints/deleteall" )
    public Response deleteAllCheckPoints(@PathParam("id") Integer id) {
        boa.server.domain.Run run = boa.server.domain.Runs.getRuns().getRunById(id);
        
        if(run == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No run having the specified id.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
			run.deleteAllIntermediateCheckPoints();
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }    
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/stations/createorupdate")
    public Response createOrUpdateStation(final boa.server.domain.json.Station  input) {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stations.getStations().createOrUpdateStation(input);
			tx.success();
		}finally{
			tx.finish();			
		}   			        
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    

    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/stations/bulkimport")            
    public Response bulkImportStations(final boa.server.domain.json.Stations  input) {
		Stations.getStations().createOrUpdateStations(input);
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )   
    @Path( "/routes/createorupdate" )
    public Response createOrUpdateRoute(final boa.server.domain.json.Route  input) {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Routes.getRoutes().createOrUpdateRoute(input);
			tx.success();
		}finally{
			tx.finish();			
		}   			        
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();  
    }    

    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/routes/bulkimport")            
    public Response bulkImportRoutes(final boa.server.domain.json.Routes input) {
		Routes.getRoutes().createOrUpdateRoutes(input);
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )   
    @Path( "/runs/createorupdate" )
    public Response createOrUpdateRun(final boa.server.domain.json.Run  input) {
		Transaction tx = DbConnection.getDb().beginTx();
		try{
			Runs.getRuns().createOrUpdateRun(input);
			tx.success();
		}finally{
			tx.finish();			
		}   			        
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();  
    }    
    

    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/runs/bulkimport")            
    public Response bulkImportRuns(final boa.server.domain.json.Runs  input) {
		Runs.getRuns().createOrUpdateRuns(input);
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }    
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )   
    @Path( "/stops/createorupdate" )
    public Response createOrUpdateStop(final boa.server.domain.json.Stop  input) {
		// staticTime check 
    	int st = input.getStaticTime();
    	Stop prev = Stops.getStops().getStopById(input.getPrevInRun());
    	Stop next = Stops.getStops().getStopById(input.getNextInRun());
    	if(st < prev.getStaticTime() || st > next.getStaticTime()){
            boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(422, "staticTime must be between prevInRun.staticTime and nextInRun.staticTime");
            return Response.ok().entity(jr).build();  
    	}    		
    	
    	Transaction tx = DbConnection.getDb().beginTx();
		try{
			Stops.getStops().createOrUpdateStop(input);
			tx.success();
		}finally{
			tx.finish();			
		}   			        
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();  
    }    
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/stops/bulkimport")            
    public Response bulkImportStops(final boa.server.domain.json.Stops  input) {
		Stops.getStops().createOrUpdateStops(input);
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }   
    
    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )   
    @Path( "/runs/{id}/checkpoints/createorupdate" )
    public Response createOrUpdateCheckPoint(@PathParam("id") Integer id, final boa.server.domain.json.CheckPoint  input) {
	
        Run run = Runs.getRuns().getRunById(id);
        if(run == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No run having the specified id.")).build();

    	Transaction tx = DbConnection.getDb().beginTx();
		try{
			run.createOrUpdateCheckPoint(input);
			tx.success();
		}finally{
			tx.finish();			
		}   			        
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();  
    }    
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/runs/{id}/createallcheckpoints" )
    public Response createAllCheckPoints(@PathParam("id") Integer id) {

        Run run = Runs.getRuns().getRunById(id);

        if(run == null)
        	return Response.ok().entity(new boa.server.plugin.json.Response(404, "No run having the specified id value.")).build();

		Transaction tx = DbConnection.getDb().beginTx();
		try{
	        run.createAllCheckPoints();	        
			tx.success();
		}finally{
			tx.finish();			
		}    	

        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/checkpoints/bulkcreation" )
    public Response bulkCreationCheckPoints() {

    	for(Run run : Runs.getRuns().getAll()){
			Transaction tx = DbConnection.getDb().beginTx();
			try{
		        run.createAllCheckPoints();	        
				tx.success();
			}finally{
				tx.finish();			
			}    	
    	}
    	
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stations/exportall" )
    public Response exportAllStations() {

    	boa.server.domain.json.Stations output = new boa.server.domain.json.Stations();
    	for(Station s : Stations.getStations().getAll()){
    		boa.server.domain.json.Station jsStation = new boa.server.domain.json.Station(s);    		
    		output.stationsObjectsList.add(jsStation);
    	}
    	
        return Response.ok().entity(output).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/routes/exportall" )
    public Response exportAllRoutes() {

    	boa.server.domain.json.Routes output = new boa.server.domain.json.Routes();
    	for(Route r : Routes.getRoutes().getAll()){
    		boa.server.domain.json.Route jsRoute = new boa.server.domain.json.Route(r);    		
    		output.routesObjectsList.add(jsRoute);
    	}
    	
        return Response.ok().entity(output).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/runs/exportall" )
    public Response exportAllRuns() {

    	boa.server.domain.json.Runs output = new boa.server.domain.json.Runs();
    	for(Run r : Runs.getRuns().getAll()){
    		boa.server.domain.json.Run jsRun = new boa.server.domain.json.Run(r);    		
    		output.runsObjectsList.add(jsRun);
    	}
    	
        return Response.ok().entity(output).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/stops/exportall" )
    public Response exportAllStops() {

    	boa.server.domain.json.Stops output = new boa.server.domain.json.Stops();
    	for(Stop s : Stops.getStops().getAll()){
    		boa.server.domain.json.Stop jsStop = new boa.server.domain.json.Stop(s);    		
    		output.stopsObjectsList.add(jsStop);
    	}
    	
        return Response.ok().entity(output).build();   
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/exportall" )
    public Response exportAll() {

    	boa.server.domain.json.Stations stations = new boa.server.domain.json.Stations();
    	for(Station s : Stations.getStations().getAll()){
    		boa.server.domain.json.Station jsStation = new boa.server.domain.json.Station(s);    		
    		stations.stationsObjectsList.add(jsStation);
    	}
    	
    	boa.server.domain.json.Routes routes = new boa.server.domain.json.Routes();
    	for(Route r : Routes.getRoutes().getAll()){
    		boa.server.domain.json.Route jsRoute = new boa.server.domain.json.Route(r);    		
    		routes.routesObjectsList.add(jsRoute);
    	}

    	boa.server.domain.json.Runs runs = new boa.server.domain.json.Runs();
    	for(Run r : Runs.getRuns().getAll()){
    		boa.server.domain.json.Run jsRun = new boa.server.domain.json.Run(r);    		
    		runs.runsObjectsList.add(jsRun);
    	}
    	
    	boa.server.domain.json.Stops stops = new boa.server.domain.json.Stops();
    	for(Stop s : Stops.getStops().getAll()){
    		boa.server.domain.json.Stop jsStop = new boa.server.domain.json.Stop(s);    		
    		stops.stopsObjectsList.add(jsStop);
    	}

    	boa.server.domain.json.Boa output = new boa.server.domain.json.Boa();
    	output.setStations(stations);
    	output.setRoutes(routes);
    	output.setRuns(runs);
    	output.setStops(stops);
    	
        return Response.ok().entity(output).build();   
    }

    @POST @Consumes("application/json")
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("/bulkimportall")            
    public Response bulkImportAll(final boa.server.domain.json.Boa input) throws IOException {
		Stations.getStations().createOrUpdateStations(input.getStations());
        Routes.getRoutes().createOrUpdateRoutes(input.getRoutes());
        Runs.getRuns().createOrUpdateRuns(input.getRuns());
        Stops.getStops().createOrUpdateStops(input.getStops());
        Stops.getStops().createOrUpdateStops(input.getStops());
        Runs.getRuns().createOrUpdateRuns(input.getRuns());
        
        bulkCreationCheckPoints();
        
        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
        return Response.ok().entity(jr).build();   
    }    

    @GET
    @Path("/cleandb")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cleanDb() {
        try {
    		DbConnection.cleanDbDirectory(database, config);
            Stations.destroy();
            Routes.destroy();
            Stops.destroy();
            Runs.destroy();
            DbConnection.destroy();
            
	        boa.server.plugin.json.Response jr = new boa.server.plugin.json.Response(200, "OK");
	        return Response.ok().entity(jr).build();  
		} catch (Throwable e1) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonHelper.createJsonFrom(e1.getMessage())).build();
		}        
    }    

}
