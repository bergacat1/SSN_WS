
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sun Jan 03 17:56:15 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "updateFieldSportResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateFieldSportResponse", namespace = "http://ws.ssn/")

public class UpdateFieldSportResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result _return;

    public ssn.beans.Result getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result new_return)  {
        this._return = new_return;
    }

}

