
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Dec 19 17:40:15 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getManagerEntitiesByIdResponse", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getManagerEntitiesByIdResponse", namespace = "http://ws.ssn/")

public class GetManagerEntitiesByIdResponse {

    @XmlElement(name = "return")
    private ssn.beans.Result<ssn.beans.ManagerEntity> _return;

    public ssn.beans.Result<ssn.beans.ManagerEntity> getReturn() {
        return this._return;
    }

    public void setReturn(ssn.beans.Result<ssn.beans.ManagerEntity> new_return)  {
        this._return = new_return;
    }

}

