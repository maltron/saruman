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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="User") @Table(name = "USER")
@NamedQueries({
   @NamedQuery(name=User.FIND_ALL, query="SELECT user FROM User user ORDER BY user.username"),
   @NamedQuery(name=User.FIND_BY_USERNAME, query="SELECT user FROM User user WHERE user.username=:USERNAME")
})
public class User implements Serializable {

    private static final Logger LOG = Logger.getLogger(User.class.getName());
    
    public static final String FIND_ALL = "User.findAll()";
    public static final String FIND_BY_USERNAME = "User.findByUsername()";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    public static final int LENGTH_USERNAME = 35;
    @Column(name="USERNAME", nullable = false, columnDefinition = "VARCHAR(35)", length = LENGTH_USERNAME, unique = true, insertable = true, updatable = true)
    @XmlElement(name="Username", type=String.class, required=true)
    private String username;
    
    public static final int LENGTH_PASSWORD = 40;
    @Column(name="PASSWORD", nullable = false, columnDefinition = "VARCHAR(40)", length = LENGTH_PASSWORD, unique = false, insertable = true, updatable = true)
    @XmlElement(name="Password", type=String.class, required=true)
    private String password;
    
    public static final int LENGTH_FULLNAME = 40;
    @Column(name="FULLNAME", nullable = true, columnDefinition = "VARCHAR(150)", length = LENGTH_FULLNAME, unique = false, insertable = true, updatable = true)
    @XmlElement(name="Fullname", type=String.class, required=false)
    private String fullName;
    
    @Column(name="ENABLED", nullable = false, columnDefinition = "TINYINT", unique = false, insertable = false, updatable = true)
    @XmlElement(name="Enabled", type=boolean.class, required=true)
    private boolean enabled;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ROLE_ID", nullable=true, columnDefinition="BIGINT", unique=false, insertable=false, updatable=true)
    @XmlElement(name="Role", type=Role.class, required=false)
    private Role role;
    
    public User() {
    }
    
    public User(String username, String password, String fullName) {
        setUsername(username); 
        setPassword(password);
        setFullName(fullName);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.fullName);
        hash = 97 * hash + Objects.hashCode(this.role);
        hash = 97 * hash + Objects.hashCode(this.enabled);
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
        final User other = (User) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.enabled, other.enabled)) {
            return false;
        }
        return true;
    }
}
