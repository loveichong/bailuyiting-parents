package com.bailuyiting.commons.core.repository.jpa;


import com.bailuyiting.commons.core.domain.AbstractStringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseStringJpaRepository<T extends AbstractStringEntity> extends JpaRepository<T, String>,JpaSpecificationExecutor<T> {

}
