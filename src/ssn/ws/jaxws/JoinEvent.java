
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Mon Dec 28 22:39:38 CET 2015
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "joinEvent", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "joinEvent", namespace = "http://ws.ssn/", propOrder = {"iduser", "idevent"})

public class JoinEvent {

    @XmlElement(name = "iduser")
    private int iduser;
    @XmlElement(name = "idevent")
    private int idevent;

    public int getIduser() {
        return this.iduser;
    }

    public void setIduser(int newIduser)  {
        this.iduser = newIduser;
    }

    public int getIdevent() {
        return this.idevent;
    }

    public void setIdevent(int newIdevent)  {
        this.idevent = newIdevent;
    }

}

