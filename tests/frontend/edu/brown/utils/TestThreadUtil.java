package edu.brown.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.voltdb.utils.Pair;

import junit.framework.TestCase;

public class TestThreadUtil extends TestCase {

    /**
     * testExceptionHandler
     */
//    public void testExceptionHandler() throws Exception {
//        final CountDownLatch latch = new CountDownLatch(5);
//        EventObservableExceptionHandler handler = new EventObservableExceptionHandler();
//        EventObserver<Pair<Thread, Throwable>> observer = new EventObserver<Pair<Thread, Throwable>>() {
//            @Override
//            public void update(EventObservable<Pair<Thread, Throwable>> o, Pair<Thread, Throwable> arg) {
//                Thread thread = arg.getFirst();
//                assertNotNull(thread);
//                Throwable error = arg.getSecond();
//                assertNotNull(error);
//                System.err.printf("[%02d] Got Error: %s / %s\n",
//                                  latch.getCount(), thread.getName(), error);
//                latch.countDown();
//            }
//        };
//        handler.addObserver(observer);
//        
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                System.err.println("Executing failing thread!");
//                throw new RuntimeException("Old and busted!");
//            }
//        };
//        
//        int poolSize = 1;
//        int stackSize = 1024*128;
//        ScheduledThreadPoolExecutor executor = ThreadUtil.getScheduledThreadPoolExecutor("TEST", handler, poolSize, stackSize);
//        executor.scheduleWithFixedDelay(r, 1, 1, TimeUnit.SECONDS);
//        
//        boolean ret = latch.await(10, TimeUnit.SECONDS);
//        assertTrue(ret);
//    }
    
    /**
     * testRun
     */
    public void testRun() {
        final int num_threads = 100;
        final AtomicInteger ctr = new AtomicInteger(0);
        
        List<Runnable> threads = new ArrayList<Runnable>(); 
        for (int i = 0; i < num_threads; i++) {
            threads.add(new Runnable() {
                public void run() {
                    ctr.incrementAndGet();
                };
            });
        } // FOR
        
        ThreadUtil.runNewPool(threads);
        assertEquals(num_threads, ctr.get());
    }
    
}
