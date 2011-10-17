package com.linkedin.clustermanager.zk;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.linkedin.clustermanager.TestDriver;

public class TestCustomIdealState extends ZkTestBase
{
  private static Logger LOG = Logger.getLogger(TestCustomIdealState.class);

  @Test
  public void testCustomIdealState() throws Exception
  {
    
    int numDb = 2;
    int numPartitionsPerDb = 100;
    int numNode = 5;
    int replica = 3;
    
    String uniqTestName = "TestCustomIS_" + "db" + numDb + "_p" + numPartitionsPerDb + "_n"
        + numNode + "_r" + replica;
    System.out.println("START " + uniqTestName + " at " + new Date(System.currentTimeMillis()));

    TestDriver.setupClusterWithoutRebalance(uniqTestName, numDb, numPartitionsPerDb, numNode, replica);

    for (int i = 0; i < numNode; i++)
    {
      TestDriver.startDummyParticipant(uniqTestName, i);
    }
    TestDriver.startController(uniqTestName);
    
    TestDriver.setIdealState(uniqTestName, 2000, 50);
    
    TestDriver.verifyCluster(uniqTestName);
    TestDriver.stopCluster(uniqTestName);

    System.out.println("END " + uniqTestName + " at " + new Date(System.currentTimeMillis()));
    

  }

}
