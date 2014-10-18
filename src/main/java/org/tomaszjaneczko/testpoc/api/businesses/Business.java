package org.tomaszjaneczko.testpoc.api.businesses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Business {
    private Long id;

    @NotEmpty
    private String name;

    public Business() {
        // Jackson
    }

    public Business(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public Business copyWithNewId(long newId) {
        return new Business(newId, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        if (id != null ? !id.equals(business.id) : business.id != null) return false;
        if (!name.equals(business.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }
}
