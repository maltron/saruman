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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio "Maltron" Leal */
@XmlRootElement(name = "BackOffice")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="BackOffice") @Table(name = "BACKOFFICE")
@NamedQueries({
   @NamedQuery(name=BackOffice.FIND_ALL, query="SELECT backoffice FROM BackOffice backoffice ORDER BY backoffice.name"),
   @NamedQuery(name=BackOffice.FIND_BY_USERNAME, query="SELECT backoffice FROM BackOffice backoffice WHERE backoffice.name=:NAME")
})
public class BackOffice implements Serializable {

    private static final Logger LOG = Logger.getLogger(BackOffice.class.getName());

    public static final String FIND_ALL = "BackOffice.findAll()";
    public static final String FIND_BY_USERNAME = "BackOffice.findByUsername()";
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BACKOFFICE_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_NAME = 100;
    @Column(name="NAME", nullable = false, columnDefinition = "VARCHAR(100)", length = LENGTH_NAME, unique = true, insertable = true, updatable = true)
    @XmlElement(name="Name", type=String.class, required=true)
    private String name;
    
    public BackOffice() {
    }
    
    public BackOffice(String name) {
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
        int hash = 5;
        hash = 41 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final BackOffice other = (BackOffice) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
