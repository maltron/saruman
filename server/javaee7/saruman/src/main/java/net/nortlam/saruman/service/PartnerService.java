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

import net.nortlam.saruman.entity.Partner;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/partner")
public class PartnerService {

    private static final Logger LOG = Logger.getLogger(PartnerService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<Partner> result = (Collection<Partner>)entityManager.createNamedQuery(Partner.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Partner>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        Partner foundPartner = entityManager.find(Partner.class, ID);
        if(foundPartner == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundPartner).build();
    }
    
    @PUT
    public Response addPartner(Partner newPartner) {
        try {
            entityManager.persist(newPartner);
        } catch(Exception e) {
            LOG.severe("### PartnerService.addPartner() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newPartner).build();
    }
    
    @POST
    public Response updatePartner(Partner partner) {
        Partner existing = null;
        try {
            existing = entityManager.find(Partner.class, partner.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(partner.getName());
            
        } catch(Exception e) {
            LOG.severe("### PartnerService.updatePartner() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deletePartner(@PathParam("ID")long ID) {
        Partner partner = entityManager.find(Partner.class, ID);
        if(partner == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(partner);
        } catch(Exception e) {
            LOG.severe("### PartnerService.deletePartner() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(partner).build();
    }
}
