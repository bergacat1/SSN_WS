
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Jan 09 18:29:30 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "updateFieldResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateFieldResponse", namespace = "http://ws.ssn/")

public class UpdateFieldResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<java.lang.Integer> _return;

    public ssn.beans.Result<java.lang.Integer> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<java.lang.Integer> new_return)  {
        this._return = new_return;
    }

}

