-- public.operations definition

-- Drop table

-- DROP TABLE operations;

CREATE TABLE operations (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"interval" int8 NULL,
	operation_name varchar(255) NULL,
	work_time date NULL,
	id_plant int8 NULL,
	CONSTRAINT operations_pkey PRIMARY KEY (id)
);


-- public.operations foreign keys

ALTER TABLE public.operations ADD CONSTRAINT fk3ee2f833cv5sbnsrwtuaeq2br FOREIGN KEY (id_plant) REFERENCES plants(id);