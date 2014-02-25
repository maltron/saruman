package net.nortlam.saruman.service;

import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.nortlam.saruman.entity.City;
import net.nortlam.saruman.entity.Country;
import net.nortlam.saruman.entity.Region;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Path("/location")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class LocationService {

    private static final Logger LOG = Logger.getLogger(LocationService.class.getName());
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @GET @Path("city")
    public Response allCities() {
        Collection<City> result = entityManager.createNamedQuery(City.FIND_ALL, City.class).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<City>>(result) {}).build();
    }
    
    @GET @Path("city/{ID}")
    public Response selectCity(@PathParam("ID")long ID) {
        City city = entityManager.find(City.class, ID);
        if(city == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(city).build();
    }

    @GET @Path("region")
    public Response allRegions() {
        Collection<Region> result = entityManager.createNamedQuery(Region.FIND_ALL, Region.class).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Region>>(result) {}).build();
    }
    
    @GET @Path("region/{ID}")
    public Response selectRegion(@PathParam("ID")long ID) {
        Region region = entityManager.find(Region.class, ID);
        if(region == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(region).build();
    }

    @GET @Path("country")
    public Response allCountries() {
        Collection<Country> result = entityManager.createNamedQuery(Country.FIND_ALL, Country.class).getResultList();
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Country>>(result) {}).build();
    }
    
    @GET @Path("country/{ID}")
    public Response selectCountry(@PathParam("ID")long ID) {
        Country country = entityManager.find(Country.class, ID);
        if(country == null) return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(country).build();
    }

}
