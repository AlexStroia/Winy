package co.alexdev.winy.core.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WinyExecutor {

    private Executor diskIO;
    private Executor mainThread;

    public WinyExecutor(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    @Inject
    public WinyExecutor() {
        this(Executors.newSingleThreadExecutor(),
                new MainThreadExecutor());
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public void setMainThread(Executor mainThread) {
        this.mainThread = mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private final android.os.Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }
    }
}
