package edu.nicholaidudakiwwarrick.advancedjava;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Basic class with a Main method to create StockService instances
 */
public class StockQuoteApplication {
    /**
     * @param args an array that contains elements:
     *             <code>String</code> representation of stock symbol, start date, and end date
     */
    public static void main(String[] args) {

        // create a StockService instance using the StockServiceFactory static factory method
        StockService basicStockService = StockServiceFactory.newInstance();

        // Retrieve date formatter and parse the 2nd and 3rd date args
        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startDate = DateTime.parse(args[1], formatter);
        DateTime endDate = DateTime.parse(args[2], formatter);

        // Print a single stock quote to the console
        System.out.println("Print a single stock quote");
        System.out.println(basicStockService.getQuote(args[0], startDate).toString());

        // Print multiple stock quotes that fall between both date ranges
        System.out.println("Print multiple stock quotes for the given date range");
        List<StockQuote> stockList = basicStockService.getQuote(args[0], startDate, endDate);

        for (StockQuote quote : stockList) {
            System.out.println(quote.toString());
        }

    }

}
