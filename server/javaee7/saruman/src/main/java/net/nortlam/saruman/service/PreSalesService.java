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

import net.nortlam.saruman.entity.PreSales;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/presales")
public class PreSalesService {

    private static final Logger LOG = Logger.getLogger(PreSalesService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<PreSales> result = (Collection<PreSales>)entityManager.createNamedQuery(PreSales.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<PreSales>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        PreSales foundPreSales = entityManager.find(PreSales.class, ID);
        if(foundPreSales == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundPreSales).build();
    }
    
    @PUT
    public Response addPreSales(PreSales newPreSales) {
        try {
            entityManager.persist(newPreSales);
        } catch(Exception e) {
            LOG.severe("### PreSalesService.addPreSales() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newPreSales).build();
    }
    
    @POST
    public Response updatePreSales(PreSales presales) {
        PreSales existing = null;
        try {
            existing = entityManager.find(PreSales.class, presales.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(presales.getName());
            
        } catch(Exception e) {
            LOG.severe("### PreSalesService.updatePreSales() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deletePreSales(@PathParam("ID")long ID) {
        PreSales presales = entityManager.find(PreSales.class, ID);
        if(presales == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(presales);
        } catch(Exception e) {
            LOG.severe("### PreSalesService.deletePreSales() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(presales).build();
    }

}
