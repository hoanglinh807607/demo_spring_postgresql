package com.postgresql.connect_postgresql.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Data
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    private String code;
    private String name;

    @ManyToMany( mappedBy = "roleEntities")
    Collection<AccountEntity> accountEntities;
}
