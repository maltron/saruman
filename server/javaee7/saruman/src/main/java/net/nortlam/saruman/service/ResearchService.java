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
import net.nortlam.saruman.entity.Technology;


/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/research")
public class ResearchService {

    private static final Logger LOG = Logger.getLogger(ResearchService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<Technology> result = (Collection<Technology>)entityManager.createNamedQuery(Technology.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Technology>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        Technology foundTechnology = entityManager.find(Technology.class, ID);
        if(foundTechnology == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundTechnology).build();
    }
    
    @PUT
    public Response addTechnology(Technology newTechnology) {
        try {
            entityManager.persist(newTechnology);
        } catch(Exception e) {
            LOG.severe("### TechnologyService.addTechnology() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newTechnology).build();
    }
    
    @POST
    public Response updateTechnology(Technology technology) {
        Technology existing = null;
        try {
            existing = entityManager.find(Technology.class, technology.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(technology.getName());
            
        } catch(Exception e) {
            LOG.severe("### TechnologyService.updateTechnology() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deleteTechnology(@PathParam("ID")long ID) {
        Technology technology = entityManager.find(Technology.class, ID);
        if(technology == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(technology);
        } catch(Exception e) {
            LOG.severe("### TechnologyService.deleteTechnology() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(technology).build();
    }
}
