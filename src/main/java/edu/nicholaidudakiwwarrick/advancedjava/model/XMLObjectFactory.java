
package edu.nicholaidudakiwwarrick.advancedjava.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the model package.
 * <p>An XMLObjectFactory allows you to programatically
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class XMLObjectFactory implements XMLDomainObject {

    private final static QName _Stocks_QNAME = new QName("", "stocks");

    /**
     * Create a new XMLObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public XMLObjectFactory() {
    }

    /**
     * Create an instance of {@link XMLStockQuoteList }
     * 
     */
    public XMLStockQuoteList createXMLStockQuoteList() {
        return new XMLStockQuoteList();
    }

    /**
     * Create an instance of {@link XMLStockQuote }
     * 
     */
    public XMLStockQuote createXMLStockQuote() {
        return new XMLStockQuote();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLStockQuoteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "stocks")
    public JAXBElement<XMLStockQuoteList> createStocks(XMLStockQuoteList value) {
        return new JAXBElement<XMLStockQuoteList>(_Stocks_QNAME, XMLStockQuoteList.class, null, value);
    }

}
