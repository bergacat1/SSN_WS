
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Thu Jan 07 20:21:14 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getUsersByEventResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUsersByEventResponse", namespace = "http://ws.ssn/")

public class GetUsersByEventResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<ssn.beans.User> _return;

    public ssn.beans.Result<ssn.beans.User> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<ssn.beans.User> new_return)  {
        this._return = new_return;
    }

}

