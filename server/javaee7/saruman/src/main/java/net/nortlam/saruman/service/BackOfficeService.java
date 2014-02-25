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

import net.nortlam.saruman.entity.BackOffice;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/backoffice")
public class BackOfficeService {

    private static final Logger LOG = Logger.getLogger(BackOfficeService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<BackOffice> result = (Collection<BackOffice>)entityManager.createNamedQuery(BackOffice.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<BackOffice>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        BackOffice foundBackOffice = entityManager.find(BackOffice.class, ID);
        if(foundBackOffice == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundBackOffice).build();
    }
    
    @PUT
    public Response addBackOffice(BackOffice newBackOffice) {
        try {
            entityManager.persist(newBackOffice);
        } catch(Exception e) {
            LOG.severe("### BackOfficeService.addBackOffice() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newBackOffice).build();
    }
    
    @POST
    public Response updateBackOffice(BackOffice backoffice) {
        BackOffice existing = null;
        try {
            existing = entityManager.find(BackOffice.class, backoffice.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(backoffice.getName());
            
        } catch(Exception e) {
            LOG.severe("### BackOfficeService.updateBackOffice() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deleteBackOffice(@PathParam("ID")long ID) {
        BackOffice backoffice = entityManager.find(BackOffice.class, ID);
        if(backoffice == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(backoffice);
        } catch(Exception e) {
            LOG.severe("### BackOfficeService.deleteBackOffice() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(backoffice).build();
    }



}
