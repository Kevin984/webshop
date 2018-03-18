
package ic.webshop.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OrderNumberImplService", targetNamespace = "http://Service.SOAP/", wsdlLocation = "https://webshopsoap.herokuapp.com/ws/ordernumber?wsdl")
public class OrderNumberImplService
    extends Service
{

    private final static URL ORDERNUMBERIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException ORDERNUMBERIMPLSERVICE_EXCEPTION;
    private final static QName ORDERNUMBERIMPLSERVICE_QNAME = new QName("http://Service.SOAP/", "OrderNumberImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://webshopsoap.herokuapp.com/ws/ordernumber?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ORDERNUMBERIMPLSERVICE_WSDL_LOCATION = url;
        ORDERNUMBERIMPLSERVICE_EXCEPTION = e;
    }

    public OrderNumberImplService() {
        super(__getWsdlLocation(), ORDERNUMBERIMPLSERVICE_QNAME);
    }

    public OrderNumberImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ORDERNUMBERIMPLSERVICE_QNAME, features);
    }

    public OrderNumberImplService(URL wsdlLocation) {
        super(wsdlLocation, ORDERNUMBERIMPLSERVICE_QNAME);
    }

    public OrderNumberImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ORDERNUMBERIMPLSERVICE_QNAME, features);
    }

    public OrderNumberImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrderNumberImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OrderNumber
     */
    @WebEndpoint(name = "OrderNumberImplPort")
    public OrderNumber getOrderNumberImplPort() {
        return super.getPort(new QName("http://Service.SOAP/", "OrderNumberImplPort"), OrderNumber.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OrderNumber
     */
    @WebEndpoint(name = "OrderNumberImplPort")
    public OrderNumber getOrderNumberImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Service.SOAP/", "OrderNumberImplPort"), OrderNumber.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ORDERNUMBERIMPLSERVICE_EXCEPTION!= null) {
            throw ORDERNUMBERIMPLSERVICE_EXCEPTION;
        }
        return ORDERNUMBERIMPLSERVICE_WSDL_LOCATION;
    }

}
