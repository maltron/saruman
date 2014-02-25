package net.nortlam.saruman.service;

import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.nortlam.saruman.entity.PTO;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/pto")
public class PTOService {

    private static final Logger LOG = Logger.getLogger(PTOService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<PTO> result = (Collection<PTO>)entityManager.createNamedQuery(PTO.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<PTO>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        PTO foundPTO = entityManager.find(PTO.class, ID);
        if(foundPTO == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundPTO).build();
    }
    
    @PUT
    public Response addPTO(PTO newPTO) {
        try {
            entityManager.persist(newPTO);
        } catch(Exception e) {
            LOG.severe("### PTOService.addPTO() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newPTO).build();
    }
    
    @POST
    public Response updatePTO(PTO pto) {
        PTO existing = null;
        try {
            existing = entityManager.find(PTO.class, pto.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(pto.getName());
            
        } catch(Exception e) {
            LOG.severe("### PTOService.updatePTO() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deletePTO(@PathParam("ID")long ID) {
        PTO pto = entityManager.find(PTO.class, ID);
        if(pto == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(pto);
        } catch(Exception e) {
            LOG.severe("### PTOService.deletePTO() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(pto).build();
    }
}
