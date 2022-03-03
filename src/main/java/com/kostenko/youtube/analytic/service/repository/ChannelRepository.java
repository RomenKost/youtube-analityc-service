package com.kostenko.youtube.analytic.service.repository;

import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<ChannelIdEntity, String> {

}
