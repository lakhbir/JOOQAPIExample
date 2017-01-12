DROP TABLE Address_Type

CREATE TABLE Address_Type
(
   address_type varchar(16) NOT NULL, --CHARACTER SET utf8 COLLATE utf8_bin 
   value varchar (32) NOT NULL,
   description varchar(255) NOT NULL,
   creation_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
   UNIQUE(address_type)
)