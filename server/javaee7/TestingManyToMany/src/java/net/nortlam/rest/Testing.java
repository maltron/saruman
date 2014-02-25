package net.nortlam.rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import net.nortlam.entity.A;
import net.nortlam.entity.B;

@Stateless
@Path("/test")
public class Testing {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @GET @Path("1")
    public Response t1() {
        
        
        A a1 = new A(1);
        a1.getCollectionB().add(b1);
        a1.getCollectionB().add(b2);
        a1.getCollectionB().add(b3);
        entityManager.persist(a1);
        
        return Response.ok().build();
    }
    
    private B findCity(String city) {
        B b = null;
        try {
            b = entityManager.createQuery("findCity", B.class)
                                .setParameter("CITY", city).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
        
        return b;
    }

} 
