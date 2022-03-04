package com.kostenko.youtube.analytic.repository;

import com.kostenko.youtube.analytic.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsInfoRepository extends CrudRepository<ChannelEntity, String> {

}
