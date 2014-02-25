package net.nortlam.saruman.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio "Maltron" Leal */
@XmlRootElement(name = "Country")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="Country") @Table(name = "COUNTRY")
@NamedQueries({
   @NamedQuery(name=Country.FIND_ALL, query="SELECT country FROM Country country ORDER BY country.name"),
   @NamedQuery(name=Country.FIND_BY_NAME, query="SELECT country FROM Country country WHERE country.name=:NAME")
})
public class Country implements Serializable {

    private static final Logger LOG = Logger.getLogger(Country.class.getName());
    
    public static final String FIND_ALL = "Country.findAll()";
    public static final String FIND_BY_NAME = "Country.findByName()";
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COUNTRY_ID", nullable = false, columnDefinition = "BIGINT", insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_NAME = 20;
    @Column(name="NAME", nullable = false, columnDefinition = "VARCHAR(50)", length = LENGTH_NAME, unique = true, insertable = true, updatable = true)
    @XmlAttribute(name="Name", required=true)
    private String name;
    
    public Country() {
    }
    
    public Country(String name) {
        setName(name);
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final Country other = (Country) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
