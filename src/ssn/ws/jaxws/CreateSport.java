
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Dec 05 16:16:00 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "createSport", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createSport", namespace = "http://ws.ssn/")

public class CreateSport {

    @XmlElement(name = "arg0")
    private ssn.beans.Sport arg0;

    public ssn.beans.Sport getArg0() {
        return this.arg0;
    }

    public void setArg0(ssn.beans.Sport newArg0)  {
        this.arg0 = newArg0;
    }

}

