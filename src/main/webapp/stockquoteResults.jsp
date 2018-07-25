<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--

This example JSP uses JTSL rather than scriplets to access data.

In the StockSearch servlet, an instance of a StockSearch is placed in the http session.

The code on this page gets that instance of out the session and uses'
it values to determine what to display to the user.

--%>

<%-- get the StockSearch instance out of the session context --%>
<jsp:useBean id="search" class="edu.nicholaidudakiwwarrick.advancedjava.model.StockSearch" scope="session">
    <c:set target='${search}'  value='${sessionScope.get("search")}'/>
</jsp:useBean>

<html>
<head>
    <title>Stock Quote Search Result</title>
</head>
<body>

Here is the result of your stock search: <br />
<c:out value="${search.quoteStr}"/>

<p>
    To start another stock quote search, click on the following link: <a href="' + document.referrer + '">Go Back </a>
</p>

</body>
</html>
