
package ssn.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.11.redhat-3
 * Thu Jan 07 20:21:14 CET 2016
 * Generated source version: 2.7.11.redhat-3
 */

@XmlRootElement(name = "addReservation", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addReservation", namespace = "http://ws.ssn/")

public class AddReservation {

    @XmlElement(name = "reservation")
    private ssn.beans.Reservation reservation;

    public ssn.beans.Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(ssn.beans.Reservation newReservation)  {
        this.reservation = newReservation;
    }

}

