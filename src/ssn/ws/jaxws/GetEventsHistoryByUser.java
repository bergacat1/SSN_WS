
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sun Jan 03 17:56:16 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getEventsHistoryByUser", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEventsHistoryByUser", namespace = "http://ws.ssn/")

public class GetEventsHistoryByUser {

    @XmlElement(name = "iduser")
    private int iduser;

    public int getIduser() {
        return this.iduser;
    }

    public void setIduser(int newIduser)  {
        this.iduser = newIduser;
    }

}

