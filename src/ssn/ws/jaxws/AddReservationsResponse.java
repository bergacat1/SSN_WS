
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sun Jan 03 17:56:17 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "addReservationsResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addReservationsResponse", namespace = "http://ws.ssn/")

public class AddReservationsResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<java.lang.Integer> _return;

    public ssn.beans.Result<java.lang.Integer> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<java.lang.Integer> new_return)  {
        this._return = new_return;
    }

}

