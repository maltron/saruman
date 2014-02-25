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

/**
 *
 * @author Mauricio "Maltron" Leal */
@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="Task") 
//@Table(name = "TASK", uniqueConstraints = @UniqueConstraint(columnNames = {"QUARTER", "WEEK", "USER_ID"}))
@Table(name = "TASK")
@NamedQueries({
   @NamedQuery(name=Task.FIND_ALL_BY_USER, query="SELECT task FROM Task task WHERE task.user.ID=:USER ORDER BY task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_QUARTER, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK ORDER BY task.user, task.quarter, task.week"),   

   @NamedQuery(name=Task.GROUP_BY_TRAVELLING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.city IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_CUSTOMER_ENGAGING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.customer IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_PARTNER, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.partner IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_RESEARCHING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.technology IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_PRESALES, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.preSales IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_POSTSALES, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.postSales IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_BACKOFFICE, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.backOffice IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.GROUP_BY_PTO, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.pto IS NOT NULL ORDER BY task.user, task.quarter, task.week"),
   
   @NamedQuery(name=Task.FIND_BY_TRAVELLING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.city IS NOT NULL AND task.city.ID=:CITY ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_CUSTOMER_ENGAGING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.customer IS NOT NULL AND task.customer.ID=:CUSTOMER ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_PARTNER, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.partner IS NOT NULL AND task.partner.ID=:PARTNER ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_RESEARCHING, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.technology IS NOT NULL AND task.technology.ID=:TECHNOLOGY ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_PRESALES, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.preSales IS NOT NULL AND task.preSales.ID=:PRESALES ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_POSTSALES, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.postSales IS NOT NULL AND task.postSales.ID=:POSTSALES ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_BACKOFFICE, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.backOffice IS NOT NULL AND task.backOffice.ID=:BACKOFFICE ORDER BY task.user, task.quarter, task.week"),
   @NamedQuery(name=Task.FIND_BY_PTO, query="SELECT task FROM Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK AND task.pto IS NOT NULL AND task.pto.ID=:PTO ORDER BY task.user, task.quarter, task.week"),
   
   @NamedQuery(name=Task.DELETE_QUARTER_WEEK, query="DELETE from Task task WHERE task.user.ID=:USER AND task.quarter=:QUARTER AND task.week=:WEEK")
})
public class Task implements Serializable {

    private static final Logger LOG = Logger.getLogger(Task.class.getName());
    
    public static final String FIND_ALL_BY_USER = "Task.findAllByUser()";
    public static final String FIND_BY_QUARTER = "Task.findByQuarter()";
    
    public static final String GROUP_BY_TRAVELLING = "Task.groupByTravelling()";
    public static final String GROUP_BY_CUSTOMER_ENGAGING = "Task.groupByCustomerEngaging()";
    public static final String GROUP_BY_PARTNER = "Task.groupByPartner()";
    public static final String GROUP_BY_RESEARCHING = "Task.groupByResearching()";
    public static final String GROUP_BY_PRESALES = "Task.groupByPreSales()";
    public static final String GROUP_BY_POSTSALES = "Task.groupByPostSales()";
    public static final String GROUP_BY_BACKOFFICE = "Task.groupByBackOffice()";
    public static final String GROUP_BY_PTO = "Task.groupByPTO()";
    
    public static final String FIND_BY_TRAVELLING = "Task.findByTravelling()";
    public static final String FIND_BY_CUSTOMER_ENGAGING = "Task.findByCustomer()";
    public static final String FIND_BY_PARTNER = "Task.findByPartner()";
    public static final String FIND_BY_RESEARCHING = "Task.findByResearching()";
    public static final String FIND_BY_PRESALES = "Task.findByPreSales()";
    public static final String FIND_BY_POSTSALES = "Task.findByPostSales()";
    public static final String FIND_BY_BACKOFFICE = "Task.findByBackOffice()";
    public static final String FIND_BY_PTO = "Task.findByPTO()";
    
    public static final String DELETE_QUARTER_WEEK = "Task.deleteQuarterWeek()";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TASK_ID", nullable = false, columnDefinition = "BIGINT", unique = false, insertable = true)
    @XmlAttribute(name = "ID", required = true)
    private long ID;
    
    @Column(name="QUARTER", nullable = false, columnDefinition = "INT", insertable = true, updatable = true)
    @XmlAttribute(name="Quarter", required = true)
    private int quarter;
    
    @Column(name="WEEK", nullable = false, columnDefinition = "INT", insertable = true, updatable = true)
    @XmlAttribute(name="Week", required=true)
    private int week;
    
    @Column(name="AMOUNT", nullable = false, columnDefinition = "INT", insertable = true, updatable = true)
    @XmlAttribute(name="Amount", required=true)
    private int amount;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="USER_ID", nullable=false, columnDefinition="BIGINT", insertable=true, updatable=true)
    @XmlElement(name="User", type=User.class, required=true)
    private User user;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CITY_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="City", type=City.class, required=false)
    private City city;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CUSTOMER_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="Customer", type=Customer.class, required=false)
    private Customer customer;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PARTNER_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="Partner", type=Partner.class, required=false)
    private Partner partner;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TECHNOLOGY_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="Technology", type=Technology.class, required=false)
    private Technology technology;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PRESALES_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="PreSales", type=PreSales.class, required=false)
    private PreSales preSales;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="POSTSALES_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="PostSales", type=PostSales.class, required=false)
    private PostSales postSales;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="BACKOFFICE_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="BackOffice", type=BackOffice.class, required=false)
    private BackOffice backOffice;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PTO_ID", nullable = true, columnDefinition = "BIGINT", insertable = true, updatable = true)
    @XmlElement(name="PTO", type=PTO.class, required=false)
    private PTO pto;
    
    public Task() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public PreSales getPreSales() {
        return preSales;
    }

    public void setPreSales(PreSales preSales) {
        this.preSales = preSales;
    }

    public PostSales getPostSales() {
        return postSales;
    }

    public void setPostSales(PostSales postSales) {
        this.postSales = postSales;
    }

    public BackOffice getBackOffice() {
        return backOffice;
    }

    public void setBackOffice(BackOffice backOffice) {
        this.backOffice = backOffice;
    }

    public PTO getPTO() {
        return pto;
    }

    public void setPTO(PTO pto) {
        this.pto = pto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 59 * hash + this.quarter;
        hash = 59 * hash + this.week;
        hash = 59 * hash + this.amount;
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.city);
        hash = 59 * hash + Objects.hashCode(this.customer);
        hash = 59 * hash + Objects.hashCode(this.partner);
        hash = 59 * hash + Objects.hashCode(this.technology);
        hash = 59 * hash + Objects.hashCode(this.preSales);
        hash = 59 * hash + Objects.hashCode(this.postSales);
        hash = 59 * hash + Objects.hashCode(this.backOffice);
        hash = 59 * hash + Objects.hashCode(this.pto);
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
        final Task other = (Task) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.quarter != other.quarter) {
            return false;
        }
        if (this.week != other.week) {
            return false;
        }
        if (this.amount != other.amount) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.partner, other.partner)) {
            return false;
        }
        if (!Objects.equals(this.technology, other.technology)) {
            return false;
        }
        if (!Objects.equals(this.preSales, other.preSales)) {
            return false;
        }
        if (!Objects.equals(this.postSales, other.postSales)) {
            return false;
        }
        if (!Objects.equals(this.backOffice, other.backOffice)) {
            return false;
        }
        if (!Objects.equals(this.pto, other.pto)) {
            return false;
        }
        return true;
    }
}
