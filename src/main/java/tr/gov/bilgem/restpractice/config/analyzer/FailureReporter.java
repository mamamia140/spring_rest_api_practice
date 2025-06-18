package tr.gov.bilgem.restpractice.config.analyzer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;

public class FailureReporter implements FailureAnalysisReporter {

    private static final Log logger = LogFactory.getLog(FailureReporter.class);

    public FailureReporter() {

    }
    @Override
    public void report(FailureAnalysis analysis) {
        logger.error(analysis.getCause().getMessage(),analysis.getCause());
        System.err.println(analysis.getDescription());
    }
}
