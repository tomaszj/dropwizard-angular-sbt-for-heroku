package org.tomaszjaneczko.testpoc.api.businesses;

import com.google.common.collect.Lists;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BusinessRepository {
    private static final List<Business> businesses;

    static {
        final Business business1 = new Business(1L, "Business 1");
        final Business business2 = new Business(2L, "Business 2");
        businesses = Lists.newArrayList(business1, business2);
    }

    public List<Business> allBusinesses() {
        return businesses;
    }

    public Optional<Business> findBusiness(long id) {
        return businesses.stream().filter(
            b -> b != null && b.getId() == id
        ).findFirst();
    }

    public Optional<Business> createBusiness(@Valid Business business) {
        Business createdBusiness;

        synchronized (businesses) {
            long lastId = businesses.stream()
                    .max(Comparator.comparing(Business::getId)).get().getId();

            long newId = lastId + 1;

            createdBusiness = business.copyWithNewId(newId);
            businesses.add(createdBusiness);
        }

        return Optional.of(createdBusiness);
    }

    public boolean deleteBusiness(long id) {
        synchronized (businesses) {
            final Optional<Business> business = findBusiness(id);

            if (business.isPresent()) {
                businesses.remove(business.get());
                return true;
            } else {
                return false;
            }
        }
    }
}
