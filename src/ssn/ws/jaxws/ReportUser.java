
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

@XmlRootElement(name = "reportUser", namespace = "http://ws.ssn/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reportUser", namespace = "http://ws.ssn/")

public class ReportUser {

    @XmlElement(name = "report")
    private ssn.beans.Report report;

    public ssn.beans.Report getReport() {
        return this.report;
    }

    public void setReport(ssn.beans.Report newReport)  {
        this.report = newReport;
    }

}

