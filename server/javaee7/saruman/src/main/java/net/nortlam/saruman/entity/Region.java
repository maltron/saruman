package net.nortlam.saruman.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio "Maltron" Leal */
@XmlRootElement(name = "Region")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="Region") 
@Table(name = "REGION", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "COUNTRY_ID"}))
@NamedQueries({
   @NamedQuery(name=Region.FIND_ALL, query="SELECT region FROM Region region ORDER BY region.name"),
   @NamedQuery(name=Region.FIND_BY_NAME, query="SELECT region FROM Region region WHERE region.name=:NAME")
})
public class Region implements Serializable {

    private static final Logger LOG = Logger.getLogger(Region.class.getName());
    
    public static final String FIND_ALL = "Region.findAll()";
    public static final String FIND_BY_NAME = "Region.findByname()";
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REGION_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_NAME = 70;
    @Column(name="NAME", nullable = false, columnDefinition = "VARCHAR(70)", length = LENGTH_NAME, insertable = false, updatable = false)
    @XmlAttribute(name="Name", required=true)
    private String name;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="COUNTRY_ID", nullable=false, columnDefinition="BIGINT", insertable=false, updatable=false)
    @XmlElement(name="Country", type=Country.class, required=true)
    private Country country;
    
    public Region() {
    }
    
    public Region(String name, Country country) {
        setName(name); setCountry(country);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.country);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Region other = (Region) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        return true;
    }
}
