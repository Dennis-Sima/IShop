package at.spengergasse.IShop.foundation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NamedLoggerFactory {

    private static final String PERFORMANCE_LOGGER = "_performance.metrics";
    private static final String DATA_QUALITY_LOGGER = "_data.quality";

    public static final Logger getPerformanceLogger() {
        return LoggerFactory.getLogger(PERFORMANCE_LOGGER);
    }

    public static final Logger getDataQualityLogger() {
        return LoggerFactory.getLogger(DATA_QUALITY_LOGGER);
    }
}
