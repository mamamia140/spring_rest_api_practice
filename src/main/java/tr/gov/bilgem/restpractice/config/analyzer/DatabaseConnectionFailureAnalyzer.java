package tr.gov.bilgem.restpractice.config.analyzer;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.sql.SQLException;

public class DatabaseConnectionFailureAnalyzer extends AbstractFailureAnalyzer<SQLException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, SQLException cause) {
        return new FailureAnalysis("Unable to connect to the database server!",rootFailure.getMessage(),rootFailure);
    }
}