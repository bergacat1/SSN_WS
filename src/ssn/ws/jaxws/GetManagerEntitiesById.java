
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Mon Dec 28 22:39:37 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getManagerEntitiesById", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getManagerEntitiesById", namespace = "http://ws.ssn/")

public class GetManagerEntitiesById {

    @XmlElement(name = "idmanagerentity")
    private int idmanagerentity;

    public int getIdmanagerentity() {
        return this.idmanagerentity;
    }

    public void setIdmanagerentity(int newIdmanagerentity)  {
        this.idmanagerentity = newIdmanagerentity;
    }

}

