
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Dec 05 15:20:43 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getSportByIdResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSportByIdResponse", namespace = "http://ws.ssn/")

public class GetSportByIdResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<ssn.beans.Sport> _return;

    public ssn.beans.Result<ssn.beans.Sport> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<ssn.beans.Sport> new_return)  {
        this._return = new_return;
    }

}

