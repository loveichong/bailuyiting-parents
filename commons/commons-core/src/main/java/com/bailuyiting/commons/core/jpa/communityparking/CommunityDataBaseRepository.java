package com.bailuyiting.commons.core.jpa.communityparking;

import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommunityDataBaseRepository extends BaseStringJpaRepository<CommunityDataBase> {
    CommunityDataBase findByCommunityName(String communityName);
    @Query(value = "select * from community_data_base where community_name like CONCAT('%',?1,'%')", nativeQuery = true)
    List<CommunityDataBase> findByCommunityNameLike(String communityName);
}
