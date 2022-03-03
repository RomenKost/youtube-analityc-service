package com.kostenko.youtube.analytic.service.repository;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsInfoRepository extends CrudRepository<ChannelEntity, String> {

}
