package net.nortlam.saruman.service;

import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import net.nortlam.saruman.entity.Role;
import net.nortlam.saruman.entity.User;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/user")
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<User> result = (Collection<User>)entityManager.createNamedQuery(User.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<User>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        User foundUser = entityManager.find(User.class, ID);
        if(foundUser == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundUser).build();
    }

    
    @GET @Path("/username/{username}")
    public Response findByUsername(@PathParam("username")String username) {
        User foundUser = null;
        try {
            foundUser = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                    .setParameter("USERNAME", username).getSingleResult();
        } catch(NoResultException e) {
            LOG.severe("### UserService.findbyUsername() UNABLE TO SEEK:"+e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(foundUser).build();
    }
    
    @PUT
    public Response addUser(User newUser) {
        try {
            entityManager.persist(newUser);
        } catch(Exception e) {
            LOG.severe("### UserService.addUser() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newUser).build();
    }
    
    @POST
    public Response updateUser(User user) {
        User existing = null;
        try {
            existing = entityManager.find(User.class, user.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setUsername(user.getUsername());
            existing.setPassword(user.getPassword());
            existing.setFullName(user.getFullName());
            
        } catch(Exception e) {
            LOG.severe("### UserService.updateUser() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @POST @Path("{ID}/{name}")
    public Response setRole(@PathParam("ID") long ID,@PathParam("name") String name) {
        User user = null;
        try {
            Role role = entityManager.createNamedQuery(Role.FIND_BY_NAME, Role.class)
                .setParameter("NAME", name).getSingleResult();
            
            user = entityManager.find(User.class, ID);
            if(user == null) return Response.status(Response.Status.NOT_FOUND).build();
        
            // Found role. Set into user
            user.setRole(role);
            
        } catch(NoResultException e) {
            LOG.severe("### UserService.setRole() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(user).build();
    }
    
    
    @DELETE @Path("{ID}")
    public Response deleteUser(@PathParam("ID")long ID) {
        User user = entityManager.find(User.class, ID);
        if(user == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(user);
        } catch(Exception e) {
            LOG.severe("### UserService.deleteUser() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(user).build();
    }

}
