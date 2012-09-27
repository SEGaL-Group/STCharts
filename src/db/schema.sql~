--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

------------------------------------------------ TABLES --------------------------------------------

------------- Projects

CREATE SEQUENCE projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE projects (
    pid integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('projects_id_seq'::regclass),
    name character varying(255),
    path character varying(255)
);

ALTER SEQUENCE projects_id_seq OWNED BY projects.pid;

------------- Commit Ranges

CREATE SEQUENCE commitranges_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE commit_ranges (
    pid integer REFERENCES projects(pid),
    cid integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('commitranges_id_seq'::regclass),
    start date,
    end date
);

ALTER SEQUENCE commitranges_id_seq OWNED BY commitranges.cid;

------------- File Pairs

CREATE TABLE file_pairs (
    
    
);
