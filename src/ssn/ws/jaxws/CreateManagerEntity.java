
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

@XmlRootElement(name = "createManagerEntity", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createManagerEntity", namespace = "http://ws.ssn/")

public class CreateManagerEntity {

    @XmlElement(name = "managerentity")
    private ssn.beans.ManagerEntity managerentity;

    public ssn.beans.ManagerEntity getManagerentity() {
        return this.managerentity;
    }

    public void setManagerentity(ssn.beans.ManagerEntity newManagerentity)  {
        this.managerentity = newManagerentity;
    }

}

