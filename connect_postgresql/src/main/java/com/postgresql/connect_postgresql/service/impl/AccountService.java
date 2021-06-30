package com.postgresql.connect_postgresql.service.impl;

import com.postgresql.connect_postgresql.converter.AccountConverter;
import com.postgresql.connect_postgresql.dto.AccountDto;
import com.postgresql.connect_postgresql.entities.AccountEntity;
import com.postgresql.connect_postgresql.entities.RoleEntity;
import com.postgresql.connect_postgresql.repositories.AccountRepos;
import com.postgresql.connect_postgresql.repositories.RoleRepos;
import com.postgresql.connect_postgresql.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountService implements IAccountService<AccountDto> {

    @Autowired
    AccountRepos accountRepos;

    @Autowired
    AccountConverter accountConverter;

    @Autowired
    RoleRepos roleRepos;

    @Override
    public AccountDto findOne(Long id) {
        return accountConverter.toDto(accountRepos.findById(id).get());
    }

    @Override
    public List<AccountDto> findAll() {
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountRepos.findAll().forEach(account->{
            accountDtoList.add(accountConverter.toDto(account));
        });
        return accountDtoList;
    }

    @Override
    @Transactional
    public AccountDto createOrUpdate(AccountDto dto) {
        AccountDto accountDto = null;
        AccountEntity accountEntity = null;
        if( dto.getId() == null ){
            accountEntity = accountConverter.toEntity(dto);
        }else{
            AccountEntity accountEntityOld = accountRepos.findById(dto.getId()).get();
            accountEntity = accountConverter.toEntity(accountEntityOld, dto);
        }
        List<RoleEntity> roles = new ArrayList<>();
        dto.getRoleCode().forEach(roleCode->{ roles.add(roleRepos.findByCode(roleCode)); });
        accountEntity.setRoleEntities(roles);
        return accountConverter.toDto(accountRepos.save(accountEntity));
    }

    @Override
    public Boolean delete(Long[] ids) {
        try{
            Arrays.stream(ids).forEach(id->{
                accountRepos.delete(accountRepos.findById(id).get());
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<AccountDto> findAll(Integer limit) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        List<AccountEntity> accountEntities = accountRepos.findAll(PageRequest.of(0,limit)).getContent();
        System.out.println(accountEntities.size());
        accountEntities.forEach(accountEntity->{
            accountDtoList.add(accountConverter.toDto(accountEntity));
        });
        return accountDtoList;
    }
}
