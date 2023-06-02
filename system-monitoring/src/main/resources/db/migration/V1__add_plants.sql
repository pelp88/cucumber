CREATE TABLE plants (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	chemical float8 NULL,
	manure float8 NULL,
	max_humidity float8 NULL,
	max_temp int4 NULL,
	min_humidity float8 NULL,
	min_temp int4 NULL,
	"name" varchar(255) NULL,
	plant_sort varchar(255) NULL,
	planting_date date NULL,
	ripening_date date NULL,
	CONSTRAINT plants_chemical_check CHECK (((chemical <= (100)::double precision) AND (chemical >= (0)::double precision))),
	CONSTRAINT plants_manure_check CHECK (((manure <= (100)::double precision) AND (manure >= (0)::double precision))),
	CONSTRAINT plants_pkey PRIMARY KEY (id)
);