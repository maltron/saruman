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

import net.nortlam.saruman.entity.Customer;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/customer")
public class CustomerService {

    private static final Logger LOG = Logger.getLogger(CustomerService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public Response findAll() {
        Collection<Customer> result = (Collection<Customer>)entityManager.createNamedQuery(Customer.FIND_ALL).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Customer>>(result) {}).build();
    }
    
    @GET @Path("{ID}")
    public Response findByID(@PathParam("ID")long ID) {
        Customer foundCustomer = entityManager.find(Customer.class, ID);
        if(foundCustomer == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(foundCustomer).build();
    }
    
    @PUT
    public Response addCustomer(Customer newCustomer) {
        try {
            entityManager.persist(newCustomer);
        } catch(Exception e) {
            LOG.severe("### CustomerService.addCustomer() UNABLE TO INSERT:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(newCustomer).build();
    }
    
    @POST
    public Response updateCustomer(Customer customer) {
        Customer existing = null;
        try {
            existing = entityManager.find(Customer.class, customer.getID());
            if(existing == null) {
                // It just doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Found it
            existing.setName(customer.getName());
            
        } catch(Exception e) {
            LOG.severe("### CustomerService.updateCustomer() UNABLE TO UPDATE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(existing).build();
    }
    
    @DELETE @Path("{ID}")
    public Response deleteCustomer(@PathParam("ID")long ID) {
        Customer customer = entityManager.find(Customer.class, ID);
        if(customer == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        try {
            entityManager.remove(customer);
        } catch(Exception e) {
            LOG.severe("### CustomerService.deleteCustomer() UNABLE TO DELETE:"+e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(customer).build();
    }
}
