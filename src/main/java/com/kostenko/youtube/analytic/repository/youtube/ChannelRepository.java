package com.kostenko.youtube.analytic.repository.youtube;

import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelIdEntity, String> {

}
