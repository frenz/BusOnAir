package ie.transportdublin.server.plugin.routes;

import domain.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.neo4j.server.database.Database;
import org.neo4j.server.rest.repr.OutputFormat;
import org.neo4j.server.webadmin.rest.SessionFactoryImpl;

@Path( "/routes" )
public class RoutesResource{
    private final Database database;
    private BufferedWriter log;

    public RoutesResource( @Context Database database,
            @Context HttpServletRequest req, @Context OutputFormat output ) throws IOException 
    {
        this( new SessionFactoryImpl( req.getSession( true ) ), database,
                output );

        StringBuffer fullURL = req.getRequestURL();
        StringBuffer queryString = new StringBuffer();
        queryString.append(req.getQueryString());
        if(!queryString.toString().equals("null"))
        	fullURL.append("?").append(queryString);
        
        log.write("\nHttpServletRequest(" + fullURL.toString() +")");
        log.flush(); 
    }

    public RoutesResource( SessionFactoryImpl sessionFactoryImpl,
            Database database, OutputFormat output ) throws IOException 
    {
        FileWriter logFile = new FileWriter("/tmp/trasportaqroutes.log");
        log = new BufferedWriter(logFile);
        this.database = database;
        DbConnection.createDbConnection(database);
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/getall" )
    public Response getAll() throws IOException{        
        log.write("\nroutes/getall");
        log.flush();
              
        json.Routes routeList = new json.Routes();

        Iterable<Route> routes = domain.Routes.getRoutes().getAll();
         
        for(Route r : routes){
        	json.Route jr = new json.Route(r);  
        	routeList.add(jr);        	
        }

        return Response.ok().entity(routeList).build();
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("{line}")
    public Response getRoute(@PathParam("line") String line) throws IOException{
    	
    	log.write("\nroutes/" + line);
        log.flush();
    	
        if ( line == null)
            return Response.status( 400 ).entity( "line cannot be blank" ).build();
        
    	domain.Route route = domain.Routes.getRoutes().getRouteByLine(line);
    	 
    	if (route != null){
    		json.Route jr = new json.Route(route);    	
    		return Response.ok().entity(jr).build();
    	} else {
    		return Response.status( 404 ).entity( "No route having the specified line value." ).build();
    	}
    }
    
    
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )    
    @Path("{line}/getallruns")
    public Response getAllRuns(@PathParam("line") String line) throws IOException{
    	
    	log.write("\ngetAllRuns/" + line);
        log.flush();
        
        if ( line == null)
            return Response.status( 400 ).entity( "line cannot be blank" ).build();
        
    	domain.Route route = domain.Routes.getRoutes().getRouteByLine(line);
    	 
    	if (route == null)
    		return Response.status( 404 ).entity( "No route having the specified line value." ).build();

    	ArrayList<Run> runs = route.getAllRuns();
    	
        if(runs.size() == 0)
        	return Response.ok().entity("").build();
        	
        json.Runs jruns = new json.Runs();
        for(domain.Run r : runs)
        	jruns.add(r);
        	   
        return Response.ok().entity(jruns).build();
    }

}