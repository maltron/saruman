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
@XmlRootElement(name = "City")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="City") 
@Table(name = "CITY", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "REGION_ID"}))
@NamedQueries({
   @NamedQuery(name=City.FIND_ALL, query="SELECT city FROM City city ORDER BY city.name"),
   @NamedQuery(name=City.FIND_BY_NAME, query="SELECT city FROM City city WHERE city.name=:NAME")
})
public class City implements Serializable {

    private static final Logger LOG = Logger.getLogger(City.class.getName());

    public static final String FIND_ALL = "City.findAll()";
    public static final String FIND_BY_NAME = "City.findByName()";
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CITY_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_NAME = 100;
    @Column(name="NAME", nullable = false, columnDefinition = "VARCHAR(100)", length = LENGTH_NAME, insertable = false, updatable = false)
    @XmlAttribute(name="Name", required=true)
    private String name;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="REGION_ID", nullable=false, columnDefinition="BIGINT", insertable=false, updatable=false)
    @XmlElement(name="Region", type=Region.class, required=true)
    private Region region;
    
    public City() {
    }
    
    public City(String name, Region region) {
        setName(name); setRegion(region);
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.region);
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
        final City other = (City) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        return true;
    }
}
