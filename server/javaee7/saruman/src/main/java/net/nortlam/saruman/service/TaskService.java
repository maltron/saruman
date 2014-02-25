package net.nortlam.saruman.service;

import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.nortlam.saruman.entity.Task;

/**
 *
 * @author Mauricio "Maltron" Leal */
@Stateless
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/task")
public class TaskService {

    private static final Logger LOG = Logger.getLogger(TaskService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

//    @GET @Path("{user}")
//    public Response findAll(@PathParam("user")long ID) {
//        Collection<Task> result = (Collection<Task>)entityManager.createNamedQuery(Task.FIND_ALL_BY_USER)
//                .setParameter("USER", ID).getResultList();
//        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
//        
//        return Response.ok(new GenericEntity<Collection<Task>>(result) {}).build();
//    }
    
    @GET @Path("travelling/{user}/{quarter}/{week}")
    public Response groupByTravelling(@PathParam("user")long user, 
                    @PathParam("quarter")int quarter, @PathParam("week")int week) {
        
        Collection<Task> result = groupTravelling(user, quarter, week);
        if(result.size() == 0) return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok(new GenericEntity<Collection<Task>>(result) {}).build();
    }
    
//    @PUT
//    public Response addTask(Task newTask) {
//        try {
//            entityManager.persist(newTask);
//        } catch(Exception e) {
//            LOG.severe("### TaskService.addTask() UNABLE TO INSERT:"+e.getMessage());
//            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
//        }
//        
//        return Response.ok(newTask).build();
//    }

    @PUT
    public Response save(Collection<Task> tasks) { 
        if(tasks == null) 
            return Response.status(Response.Status.NO_CONTENT).build();
        if(tasks.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        
        // Assuming all Tasks belongs to the same user, with Quarter and Week
        // Retrieve basic information
        Task first = tasks.iterator().next();
        
        // Before storing a collection of tasks, the user might be deleted
        // a particular task and the server side won't be aware of that
        
        // Because of that, the strategy in here it's to:
        // Step #1: Delete all tasks based on UserID, Quarter and Week
        entityManager.createNamedQuery(Task.DELETE_QUARTER_WEEK)
               .setParameter("USER", first.getUser().getID())
                     .setParameter("QUARTER", first.getQuarter())
                          .setParameter("WEEK", first.getWeek()).executeUpdate();

        // IMPORTANT: If one of the objects has their ID, 
        //            the Context will conclude it's a detached object and 
        //            it will not persist
        // Step #2: Insert each task again
        for(Task task: tasks) {
            try {
                entityManager.persist(task);
            } catch(Exception e) {
                LOG.severe("### TaskService.save() UNABLE TO INSERT:"+e.getMessage());
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        }
        
        // With this strategy, it user can publish the same information over
        // and over again and be assure the content is always up-to-date
        
        return Response.ok().build();
    }
    
    // BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS 
    //  BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS BUSINESS METHODS 
    
//    // Search for a particular Task. If it does exist, then insert it, else update it.
//    public boolean save(Task task) throws Exception {
//        if(task == null) {
//            LOG.severe("### TaskService.save() TASK PARAMETER IS NULL");
//            return false;
//        }
//        
//        boolean found = false;
//        if(isTravelling(task))
//            for(Task scan: findTravelling(task))
//                if(found = match(task, scan)) break;
//        
//        // FOUND IT ? Update it else Insert it
//        if(found) update(task); else insert(task);
//    }
    
    public Task insert(Task task) throws Exception {
        entityManager.persist(task);
        
        return task;
    }
    
    public Task update(Task task) throws Exception {
        Task existing = null;
        if(isTravelling(task)) existing = findByTravelling(task);
        else if(isCustomer(task)) existing = findByCustomer(task);
        else if(isPartner(task)) existing = findByPartner(task);
        else if(isResearch(task)) existing = findByResearch(task);
        else if(isPreSales(task)) existing = findByPreSales(task);
        else if(isPostSales(task)) existing = findByPostSales(task);
        else if(isBackOffice(task)) existing = findByBackOffice(task);
        else if(isPTO(task)) existing = findByPTO(task);
        
        existing.setQuarter(task.getQuarter());
        existing.setWeek(task.getWeek());
        existing.setAmount(task.getAmount());
        
        return existing;
    }
    
    public boolean match(Task t1, Task t2) {
        if(t1 == null || t2 == null) return false;
        
        return t1.getUser().getID() == t2.getUser().getID() &&
                t1.getQuarter() == t2.getQuarter() &&
                 t1.getWeek() == t2.getWeek();
    }
    
    public boolean isTravelling(Task task) {
        if(task == null) return false;
        
        return task.getCity() != null;
    }
    
    public boolean isCustomer(Task task) {
        if(task == null) return false;
        
        return task.getCustomer() != null;
    }
    
    public boolean isPartner(Task task) {
        if(task == null) return false;
        
        return task.getPartner() != null;
    }
    
    public boolean isResearch(Task task) {
        if(task == null) return false;
        
        return task.getTechnology() != null;
    }
    
    public boolean isPreSales(Task task) {
        if(task == null) return false;
        
        return task.getPreSales() != null;
    }
    
    public boolean isPostSales(Task task) {
        if(task == null) return false;
        
        return task.getPostSales() != null;
    }
    
    public boolean isBackOffice(Task task) {
        if(task == null) return false;
        
        return task.getBackOffice() != null;
    }
    
    public boolean isPTO(Task task) {
        if(task == null) return false;
        
        return task.getPTO() != null;
    }
    
    public Task findByTravelling(Task task) throws NoResultException {
        return findByTravelling(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getCity().getID());
    }
    
    public Task findByTravelling(long user, int quarter, int week, long city) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_TRAVELLING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("CITY", city)
                                                            .getSingleResult();
    }
    
    public Task findByCustomer(Task task) throws NoResultException {
        return findByTravelling(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getCustomer().getID());
    }
    
    public Task findByCustomer(long user, int quarter, int week, long customer) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_CUSTOMER_ENGAGING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("CUSTOMER", customer)
                                                            .getSingleResult();
    }
    
    public Task findByPartner(Task task) throws NoResultException {
        return findByPartner(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getPartner().getID());
    }
    
    public Task findByPartner(long user, int quarter, int week, long partner) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_PARTNER, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("PARTNER", partner)
                                                            .getSingleResult();
    }
    
