package com.postgresql.connect_postgresql.converter;

import com.postgresql.connect_postgresql.dto.AccountDto;
import com.postgresql.connect_postgresql.entities.AccountEntity;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountConverter implements AbtractConverter<AccountDto, AccountEntity> {

    public AccountDto toDto(AccountEntity entity){
        AccountDto accountDto = AccountDto.builder().username(entity.getUsername())
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .roleCode(new ArrayList<>())
                .roleName(new ArrayList<>())
                .build();
        accountDto.setId(entity.getId());
        entity.getRoleEntities();
        entity.getRoleEntities().stream().forEach(roleEntity -> {
            accountDto.getRoleCode().add(roleEntity.getCode());
            accountDto.getRoleName().add(roleEntity.getName());
        });
        return accountDto;
    }

    @Override
    public AccountEntity toEntity(AccountDto dto) {
        return getEntity(new AccountEntity(), dto);
    }

    @Override
    public AccountEntity toEntity(AccountEntity entity, AccountDto dto) {
        return getEntity(entity, dto);
    }

    @NotNull
    private AccountEntity getEntity(AccountEntity entity, AccountDto dto) {
        if( dto.getId() != null ) entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
