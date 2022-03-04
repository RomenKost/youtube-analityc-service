package com.kostenko.youtube.analytic.repository;

import com.kostenko.youtube.analytic.entity.ChannelIdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<ChannelIdEntity, String> {

}
