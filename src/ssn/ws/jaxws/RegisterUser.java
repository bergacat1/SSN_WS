
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

@XmlRootElement(name = "registerUser", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerUser", namespace = "http://ws.ssn/")

public class RegisterUser {

    @XmlElement(name = "user")
    private ssn.beans.User user;

    public ssn.beans.User getUser() {
        return this.user;
    }

    public void setUser(ssn.beans.User newUser)  {
        this.user = newUser;
    }

}

