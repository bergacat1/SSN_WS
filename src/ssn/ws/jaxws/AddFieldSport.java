
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

@XmlRootElement(name = "addFieldSport", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addFieldSport", namespace = "http://ws.ssn/", propOrder = {"arg0", "arg1", "arg2"})

public class AddFieldSport {

    @XmlElement(name = "arg0")
    private int arg0;
    @XmlElement(name = "arg1")
    private int arg1;
    @XmlElement(name = "arg2")
    private double arg2;

    public int getArg0() {
        return this.arg0;
    }

    public void setArg0(int newArg0)  {
        this.arg0 = newArg0;
    }

    public int getArg1() {
        return this.arg1;
    }

    public void setArg1(int newArg1)  {
        this.arg1 = newArg1;
    }

    public double getArg2() {
        return this.arg2;
    }

    public void setArg2(double newArg2)  {
        this.arg2 = newArg2;
    }

}

