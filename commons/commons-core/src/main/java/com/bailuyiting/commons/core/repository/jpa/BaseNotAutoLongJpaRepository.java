package com.bailuyiting.commons.core.repository.jpa;



import com.bailuyiting.commons.core.domain.AbstractNotAutoLongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseNotAutoLongJpaRepository<T extends AbstractNotAutoLongEntity> extends JpaRepository<T, Long> {

}
