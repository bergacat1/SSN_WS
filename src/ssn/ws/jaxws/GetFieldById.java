
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Sat Jan 09 18:29:29 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "getFieldById", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFieldById", namespace = "http://ws.ssn/")

public class GetFieldById {

    @XmlElement(name = "idfield")
    private int idfield;

    public int getIdfield() {
        return this.idfield;
    }

    public void setIdfield(int newIdfield)  {
        this.idfield = newIdfield;
    }

}

