package net.nortlam.saruman.service;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Path("ping")
public class PingService {

    private static final Logger LOG = Logger.getLogger(PingService.class.getName());
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response ping() {
        return Response.ok().build();
    }
    
    @GET @Path("database")
    public Response database() {
        try {
            // A simple request to see if it works
            entityManager.createQuery("select user from User user")
                    .setMaxResults(1).getResultList();
        } catch(Exception e) {
            LOG.severe("### PingService.database() UNABLE TO REACH DATABASE");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return Response.ok().build();
    }

}
