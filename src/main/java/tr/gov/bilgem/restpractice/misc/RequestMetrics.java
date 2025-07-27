package tr.gov.bilgem.restpractice.misc;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestMetrics {

    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger clientFailCount = new AtomicInteger(0);
    private final AtomicInteger serverFailCount = new AtomicInteger(0);
    private final AtomicLong totalDuration = new AtomicLong(0);
    private final AtomicInteger requestCount = new AtomicInteger(0);

    private final Object lock = new Object();

    public void recordSuccess() {
        synchronized (lock) {
            successCount.incrementAndGet();
        }
    }

    public void recordClientFail() {
        synchronized (lock) {
            clientFailCount.incrementAndGet();
        }
    }

    public void recordServerFail() {
        synchronized (lock) {
            serverFailCount.incrementAndGet();
        }
    }

    public void recordDuration(long durationMillis) {
        synchronized (lock) {
            totalDuration.addAndGet(durationMillis);
            requestCount.incrementAndGet();
        }
    }

    public int getSuccessCount() {
        synchronized (lock) {
            return successCount.get();
        }
    }

    public int getClientFailCount() {
        synchronized (lock) {
            return clientFailCount.get();
        }
    }

    public int getServerFailCount() {
        synchronized (lock) {
            return serverFailCount.get();
        }
    }

    public long getAvgDuration() {
        synchronized (lock) {
            int count = requestCount.get();
            return count == 0 ? 0 : totalDuration.get() / count;
        }
    }

    public void reset() {
        synchronized (lock) {
            successCount.set(0);
            clientFailCount.set(0);
            serverFailCount.set(0);
            totalDuration.set(0);
            requestCount.set(0);
        }
    }

    public Map<String, Object> getSnapshot() {
        synchronized (lock) {
            return Map.of(
                    "req-success-count", successCount.get(),
                    "req-client-fail-count", clientFailCount.get(),
                    "req-server-fail-count", serverFailCount.get(),
                    "req-avg-duration", getAvgDuration()
            );
        }
    }
}


