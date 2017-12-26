package com.tresor.common.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kris on 9/16/17. Tokopedia
 */

public class TresorExecutor implements Executor {

    private ThreadPoolExecutor threadPoolExecutor;

    public TresorExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(), new TresorFactory());
    }

    @Override
    public void execute(@NonNull Runnable command) {

    }


    private static class TresorFactory implements ThreadFactory {

        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "thread_number: " + counter++ );
        }
    }

}
