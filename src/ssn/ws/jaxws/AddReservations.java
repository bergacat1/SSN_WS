
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

@XmlRootElement(name = "addReservations", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addReservations", namespace = "http://ws.ssn/")

public class AddReservations {

    @XmlElement(name = "reservations")
    private java.util.List<ssn.beans.Reservation> reservations;

    public java.util.List<ssn.beans.Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(java.util.List<ssn.beans.Reservation> newReservations)  {
        this.reservations = newReservations;
    }

}

