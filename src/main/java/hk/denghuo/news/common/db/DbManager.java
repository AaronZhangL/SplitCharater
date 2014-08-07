package main.java.hk.denghuo.news.common.db;

import static java.lang.System.out;

import java.io.File;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;

public class DbManager {


    private GraphDatabaseService db;
    private TraversalDescription friendsTraversal;

    private static final String DB_PATH = "http://dev.ziizle.com:7474/db/data/";

    public static void main( String[] args )
    {
        deleteFileOrDirectory( new File( DB_PATH ) );
        GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        DbManager example = new DbManager( database );
        Node joe = example.createData();
        example.run( joe );
    }

    public DbManager( GraphDatabaseService db )
    {
        this.db = db;
        // START SNIPPET: basetraverser
        friendsTraversal = db.traversalDescription()
                .depthFirst()
                .relationships( Rels.KNOWS )
                .uniqueness( Uniqueness.RELATIONSHIP_GLOBAL );
        // END SNIPPET: basetraverser
    }
    
    private Node createData()
    {
        ExecutionEngine engine = new ExecutionEngine( db );
        String query = "CREATE (joe {name: 'Joe'}), (sara {name: 'Sara'}), "
           + "(lisa {name: 'Lisa'}), (peter {name: 'PETER'}), (dirk {name: 'Dirk'}), "
                       + "(lars {name: 'Lars'}), (ed {name: 'Ed'}),"
           + "(joe)-[:KNOWS]->(sara), (lisa)-[:LIKES]->(joe), "
           + "(peter)-[:KNOWS]->(sara), (dirk)-[:KNOWS]->(peter), "
           + "(lars)-[:KNOWS]->(drk), (ed)-[:KNOWS]->(lars), " 
           + "(lisa)-[:KNOWS]->(lars) " 
           + "RETURN joe";
        ExecutionResult result = engine.execute( query );
        Object joe = result.columnAs( "joe" ).next();
        if ( joe instanceof Node )
        {
            return (Node) joe;
        }
        else
        {
            throw new RuntimeException( "Joe isn't a node!" );
        }
    }

    private void run( Node joe )
    {
        try (Transaction tx = db.beginTx())
        {
            out.println( knowsLikesTraverser( joe ) );
            out.println( traverseBaseTraverser( joe ) );
            out.println( depth3( joe ) );
            out.println( depth4( joe ) );
            out.println( nodes( joe ) );
            out.println( relationships( joe ) );
        }
    }

    public String knowsLikesTraverser( Node node )
    {
        String output = "";
        // START SNIPPET: knowslikestraverser
        for ( Path position : db.traversalDescription()
                .depthFirst()
                .relationships( Rels.KNOWS )
                .relationships( Rels.LIKES, Direction.INCOMING )
                .evaluator( Evaluators.toDepth( 5 ) )
                .traverse( node ) )
        {
            output += position + "\n";
        }
        // END SNIPPET: knowslikestraverser
        return output;
    }

    public String traverseBaseTraverser( Node node )
    {
        String output = "";
        // START SNIPPET: traversebasetraverser
        for ( Path path : friendsTraversal.traverse( node ) )
        {
            output += path + "\n";
        }
        // END SNIPPET: traversebasetraverser
        return output;
    }

    public String depth3( Node node )
    {
        String output = "";
        // START SNIPPET: depth3
        for ( Path path : friendsTraversal
                .evaluator( Evaluators.toDepth( 3 ) )
                .traverse( node ) )
        {
            output += path + "\n";
        }
        // END SNIPPET: depth3
        return output;
    }

    public String depth4( Node node )
    {
        String output = "";
        // START SNIPPET: depth4
        for ( Path path : friendsTraversal
                .evaluator( Evaluators.fromDepth( 2 ) )
                .evaluator( Evaluators.toDepth( 4 ) )
                .traverse( node ) )
        {
            output += path + "\n";
        }
        // END SNIPPET: depth4
        return output;
    }

    public String nodes( Node node )
    {
        String output = "";
        // START SNIPPET: nodes
        for ( Node currentNode : friendsTraversal
                .traverse( node )
                .nodes() )
        {
            output += currentNode.getProperty( "name" ) + "\n";
        }
        // END SNIPPET: nodes
        return output;
    }

    public String relationships( Node node )
    {
        String output = "";
        // START SNIPPET: relationships
        for ( Relationship relationship : friendsTraversal
                .traverse( node )
                .relationships() )
        {
            output += relationship.getType().name() + "\n";
        }
        // END SNIPPET: relationships
        return output;
    }

    // START SNIPPET: sourceRels
    private enum Rels implements RelationshipType
    {
        LIKES, KNOWS
    }
    // END SNIPPET: sourceRels

    private static void deleteFileOrDirectory( File file )
    {
        if ( file.exists() )
        {
            if ( file.isDirectory() )
            {
                for ( File child : file.listFiles() )
                {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }
}
