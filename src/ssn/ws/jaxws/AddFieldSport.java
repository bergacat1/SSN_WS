
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

@XmlRootElement(name = "addFieldSport", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addFieldSport", namespace = "http://ws.ssn/", propOrder = {"idfield", "idsport", "priceperhour"})

public class AddFieldSport {

    @XmlElement(name = "idfield")
    private int idfield;
    @XmlElement(name = "idsport")
    private int idsport;
    @XmlElement(name = "priceperhour")
    private double priceperhour;

    public int getIdfield() {
        return this.idfield;
    }

    public void setIdfield(int newIdfield)  {
        this.idfield = newIdfield;
    }

    public int getIdsport() {
        return this.idsport;
    }

    public void setIdsport(int newIdsport)  {
        this.idsport = newIdsport;
    }

    public double getPriceperhour() {
        return this.priceperhour;
    }

    public void setPriceperhour(double newPriceperhour)  {
        this.priceperhour = newPriceperhour;
    }

}

