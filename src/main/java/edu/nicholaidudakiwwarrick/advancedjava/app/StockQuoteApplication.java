package edu.nicholaidudakiwwarrick.advancedjava.app;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceFactory;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockService;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Basic class with a Main method to create StockService instances
 */
public class StockQuoteApplication {

    @Option(name="-symbol", usage="stock symbol to look up")
    private String symbol;

    @Option(name="-start", usage="start date of the stock lookup")
    private String startDate;

    @Option(name="-end", usage="end date of the stock lookup")
    private String endDate;

    @Option(name="-interval", usage="time interval between the start and end dates")
    private String interval;

    /**
     * @param args an array that contains elements:
     *             <code>String</code> representation of stock symbol, start date, and end date
     */
    public static void main(@NotNull String[] args) {

        // Create StockQuoteApplication object to pass to the command line parser
        StockQuoteApplication app = new StockQuoteApplication();
        CmdLineParser parser = new CmdLineParser(app);
        parser.setUsageWidth(80);

        // Pass the command line args to the parser
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("StockQuoteApplication sample arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
        }

        // Retrieve date formatter and parse the start and end date args
        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startDate = DateTime.parse(app.startDate, formatter);
        DateTime endDate = DateTime.parse(app.endDate, formatter);

        // Print stock quotes using all three service types - BASIC, DATABASE, WEB
        try {
            app.printStockQuotes(app.symbol, startDate, endDate, app.asIntervalEnum(app.interval), ServiceType.BASIC);

            app.printStockQuotes(app.symbol, startDate, endDate, app.asIntervalEnum(app.interval), ServiceType.DATABASE);

            app.printStockQuotes(app.symbol, startDate, endDate, app.asIntervalEnum(app.interval), ServiceType.WEB);
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints out {@code StockQuote} objects for all overloaded {@code getQuote} methods
     * @param symbol the stock symbol to look up
     * @param start the starting date of the stock look up
     * @param end the end date of the stock look up
     * @param interval interval for the stock look up (e.g., DAILY, WEEKLY, MONTHLY)
     * @param type the service type of the stock look up (e.g., BASIC, DATABASE, WEB)
     * @throws StockServiceException when the {@code StockService} creation fails or if
     *                               any of the getQuote methods fail
     */
    public final void printStockQuotes(String symbol, DateTime start, DateTime end, IntervalEnum interval, ServiceType type)
    throws StockServiceException{

        StockService stockService = ServiceFactory.getStockServiceInstance(type);

        System.out.println("Print a single stock quote - " + type.toString() + " stock service");
        System.out.println("------------------------------------------------------------------");
        System.out.println(stockService.getQuote(symbol, start).toString());

        System.out.println("Print multiple stock quotes - " + type.toString() + " stock service");
        System.out.println("-------------------------------------------------------------------");
        List<StockQuote> quoteList = stockService.getQuote(symbol, start, end);
        for (StockQuote quote : quoteList){
            System.out.println(quote.toString());
        }

        System.out.println("Print multiple stock quotes at a " + interval.toString() + " interval - "
                + type.toString() + " stock service");
        System.out.println("-------------------------------------------------------------------");
        List<StockQuote> intervalQuoteList = stockService.getQuote(symbol, start, end, interval);
        for (StockQuote quote : intervalQuoteList) {
            System.out.println(quote.toString());
        }
    }

    /**
     * Returns an {@code IntervalEnum} from a passed {@code String}
     *
     * @param interval a string representation of an IntervalEnum
     * @return an IntervalEnum
     */
    public final IntervalEnum asIntervalEnum(String interval) {
        // Default Interval - returned if no match is found
        IntervalEnum result = IntervalEnum.MONTHLY;

        if (interval.toUpperCase().equals("HOURLY")) {
            result = IntervalEnum.HOURLY;
        } else if (interval.toUpperCase().equals("HALF_DAY")) {
            result = IntervalEnum.HALF_DAY;
        } else if (interval.toUpperCase().equals("DAILY")) {
            result = IntervalEnum.DAILY;
        } else if (interval.toUpperCase().equals("WEEKLY")) {
            result = IntervalEnum.WEEKLY;
        } else if (interval.toUpperCase().equals("MONTHLY")) {
            result = IntervalEnum.MONTHLY;
        }

        return result;
    }
}