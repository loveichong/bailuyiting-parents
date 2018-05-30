package com.bailuyiting.commons.core.jpa.communityparking;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.repository.jpa.BaseStringJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CommunityParkingParkInfoRepository extends BaseStringJpaRepository<CommunityParkingParkInfo> {
    /**
     *
     * @param id
     * @param status
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE community_parking_park_info u SET u.park_status=?2 WHERE u.id=?1", nativeQuery = true)
    void updateStatusById(String id,int status);
    /**
     *
     * @param Id
     * @return
     */
    CommunityParkingParkInfo findByParkOwnerID(String Id);
    List<CommunityParkingParkInfo> findByCommunityIdAndParkStatus(String Id, Integer status,Pageable pageable);
}
