ALTER TABLE if exists plants DROP COLUMN health;

ALTER TABLE if exists fields ADD if NOT EXISTS plant_health INTEGER;