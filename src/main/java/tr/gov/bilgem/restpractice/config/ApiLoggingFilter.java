package tr.gov.bilgem.restpractice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //long startTime = System.currentTimeMillis();
        log.info("{} {} HTTP {} {}?{}",
                getTimestamp(),
                getClientIp(request),
                request.getMethod(),
                request.getRequestURI(),
                ((request.getQueryString() != null) ? "?" + request.getQueryString() : "")
        );
        try {
            filterChain.doFilter(request, response);
        } finally {
            /*
            long duration = System.currentTimeMillis() - startTime;
            log.info(
                    "Method: {} URI: {} Status: {} Duration: {}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration
            );
            */
        }
    }
    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        // In case there are multiple IPs in X-Forwarded-For
        return xfHeader.split(",")[0];
    }
    private String getTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}

