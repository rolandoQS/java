
CREATE DATABASE demo;

USE demo;

CREATE TABLE user (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    created time without time zone,
    modified time without time zone,
    last_login time without time zone,
    token character varying,
    is_active boolean default true

);

CREATE TABLE phone (
    id bigint NOT NULL,
    number bigint NOT NULL,
    city_code integer NOT NULL,
    contry_code integer NOT NULL
);
