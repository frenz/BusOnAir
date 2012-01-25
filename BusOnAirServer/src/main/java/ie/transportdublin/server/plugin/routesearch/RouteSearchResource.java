package ie.transportdublin.server.plugin.routesearch;

import domain.*;
import ie.transportdublin.server.plugin.json.RouteStop;
import ie.transportdublin.server.plugin.json.Routes;
import ie.transportdublin.xml.Stop;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.lucene.index.IndexNotFoundException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.index.lucene.QueryContext;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.server.database.Database;
import org.neo4j.server.rest.repr.OutputFormat;
import org.neo4j.server.webadmin.rest.SessionFactoryImpl;

@Path( "/routesearch" )
public class RouteSearchResource
{

    private Routes routeList;
    private Index<Node> stopLayer;
    private final Database database;
    private BufferedWriter log;

    public RouteSearchResource( @Context Database database,
            @Context HttpServletRequest req, @Context OutputFormat output ) throws IOException 
    {
        this( new SessionFactoryImpl( req.getSession( true ) ), database,
                output );

        String fullURL = req.getRequestURL().append("?").append( 
            req.getQueryString()).toString();        
        log.write("\nHttpServletRequest(" + fullURL +")");
        log.flush(); 
        
        
    }

    public RouteSearchResource( SessionFactoryImpl sessionFactoryImpl,
            Database database, OutputFormat output ) throws IOException 
    {
        FileWriter logFile = new FileWriter("/tmp/trasportaqroutes.log");
        log = new BufferedWriter(logFile);
        this.database = database;
        DbConnection.createDbConnection(database);
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/" )
    public Response routeSearch( @QueryParam( "routeId" ) String routeId ) throws IOException 
    {        
        log.write("\nROUTESEARCH(" + routeId +")");
        log.flush();

        if ( routeId == null || routeId.trim().length() == 0 )
            return Response.serverError().entity( "routeId cannot be blank" ).build();

        domain.Route route = domain.Routes.getRoutes().getRouteByLine(routeId);
        if(route == null){
            return Response.status( 400 ).entity(
                "No Route Found: " + routeId ).build();
        }
        log.write(route.toString());
        log.flush();
                
        Run run = route.getAllRuns().iterator().next();
        
        domain.Stop stop = run.getFirstStop();
        
        routeList = new Routes();
        while(stop != null){
            routeList.add(new RouteStop(stop));
            stop = stop.getNextInRun();
        }

        return Response.ok().entity(routeList).build();
    }
}
