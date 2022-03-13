package com.kostenko.youtube.analytic.repository.youtube;

import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsInfoRepository extends CrudRepository<ChannelEntity, String> {

}
