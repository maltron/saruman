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
@XmlRootElement(name = "Role")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="Role") @Table(name = "ROLE")
@NamedQueries({
   @NamedQuery(name=Role.FIND_ALL, query="SELECT role FROM Role role ORDER BY role.name"),
   @NamedQuery(name=Role.FIND_BY_NAME, query="SELECT role FROM Role role WHERE role.name=:NAME")
})
public class Role implements Serializable {

    private static final Logger LOG = Logger.getLogger(Role.class.getName());
    
    public static final String FIND_ALL = "Role.findAll()";
    public static final String FIND_BY_NAME = "Role.findByName()";
    
    // Types of Roles
    public enum Type {
        ADMINSTRATOR("admin"), USER("user");
        
        private String value;
        Type(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROLE_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_NAME = 20;
    @Column(name="NAME", nullable = false, columnDefinition = "VARCHAR(20)", length = LENGTH_NAME, unique = true, insertable = true, updatable = true)
    @XmlAttribute(name="Name", required=true)
    private String name;
    
    public Role() {
    }
    
    public Role(String name) {
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
        int hash = 7;
        hash = 97 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Role other = (Role) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
