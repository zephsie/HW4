package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.IPizza;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Pizza implements IPizza {
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public Pizza() {
    }

    public Pizza(Long id, String name, String description, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        if (!Objects.equals(id, pizza.id)) return false;
        if (!Objects.equals(name, pizza.name)) return false;
        if (!Objects.equals(description, pizza.description)) return false;
        if (!Objects.equals(createDate, pizza.createDate)) return false;
        return Objects.equals(updateDate, pizza.updateDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
