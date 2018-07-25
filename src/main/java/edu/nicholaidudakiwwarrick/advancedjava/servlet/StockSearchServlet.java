package edu.nicholaidudakiwwarrick.advancedjava.servlet;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockSearch;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is a servlet for a stock quote search web application.
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class StockSearchServlet extends HttpServlet {

    private static final String SYMBOL_PARAMETER_KEY = "symbol";
    private static final String STARTDATE_PARAMETER_KEY = "startDate";
    private static final String ENDDATE_PARAMETER_KEY = "endDate";
    private static final String INTERVAL_PARAMETER_KEY = "interval";
    private static final String SERVICETYPE_PARAMETER_KEY = "serviceType";

    /**
     * Requests access to form submission data and, after gaining access, uses the data to generate a response
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
        String startDate = request.getParameter(STARTDATE_PARAMETER_KEY);
        String endDate = request.getParameter(ENDDATE_PARAMETER_KEY);
        String interval = request.getParameter(INTERVAL_PARAMETER_KEY);
        String serviceType = request.getParameter(SERVICETYPE_PARAMETER_KEY);

        HttpSession session = request.getSession();

        StockSearch search = new StockSearch(symbol, startDate, endDate, interval);
        try {
            switch (serviceType) {
                case ("BASIC"):
                    search.processData(ServiceType.BASIC);
                    break;
                case ("DATABASE"):
                    search.processData(ServiceType.DATABASE);
                    break;
                case ("WEB"):
                    search.processData(ServiceType.WEB);
                    break;
                default:
                    search.processData(ServiceType.WEB);
            }
        } catch (StockServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        session.setAttribute("search", search);

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stocksearchResults.jsp");
        dispatcher.forward(request, response);
    }
}
