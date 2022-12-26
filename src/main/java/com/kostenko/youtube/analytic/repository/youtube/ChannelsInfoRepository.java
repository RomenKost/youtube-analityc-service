package com.kostenko.youtube.analytic.repository.youtube;

import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsInfoRepository extends JpaRepository<ChannelEntity, String> {

}
