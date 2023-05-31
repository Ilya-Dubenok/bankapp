package org.example.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

public class CurrencyTypeDTO {
    @JsonProperty("Cur_ID")
    private long id;
    @JsonProperty("Cur_Abbreviation")
    private String name;

    public CurrencyTypeDTO() {
    }

    public CurrencyTypeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    @JsonSetter("Cur_ID")
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonSetter("Cur_Abbreviation")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyTypeDTO that = (CurrencyTypeDTO) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
