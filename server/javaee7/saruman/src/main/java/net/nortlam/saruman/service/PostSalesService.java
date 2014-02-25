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

import net.nortlam.saruman.entity.PostSales;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/postsales")
public class PostSalesService {

    private static final Logger LOG = Logger.getLogger(PostSalesService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<PostSales> result = (Collection<PostSales>)entityManager.createNamedQuery(PostSales.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<PostSales>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        PostSales foundPostSales = entityManager.find(PostSales.class, ID);
        if(foundPostSales == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundPostSales).build();
    }
    
    @PUT
    public Response addPostSales(PostSales newPostSales) {
        try {
            entityManager.persist(newPostSales);
        } catch(Exception e) {
            LOG.severe("### PostSalesService.addPostSales() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newPostSales).build();
    }
    
    @POST
    public Response updatePostSales(PostSales postsales) {
        PostSales existing = null;
        try {
            existing = entityManager.find(PostSales.class, postsales.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(postsales.getName());
            
        } catch(Exception e) {
            LOG.severe("### PostSalesService.updatePostSales() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deletePostSales(@PathParam("ID")long ID) {
        PostSales postsales = entityManager.find(PostSales.class, ID);
        if(postsales == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(postsales);
        } catch(Exception e) {
            LOG.severe("### PostSalesService.deletePostSales() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(postsales).build();
    }


}
