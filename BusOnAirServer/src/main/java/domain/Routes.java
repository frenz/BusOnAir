/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

/**
 *
 * @author rashta
 */
public class Routes {
    private Index<Node> routesIndex;

    private static Routes instance = null;
    
    public static synchronized Routes getRoutes() {
        if (instance == null) 
            instance = new Routes();
        return instance;
    }    
    
    private Routes(){
        routesIndex = DbConnection.getDb().index().forNodes("routesIndex");
    }
    
    public void addRoute(Route r){
        routesIndex.add(r.getUnderlyingNode(), "line", r.getLine());
    }
    
    public Route getRouteByLine(String line){
        IndexHits<Node> result = routesIndex.get("line", line);
        Node n = result.getSingle();
        result.close();
        if(n == null){
            return null;
        } else {
            return new Route(n);                
        }
    }

    public Iterable<Route> getAll() {
        ArrayList<Route> output = new ArrayList<Route>();
        IndexHits<Node> result = routesIndex.query("line", "*");
        for(Node n : result){
            output.add(new Route(n));           
        }     
        result.close();
        return output;
    }
}
