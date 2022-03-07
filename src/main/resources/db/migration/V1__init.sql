create table if not exists channel(
    channel_id varchar(24),
    primary key(channel_id)
);

create table if not exists channels_info (
    channel_id varchar(24),
    title text,
    description text,
    published_at timestamp,
    country varchar(2),
    last_check timestamp,
    primary key (channel_id)
);

create table if not exists videos_info (
    video_id varchar(11),
    title text,
    description text,
    published_at timestamp,
    channel_id varchar(24),
    last_check timestamp,
    foreign key (channel_id)
    references channels_info(channel_id),
    primary key (video_id)
);
