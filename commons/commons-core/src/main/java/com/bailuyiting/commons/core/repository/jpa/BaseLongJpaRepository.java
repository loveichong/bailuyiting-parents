package com.xinchidao.common.core.repository.jpa;




import com.bailuyiting.commons.core.domain.AbstractLongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseLongJpaRepository<T extends AbstractLongEntity> extends JpaRepository<T, Long>,JpaSpecificationExecutor<T> {

}
