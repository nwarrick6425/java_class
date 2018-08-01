package edu.nicholaidudakiwwarrick.advancedjava.servlet;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockSearch;
import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Nicholai Dudakiw-Warrick
 */
public class StockSearchServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static StockSearchServlet servlet;
    private static final String SYMBOL_PARAMETER_KEY = "symbol";
    private static final String STARTDATE_PARAMETER_KEY = "startDate";
    private static final String ENDDATE_PARAMETER_KEY = "endDate";
    private static final String INTERVAL_PARAMETER_KEY = "interval";
    private static final String SERVICETYPE_PARAMETER_KEY = "serviceType";

    private static WebDriver driver;
    WebElement element;

    @BeforeClass
    public static void openBrowser() throws Exception {
        System.setProperty("webdriver.gecko.driver", "C:\\\\Users\\nwarr\\Downloads\\geckodriver-v0.21.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Sets up the logic common to each test in this class
     */
    @Before
    public final void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter("symbol"))
                .thenReturn("AAPL");

        when(request.getParameter("startDate"))
                .thenReturn("2016-04-23 00:00:00");

        when(request.getParameter("endDate"))
                .thenReturn("2016-07-23 00:00:00");

        when(request.getParameter("interval"))
                .thenReturn("DAY");

        when(request.getParameter("serviceType"))
                .thenReturn("WEB");

        String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
        String startRange = request.getParameter(STARTDATE_PARAMETER_KEY);
        String endRange = request.getParameter(ENDDATE_PARAMETER_KEY);
        String interval = request.getParameter(INTERVAL_PARAMETER_KEY);
        String serviceType = request.getParameter(SERVICETYPE_PARAMETER_KEY);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher("/stockquoteResults.jsp")).thenReturn(dispatcher);
        servlet = new StockSearchServlet() {
            public ServletContext getServletContext() {
                return servletContext; // return the mock
            }
        };

        StockSearch search = new StockSearch(symbol, startRange, endRange, interval);
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
    }

    /**
     * Verifies that the doPost method throws an exception when passed null arguments
     * @throws ServletException
     * @throws IOException
     */
    @Test(expected = NullPointerException.class)
    public final void testDoPostPositive() throws ServletException, IOException {
        servlet.doPost(null, null);
    }

    /**
     * Verifies that the doPost method runs without exception
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public final void testDoPostNegative() throws ServletException, IOException {
        boolean throwsException = false;
        try {
            servlet.doPost(request, response);
        } catch (Exception e) {
            throwsException = true;
        }
        assertFalse("doPost throws an exception", throwsException);
    }

    public final void testWebServicePositive() {
        driver.get("http://localhost:8080/StockSearchWebApp/index.jsp");
        driver.findElement(By.xpath("//a[@href='stockquote.jsp']")).click();
        driver.findElement(By.name("symbol")).sendKeys("GOOG");
        driver.findElement(By.name("startDate")).sendKeys("2016-04-23 00:00:00");
        driver.findElement(By.name("endDate")).sendKeys("2016-07-23 00:00:00");
        driver.findElement(By.xpath("//input[@value='WEB']")).click();
        driver.findElement(By.xpath("//input[@value='OK']")).click();
        element = driver.findElement(By.xpath("//*[text()='Stock Quote Search Result']"));
        Assert.assertNotNull(element);
    }

    @AfterClass
    public static void closeBrowser(){
        driver.quit();
    }
}