    public Task findByResearch(Task task) throws NoResultException {
        return findByResearch(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getTechnology().getID());
    }
    
    public Task findByResearch(long user, int quarter, int week, long technology) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_RESEARCHING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("TECHNOLOGY", technology)
                                                            .getSingleResult();
    }
    
    public Task findByPreSales(Task task) throws NoResultException {
        return findByPreSales(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getPreSales().getID());
    }
    
    public Task findByPreSales(long user, int quarter, int week, long preSales) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_PRESALES, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("PRESALES", preSales)
                                                            .getSingleResult();
    }
    
    public Task findByPostSales(Task task) throws NoResultException {
        return findByPostSales(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getPostSales().getID());
    }
    
    public Task findByPostSales(long user, int quarter, int week, long postSales) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_POSTSALES, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("POSTSALES", postSales)
                                                            .getSingleResult();
    }
    
    public Task findByBackOffice(Task task) throws NoResultException {
        return findByBackOffice(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getBackOffice().getID());
    }
    
    public Task findByBackOffice(long user, int quarter, int week, long backOffice) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_BACKOFFICE, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("BACKOFFICE", backOffice)
                                                            .getSingleResult();
    }
    
    public Task findByPTO(Task task) throws NoResultException {
        return findByPTO(task.getUser().getID(), task.getQuarter(), 
                                        task.getWeek(), task.getPTO().getID());
    }
    
    public Task findByPTO(long user, int quarter, int week, long pto) throws NoResultException {
        return entityManager.createNamedQuery(Task.FIND_BY_PTO, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                        .setParameter("WEEK", week).setParameter("PTO", pto)
                                                            .getSingleResult();
    }
    
    public Collection<Task> groupTravelling(Task task) {
        return groupTravelling(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupTravelling(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_TRAVELLING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupCustomer(Task task) {
        return groupCustomer(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupCustomer(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_CUSTOMER_ENGAGING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupPartner(Task task) {
        return groupPartner(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupPartner(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_PARTNER, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupResearching(Task task) {
        return groupResearching(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupResearching(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_RESEARCHING, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupPreSales(Task task) {
        return groupPreSales(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupPreSales(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_PRESALES, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupPostSales(Task task) {
        return groupPostSales(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupPostSales(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_POSTSALES, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupBackOffice(Task task) {
        return groupBackOffice(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupBackOffice(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_BACKOFFICE, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
    
    public Collection<Task> groupPTO(Task task) {
        return groupPTO(task.getUser().getID(), task.getQuarter(), task.getWeek());
    }
    
    public Collection<Task> groupPTO(long user, int quarter, int week) {
        return entityManager.createNamedQuery(Task.GROUP_BY_PTO, Task.class)
                .setParameter("USER", user).setParameter("QUARTER", quarter)
                    .setParameter("WEEK", week).getResultList();
    }
}
