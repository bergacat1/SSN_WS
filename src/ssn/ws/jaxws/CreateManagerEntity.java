
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Dec 05 16:15:59 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "createManagerEntity", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createManagerEntity", namespace = "http://ws.ssn/")

public class CreateManagerEntity {

    @XmlElement(name = "arg0")
    private ssn.beans.ManagerEntity arg0;

    public ssn.beans.ManagerEntity getArg0() {
        return this.arg0;
    }

    public void setArg0(ssn.beans.ManagerEntity newArg0)  {
        this.arg0 = newArg0;
    }

}

