
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

@XmlRootElement(name = "getManagerEntitiesByEvent", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getManagerEntitiesByEvent", namespace = "http://ws.ssn/")

public class GetManagerEntitiesByEvent {

    @XmlElement(name = "idevent")
    private int idevent;

    public int getIdevent() {
        return this.idevent;
    }

    public void setIdevent(int newIdevent)  {
        this.idevent = newIdevent;
    }

}

