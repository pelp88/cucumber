CREATE TABLE fields (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	latitude float8 NULL,
	longitude float8 NULL,
	sensor_serial_number uuid NULL,
	soil_type varchar(255) NULL,
	id_plant int8 NULL,
	CONSTRAINT fields_pkey PRIMARY KEY (id)
);


-- public.fields foreign keys

ALTER TABLE public.fields ADD CONSTRAINT fko9ygvkb2afyf19xwwwae9yblp FOREIGN KEY (id_plant) REFERENCES plants(id);