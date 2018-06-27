package edu.nicholaidudakiwwarrick.advancedjava.app;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockService;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceFactory;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Basic class with a Main method to create StockService instances
 */
public class StockQuoteApplication {
    /**
     * @param args an array that contains elements:
     *             <code>String</code> representation of stock symbol, start date, and end date
     */
    public static void main(@NotNull String[] args) {

        StockService stockService = null;

        // create a StockService instance using the StockServiceFactory static factory method
        try {
            stockService = StockServiceFactory.getInstance(ServiceType.BASIC);
        } catch (StockServiceException e) {
            e.getMessage();
        }

        // Retrieve date formatter and parse the 2nd and 3rd date args
        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startDate = DateTime.parse(args[1], formatter);
        DateTime endDate = DateTime.parse(args[2], formatter);

        System.out.println("Basic Stock Service Quotes");
        System.out.println("****************************************");
        try {
            // Print a single stock quote to the console
            System.out.println("Print a single stock quote");
            System.out.println(stockService.getQuote(args[0], startDate).toString());
        } catch (StockServiceException e) {
            e.getMessage();
        }

        try {
            // Print multiple stock quotes that fall between both date ranges
            System.out.println("Print multiple stock quotes for the given date range");
            List<StockQuote> stockList = stockService.getQuote(args[0], startDate, endDate);

            for (StockQuote quote : stockList) {
                System.out.println(quote.toString());
            }
        } catch (StockServiceException e) {
            e.getMessage();
        }
        // Print multiple stock quotes between the period at the given interval
        System.out.println("Print multiple stock quotes for the given date range at the given interval");
        if(args[3].toUpperCase().equals("HOURLY")) {
            List<StockQuote> intervalStockList =
                    stockService.getQuote(args[0], startDate, endDate, IntervalEnum.HOURLY);

            for (StockQuote quote : intervalStockList) {
                System.out.println(quote.toString());
            }
        } else if(args[3].toUpperCase().equals("HALF_DAY")) {
            List<StockQuote> intervalStockList =
                    stockService.getQuote(args[0], startDate, endDate, IntervalEnum.HALF_DAY);

            for (StockQuote quote : intervalStockList) {
                System.out.println(quote.toString());
            }
        } else if(args[3].toUpperCase().equals("DAILY")) {
            List<StockQuote> intervalStockList =
                    stockService.getQuote(args[0], startDate, endDate, IntervalEnum.DAILY);

            for (StockQuote quote : intervalStockList) {
                System.out.println(quote.toString());
            }
        }

        System.out.println("Database Stock Service Quotes");
        System.out.println("*********************************************");

        StockService stockService_two = null;
        // create a StockService instance using the StockServiceFactory static factory method
        try {
            stockService_two = StockServiceFactory.getInstance(ServiceType.DATABASE);
        } catch (StockServiceException e) {
            e.getMessage();
        }

        try {
            // Print a single stock quote to the console from the database
            System.out.println("Print a single stock quote");
            System.out.println(stockService_two.getQuote(args[0], startDate).toString());
        } catch (StockServiceException e) {
            e.getMessage();
        }

        try {
            // Print multiple stock quotes that fall between both date ranges
            System.out.println("Print multiple stock quotes for the given date range");
            List<StockQuote> stockList_two = stockService_two.getQuote(args[0], startDate, endDate);

            for (StockQuote quote : stockList_two) {
                System.out.println(quote.toString());
            }
        } catch (StockServiceException e) {
            e.getMessage();
        }





    }

}
