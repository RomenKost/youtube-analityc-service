ALTER TABLE channels_info ALTER COLUMN published_at TYPE date;
ALTER TABLE channels_info ALTER COLUMN last_check TYPE date;
ALTER TABLE videos_info ALTER COLUMN published_at TYPE date;
ALTER TABLE videos_info ALTER COLUMN last_check TYPE date;
ALTER TABLE users ALTER COLUMN updated TYPE date;
ALTER TABLE users ALTER COLUMN created TYPE date;