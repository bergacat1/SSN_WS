
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sun Dec 27 18:43:45 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getFieldByIdResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFieldByIdResponse", namespace = "http://ws.ssn/")

public class GetFieldByIdResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<ssn.beans.Field> _return;

    public ssn.beans.Result<ssn.beans.Field> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<ssn.beans.Field> new_return)  {
        this._return = new_return;
    }

}

