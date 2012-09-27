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
    name character varying(255)
);

ALTER SEQUENCE projects_id_seq OWNED BY projects.pid;

------------- Commit Ranges

CREATE SEQUENCE intervals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE intervals (
    pid integer REFERENCES projects(pid),
    iid integer NOT NULL UNIQUE DEFAULT NEXTVAL('intervals_id_seq'::regclass),
    interval integer,
    interval_type character varying(255),
    PRIMARY KEY(interval, interval_type, pid)
);

ALTER SEQUENCE intervals_id_seq OWNED BY intervals.iid;

------------- Users

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE users (
    uid integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('user_id_seq'::regclass),
    name character varying(255),
    email character varying(255)
);

ALTER SEQUENCE user_id_seq OWNED BY users.uid;

------------- Technical Networks

CREATE SEQUENCE tnetwork_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE t_networks (
    iid integer REFERENCES intervals(iid) ON DELETE CASCADE,
    nid integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('tnetwork_id_seq'::regclass)
);

ALTER SEQUENCE tnetwork_id_seq OWNED BY t_networks.nid;

------------- Technical Edges

CREATE TABLE t_edges (
    user_a integer REFERENCES users(uid) ON DELETE CASCADE,
    user_b integer REFERENCES users(uid) ON DELETE CASCADE,
    weight real,
    nid integer REFERENCES t_networks(nid) ON DELETE CASCADE
);

------------- Social Networks

CREATE SEQUENCE snetwork_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE s_networks (
    iid integer REFERENCES intervals(iid) ON DELETE CASCADE,
    nid integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('snetwork_id_seq'::regclass)
);

ALTER SEQUENCE snetwork_id_seq OWNED BY s_networks.nid;

------------- Social Edges

CREATE TABLE s_edges (
    user_a integer REFERENCES users(uid) ON DELETE CASCADE,
    user_b integer REFERENCES users(uid) ON DELETE CASCADE,
    weight real,
    nid integer REFERENCES s_networks(nid) ON DELETE CASCADE
);


