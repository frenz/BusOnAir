package boa.server.test;

import boa.server.domain.DbConnection;
import boa.server.domain.Run;
import boa.server.domain.Runs;
import boa.server.domain.Stop;
import boa.server.test.utils.Chronometer;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;


public class TestPesanteUpdate {


    public static void main(String[] args) {
        DbConnection.createEmbeddedDbConnection();
        GraphDatabaseService db = DbConnection.getDb();

        int i = 0;

        Chronometer ch = new Chronometer();

        ch.start();

        for (Run r : Runs.getRuns().getAll()) {
            Stop fs = r.getFirstStop();

            //System.out.print("\n-------------------\n" + r);

            Transaction tx = DbConnection.getDb().beginTx();
            try {
                r.checkPointPass(r.getFirstCheckPoint(), fs.getTime() + 30);
                tx.success();
            } finally {
                tx.finish();
            }


            //System.out.print("\n" + r);

            //r.restoreRun();

            i++;
        }

        ch.stop();
        System.out.println("\n" + ch.getSeconds());

        System.out.print("\n" + i);


        DbConnection.turnoff();


    }

}
