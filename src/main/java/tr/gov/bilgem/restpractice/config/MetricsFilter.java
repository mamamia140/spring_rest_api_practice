package tr.gov.bilgem.restpractice.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.misc.RequestMetrics;

import java.io.IOException;

@Component
public class MetricsFilter implements Filter {

    private final RequestMetrics metrics;

    @Value("${metrics.enabled:true}")
    private boolean metricsEnabled;

    public MetricsFilter(RequestMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!metricsEnabled) {
            chain.doFilter(request, response);
            return;
        }

        long start = System.currentTimeMillis();

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            metrics.recordDuration(duration);

            if (response instanceof HttpServletResponse resp) {
                int status = resp.getStatus();
                if (status >= 200 && status < 300) {
                    metrics.recordSuccess();
                } else if (status >= 400 && status < 500) {
                    metrics.recordClientFail();
                } else if (status >= 500) {
                    metrics.recordServerFail();
                }
            }
        }
    }
}