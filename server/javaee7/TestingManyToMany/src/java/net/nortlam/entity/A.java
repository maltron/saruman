package net.nortlam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Entity(name="A")
@Table(name="A")
public class A implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT")
    private int ID;
    
    @Column(name="value", columnDefinition = "INT", insertable = true, updatable = true)
    private int value;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AB", uniqueConstraints = @UniqueConstraint(columnNames = {"A_ID", "B_ID"}),
            joinColumns = @JoinColumn(name = "A_ID", columnDefinition = "INT", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name="B_ID", columnDefinition = "INT", referencedColumnName = "ID"))
    private Collection<B> collectionB;
    
    public A() {
    }
    
    public A(int value) {
        setValue(value);
        collectionB = new ArrayList<B>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Collection<B> getCollectionB() {
        return collectionB;
    }

    public void setCollectionB(Collection<B> collectionB) {
        this.collectionB = collectionB;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.ID;
        hash = 31 * hash + this.value;
        hash = 31 * hash + Objects.hashCode(this.collectionB);
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
        final A other = (A) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.collectionB, other.collectionB)) {
            return false;
        }
        return true;
    }
}
