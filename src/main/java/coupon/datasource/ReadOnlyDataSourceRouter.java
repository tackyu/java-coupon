package coupon.datasource;

import static coupon.datasource.DataSourceType.READER;
import static coupon.datasource.DataSourceType.WRITER;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return READER;
        }
        return WRITER;
    }
}