/***************************************************************************
 *  Copyright (C) 2012 by H-Store Project                                  *
 *  Brown University                                                       *
 *  Massachusetts Institute of Technology                                  *
 *  Yale University                                                        *
 *                                                                         *
 *  Original By: VoltDB Inc.											   *
 *  Ported By:  Justin A. DeBrabant (http://www.cs.brown.edu/~debrabant/)  *								   
 *                                                                         *
 *                                                                         *
 *  Permission is hereby granted, free of charge, to any person obtaining  *
 *  a copy of this software and associated documentation files (the        *
 *  "Software"), to deal in the Software without restriction, including    *
 *  without limitation the rights to use, copy, modify, merge, publish,    *
 *  distribute, sublicense, and/or sell copies of the Software, and to     *
 *  permit persons to whom the Software is furnished to do so, subject to  *
 *  the following conditions:                                              *
 *                                                                         *
 *  The above copyright notice and this permission notice shall be         *
 *  included in all copies or substantial portions of the Software.        *
 *                                                                         *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,        *
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF     *
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. *
 *  IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR      *
 *  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,  *
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR  *
 *  OTHER DEALINGS IN THE SOFTWARE.                                        *
 ***************************************************************************/

package edu.brown.benchmark.microexperiments.noftriggers.trig10;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;
import org.voltdb.client.ProcedureCallback;

import weka.classifiers.meta.Vote;

import edu.brown.api.BenchmarkComponent;
import edu.brown.hstore.Hstoreservice.Status;
import edu.brown.logging.LoggerUtil.LoggerBoolean;

public class NoFTriggersClient extends BenchmarkComponent {
    private static final Logger LOG = Logger.getLogger(NoFTriggersClient.class);
    private static final LoggerBoolean debug = new LoggerBoolean();

    // Flags to tell the worker threads to stop or go
    AtomicBoolean warmupComplete = new AtomicBoolean(false);
    AtomicBoolean benchmarkComplete = new AtomicBoolean(false);

    // voter benchmark state
    AtomicLong acceptedVotes = new AtomicLong(0);
    AtomicLong badContestantVotes = new AtomicLong(0);
    AtomicLong badVoteCountVotes = new AtomicLong(0);
    AtomicLong failedVotes = new AtomicLong(0);
    static AtomicLong batchid = new AtomicLong(0);
    Random rand = new Random();

    public static void main(String args[]) {
        BenchmarkComponent.main(NoFTriggersClient.class, args, false);
    }

    public NoFTriggersClient(String args[]) {
        super(args);
    }

    @Override
    public void runLoop() {
        try {
            while (true) {
                // synchronously call the "Vote" procedure
                try {
                    runOnce();
                } catch (Exception e) {
                    failedVotes.incrementAndGet();
                }

            } // WHILE
        } catch (Exception e) {
            // Client has no clean mechanism for terminating with the DB.
            e.printStackTrace();
        }
    }

    @Override
    protected boolean runOnce() throws IOException {

        Client client = this.getClientHandle();
        long id = batchid.getAndIncrement();
        boolean resp = true;
        
        ClientResponse cr;
		try {
			cr = client.callProcedure("ProcOne",(int)id,rand.nextInt(10));
	        incrementTransactionCounter(cr, 0);
	        if(NoFTriggersConstants.NUM_TRIGGERS > 1)
	        	client.callProcedure("ProcTwo");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 2)
	        	client.callProcedure("ProcThree");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 3)
	        	client.callProcedure("ProcFour");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 4)
	        	client.callProcedure("ProcFive");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 5)
	        	client.callProcedure("ProcSix");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 6)
	        	client.callProcedure("ProcSeven");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 7)
	        	client.callProcedure("ProcEight");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 8)
	        	client.callProcedure("ProcNine");
	        if(NoFTriggersConstants.NUM_TRIGGERS > 9)
	        	client.callProcedure("ProcTen");
    	}
        catch (ProcCallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp = false;
		}
        	
        	
        return resp;
    }

    @Override
    public String[] getTransactionDisplayNames() {
        // Return an array of transaction names
        String procNames[] = new String[]{
            Vote.class.getSimpleName()
        };
        return (procNames);
    }
}