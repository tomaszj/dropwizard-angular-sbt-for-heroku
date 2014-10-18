package org.tomaszjaneczko.testpoc.api.businesses;

import com.codahale.metrics.annotation.Timed;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/businesses")
public class BusinessesResource {

    final BusinessRepository repository;

    public BusinessesResource() {
        repository = new BusinessRepository();
    }

    public BusinessesResource(BusinessRepository repo) {
        repository = repo;
    }

    @GET
    @Timed
    public List<Business> getBusinesses() {
        return repository.allBusinesses();
    }

    @GET
    @Path("{business_id}")
    @Timed
    public Business getBusiness(@PathParam("business_id") long businessId) {

        Optional<Business> business = repository.findBusiness(businessId);

        if (business.isPresent()) {
            return business.get();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @POST
    @Timed
    public Business createBusiness(@Valid Business business) {
        final Optional<Business> createdBusiness = repository.createBusiness(business);

        if (createdBusiness.isPresent()) {
            return createdBusiness.get();
        } else {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{business_id}")
    @Timed
    public void deleteBusiness(@PathParam("business_id") long id) {
        final boolean deleteSuccessful = repository.deleteBusiness(id);

        if (!deleteSuccessful) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}