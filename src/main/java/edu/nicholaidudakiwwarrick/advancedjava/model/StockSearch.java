package edu.nicholaidudakiwwarrick.advancedjava.model;

import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceFactory;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockService;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Container class to facilitate stock searches using the webapp
 * Implements the Java Bean pattern
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class StockSearch {

    // static member fields of this class
    private String symbol;
    private DateTime startDate;
    private DateTime endDate;
    private IntervalEnum interval;
    private String quoteStr;

    /**
     * constructs a new {@code StockSearch} instance by default per Java Bean pattern
     */
    public StockSearch() {}

    /**
     * constructs a new {@code StockSearch} instance
     *
     * @param symbol the stock symbol entered
     * @param startDate the starting date of symbol being queried
     * @param endDate the end date of the symbol being queried
     * @param interval the interval at which to retrieve the stock quotes between the supplied dates
     */
    public StockSearch(String symbol, String startDate, String endDate, String interval) {
        setSymbol(symbol);
        setStartDate(startDate);
        setEndDate(endDate);
        setInterval(interval);
    }

    /**
     * Returns a String representation of the symbol
     * @return a symbol that represents the company issuing the stock
     */
    public final String getSymbol() {
        return symbol;
    }

    /**
     * Sets a String representation of the symbol
     * @param symbol a symbol that represents the company issuing the stock
     */
    public final void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns a DateTime representation of the start date
     * @return the starting date of the stock quote info
     */
    public final String getStartDate() {
        return startDate.toString(StockQuote.getDateFormatter());
    }

    /**
     * Sets a DateTime representation of the start date
     * @param startDate
     */
    public final void setStartDate(String startDate) {
        this.startDate = DateTime.parse(startDate, StockQuote.getDateFormatter());
    }

    public final String getEndDate() {
        return endDate.toString(StockQuote.getDateFormatter());
    }

    public final void setEndDate(String endDate) {
        this.endDate = DateTime.parse(endDate, StockQuote.getDateFormatter());
    }

    public final String getInterval() {
        return interval.toString();
    }

    public final void setInterval(String str) {
        String intervalStr = str.toUpperCase();
        switch (intervalStr) {
            case("DAY"):
                this.interval = IntervalEnum.DAILY;
                break;
            case("WEEK"):
                this.interval = IntervalEnum.WEEKLY;
                break;
            case("MONTH"):
                this.interval = IntervalEnum.MONTHLY;
                break;
            default:
                this.interval = IntervalEnum.DAILY;
        }
    }

    public final String getQuoteStr() {
        return quoteStr;
    }

    public final void setQuoteStr(String quoteStr) {
        this.quoteStr = quoteStr;
    }

    public void processData(ServiceType type) throws StockServiceException {
        if (this.validateData()) {
            StockService service = ServiceFactory.getStockServiceInstance(type);
            List<StockQuote> quoteList = service.getQuote(symbol, startDate, endDate, interval);
            StringBuilder builder = new StringBuilder();
            for (StockQuote q : quoteList) {
                builder.append(q.toString());
            }
            this.quoteStr = builder.toString();
        } else {
            throw new IllegalStateException("Not all fields have been initialized");
        }
    }

    public boolean validateData() {
        if ((this.symbol == null) || (this.startDate == null) || (this.endDate == null) || this.interval == null)
            return false;
        return true;
    }
}
