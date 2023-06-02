-- public.plant_soil_type definition

-- Drop table

-- DROP TABLE plant_soil_type;

CREATE TABLE plant_soil_type (
	plant_id int8 NOT NULL,
	soil_type varchar(255) NULL
);


-- public.plant_soil_type foreign keys

ALTER TABLE public.plant_soil_type ADD CONSTRAINT fk80wtlixaepdits7382k80wv97 FOREIGN KEY (plant_id) REFERENCES plants(id);