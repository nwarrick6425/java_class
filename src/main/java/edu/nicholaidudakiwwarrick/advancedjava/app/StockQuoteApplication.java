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

        StockQuoteApplication app = new StockQuoteApplication();
        CmdLineParser parser = new CmdLineParser(app);
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("StockQuoteApplication sample arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
        }

        StockService stockService = null;

        // create a StockService instance using the ServiceFactory static factory method
        try {
            stockService = ServiceFactory.getStockServiceInstance(ServiceType.BASIC);
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // Retrieve date formatter and parse the 2nd and 3rd date args
        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startDate = DateTime.parse(app.startDate, formatter);
        DateTime endDate = DateTime.parse(app.endDate, formatter);

        System.out.println("Basic Stock Service Quotes");
        System.out.println("****************************************");
        try {
            // Print a single stock quote to the console
            System.out.println("Print a single stock quote");
            System.out.println(stockService.getQuote(app.symbol, startDate).toString());
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        try {
            // Print multiple stock quotes that fall between both date ranges
            System.out.println("Print multiple stock quotes for the given date range");
            List<StockQuote> stockList = stockService.getQuote(app.symbol, startDate, endDate);

            for (StockQuote quote : stockList) {
                System.out.println(quote.toString());
            }
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // Print multiple stock quotes between the period at the given interval
        System.out.println("Print multiple stock quotes for the given date range at the given interval");
        try {
            if (app.interval.toUpperCase().equals("HOURLY")) {
                List<StockQuote> intervalStockList =
                        stockService.getQuote(app.symbol, startDate, endDate, IntervalEnum.HOURLY);

                for (StockQuote quote : intervalStockList) {
                    System.out.println(quote.toString());
                }
            } else if (app.interval.toUpperCase().equals("HALF_DAY")) {
                List<StockQuote> intervalStockList =
                        stockService.getQuote(app.symbol, startDate, endDate, IntervalEnum.HALF_DAY);

                for (StockQuote quote : intervalStockList) {
                    System.out.println(quote.toString());
                }
            } else if (app.interval.toUpperCase().equals("DAILY")) {
                List<StockQuote> intervalStockList =
                        stockService.getQuote(app.symbol, startDate, endDate, IntervalEnum.DAILY);

                for (StockQuote quote : intervalStockList) {
                    System.out.println(quote.toString());
                }
            }
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Database Stock Service Quotes");
        System.out.println("*********************************************");

        StockService stockService_two = null;
        // create a StockService instance using the ServiceFactory static factory method
        try {
            stockService_two = ServiceFactory.getStockServiceInstance(ServiceType.DATABASE);
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        try {
            // Print a single stock quote to the console from the database
            System.out.println("Print a single stock quote");
            System.out.println(stockService_two.getQuote(app.symbol, startDate).toString());
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        try {
            // Print multiple stock quotes that fall between both date ranges
            System.out.println("Print multiple stock quotes for the given date range");
            List<StockQuote> stockList_two = stockService_two.getQuote(app.symbol, startDate, endDate);

            for (StockQuote quote : stockList_two) {
                System.out.println(quote.toString());
            }
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // Print stock quotes from the Yahoo Finance API
        StockService stockService_three = null;
        System.out.println("Print a single stock quote using the Yahoo Finance API");
        try {
            stockService_three = ServiceFactory.getStockServiceInstance(ServiceType.WEB);
            System.out.println(stockService_three.getQuote(app.symbol, startDate).toString());
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Print multiple stock quotes from the Yahoo Finance API");
        try {

            List<StockQuote> stockList_three = stockService_three.getQuote(app.symbol, startDate, endDate);
            for (StockQuote quote : stockList_three) {
                System.out.println(quote.toString());
            }
        } catch (StockServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
