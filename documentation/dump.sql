--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: message; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.message (
    id bigint NOT NULL,
    contents character varying(255),
    receiver character varying(255),
    sender character varying(255),
    sentdate timestamp without time zone,
    title character varying(255)
);


--
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.message_id_seq OWNED BY public.message.id;


--
-- Name: message_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.message_user (
    message_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.project (
    project_id bigint NOT NULL,
    projectinformation character varying(255),
    projectname character varying(255),
    manager_id bigint
);


--
-- Name: project_project_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.project_project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: project_project_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.project_project_id_seq OWNED BY public.project.project_id;


--
-- Name: task; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.task (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255),
    priority integer,
    tag character varying(255),
    taskstatus integer NOT NULL,
    project_id bigint
);


--
-- Name: task_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.task_id_seq OWNED BY public.task.id;


--
-- Name: user_model; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_model (
    user_id bigint NOT NULL,
    activationcode character varying(255),
    email character varying(255),
    firstname character varying(255),
    isblocked boolean NOT NULL,
    islocked boolean NOT NULL,
    lastname character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255)
);


--
-- Name: user_model_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_model_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_model_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_model_user_id_seq OWNED BY public.user_model.user_id;


--
-- Name: user_project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_project (
    user_id bigint NOT NULL,
    project_id bigint NOT NULL
);


--
-- Name: message id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message ALTER COLUMN id SET DEFAULT nextval('public.message_id_seq'::regclass);


--
-- Name: project project_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project ALTER COLUMN project_id SET DEFAULT nextval('public.project_project_id_seq'::regclass);


--
-- Name: task id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.task ALTER COLUMN id SET DEFAULT nextval('public.task_id_seq'::regclass);


--
-- Name: user_model user_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_model ALTER COLUMN user_id SET DEFAULT nextval('public.user_model_user_id_seq'::regclass);


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.message (id, contents, receiver, sender, sentdate, title) FROM stdin;
1	Message which will sent by admin to user	user@mail.com	admin@mail.com	2018-04-12 10:57:38.31	Msg sent by admin to user
2	Message which will sent by user to admin	admin@mail.com	user@mail.com	2018-04-12 10:57:38.312	Msg sent by user to admin
\.


--
-- Data for Name: message_user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.message_user (message_id, user_id) FROM stdin;
1	3
1	1
2	3
2	1
\.


--
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.project (project_id, projectinformation, projectname, manager_id) FROM stdin;
1	project1Info	Lotnisko	2
2	project2Info	project2	2
3	project3Info	project3	2
4	project 0 details	Temp project 0	2
5	project 1 details	Temp project 1	2
6	project 2 details	Temp project 2	2
7	project 3 details	Temp project 3	2
8	project 4 details	Temp project 4	2
9	project 5 details	Temp project 5	2
10	project 6 details	Temp project 6	2
11	project 7 details	Temp project 7	2
12	project 8 details	Temp project 8	2
13	project 9 details	Temp project 9	2
\.


--
-- Data for Name: task; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.task (id, description, name, taskPriority, tag, taskstatus, project_id) FROM stdin;
1	Podstawowy szkielet funkcionalnej aplikacji	Stworzenie szkieletu	0	START	5	1
2	Dodanie do projektu połaczenia z baza danych	Baza danych	0	BD	5	1
3	Stworznie wszystkich wymaganych entity na potrzeby projektu	Entity	1	BD	4	1
4	Stworznie repozytowrów JPA dla entity oraz wykonanie testów zapisu do bazy dancyh	Połaczenie z bazą	1	JP	3	1
5	stworznie potrzebnej logiki aplikacji do zarazdzania i kontrolą lotów na lotnisku	Logika aplikacji	0	SERVICE	3	1
6	Dodanie potrzebnych kontrolerów	kontrolery	1	REST	2	1
7	Testownie integracyjne bazy andych z logiką oraz kontrolerami	testownie	2	[TEST]	1	1
8	stwornzie szkieletu fronu aplikacji	FrontEnd	0	[FE]	1	1
9	Zabezpieczenie aplikacji przed działaniami szkodzącymi	Security	2	[SEC]	0	1
10	Wdrązenie aplikacji na serwer	Wdrązenie	2	[END]	0	1
\.


--
-- Data for Name: user_model; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_model (user_id, activationcode, email, firstname, isblocked, islocked, lastname, password, role, username) FROM stdin;
1	\N	user@mail.com	Benek	f	f	Bebenek	$2a$12$3ghf7N4P4Jm.f4SLFHHT3ukEpGT7OQCruJtClexrjkhxpEYFs6LHS	USER	user
2	\N	manager@mail.com	Edward	f	f	Oncki	$2a$12$1ASFttsx9TRInXSeawOqiuT1psfh.EhLkjN3ymsGPguXL1uzbnT.e	USER	manager
3	\N	admin@mail.com	Adam	f	f	Admiński	$2a$12$Jv.6qw3LhBJ.GUYnCfajf.Up.cXXvdzbjgO3/MUWujNpSwSXZCPCy	ADMIN	admin
4	\N	user0@mail.com	Adam0	f	f	Spadam0	$2a$12$bWO.crhIgTP8hCgZy/0yauk3lO/XEFupiQERAUz8Pc.xIOwu23H6G	USER	user0
5	\N	user1@mail.com	Adam1	f	f	Spadam1	$2a$12$K1gAdfb.sGKxlezj9ZHPBeV6qV9q1./QFb8r4rNLKEUixGoPAu9s.	USER	user1
6	\N	user2@mail.com	Adam2	f	f	Spadam2	$2a$12$6XyLhAU1uSat7hXBuU13nOKEkxMjvYTmNL.QVI.wyvQWJ7g.6I4CC	USER	user2
7	\N	user3@mail.com	Adam3	f	f	Spadam3	$2a$12$RJJ7khuF4FFTWu1hxbtVGOVTkk3xIGkj/yqzfb2Y5hNy.gWGwHI0e	USER	user3
8	\N	user4@mail.com	Adam4	f	f	Spadam4	$2a$12$0mlEKNfb2BoL1nlmFadhqOdSPXvnWWTyB4AQtA9b8yw94Qr2CnfCW	USER	user4
9	\N	user5@mail.com	Adam5	f	f	Spadam5	$2a$12$4LbPBfRlC2n7Wt6loxTn5uodWdxhiT.ML5wN17oJoaVk2Q.YEE.Su	USER	user5
10	\N	user6@mail.com	Adam6	f	f	Spadam6	$2a$12$Fk9ifY.VYVdcbBvKYG9Pv.qYcdcm2lyjKLvHA2AS4Ma92qLCvzn/a	USER	user6
11	\N	user7@mail.com	Adam7	f	f	Spadam7	$2a$12$oOUIIHRtoPma8.rSzibuyO6MIbDh8S26OeAW1zZ1d0I59OCwidBdy	USER	user7
12	\N	user8@mail.com	Adam8	f	f	Spadam8	$2a$12$k5Q/WJzT2taGCi.02cg6f.OR1Savp5CAbOB9Kz9P11oCGzkiG.lKa	USER	user8
13	\N	user9@mail.com	Adam9	f	f	Spadam9	$2a$12$O3SJEcmaQ4mL23iy0mNzK.j2IrB.i8xLHDhj0Vhnq9LX38paZw.q2	USER	user9
\.


--
-- Data for Name: user_project; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_project (user_id, project_id) FROM stdin;
1	3
1	2
1	1
\.


--
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.message_id_seq', 2, true);


--
-- Name: project_project_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.project_project_id_seq', 13, true);


--
-- Name: task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.task_id_seq', 10, true);


--
-- Name: user_model_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.user_model_user_id_seq', 13, true);


--
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- Name: message_user message_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message_user
    ADD CONSTRAINT message_user_pkey PRIMARY KEY (message_id, user_id);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- Name: task task_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (id);


--
-- Name: user_model user_model_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_model
    ADD CONSTRAINT user_model_pkey PRIMARY KEY (user_id);


--
-- Name: user_project user_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT user_project_pkey PRIMARY KEY (user_id, project_id);


--
-- Name: project fk2n52kng6v8wtcrjhlj85476xj; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fk2n52kng6v8wtcrjhlj85476xj FOREIGN KEY (manager_id) REFERENCES public.user_model(user_id);


--
-- Name: message_user fkn95hh7gh3ynym2krfmr80qfvl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message_user
    ADD CONSTRAINT fkn95hh7gh3ynym2krfmr80qfvl FOREIGN KEY (user_id) REFERENCES public.user_model(user_id);


--
-- Name: user_project fknni2eyvhm0ik5f1mr59t7p9f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT fknni2eyvhm0ik5f1mr59t7p9f FOREIGN KEY (user_id) REFERENCES public.user_model(user_id);


--
-- Name: task fkpvt2aj1xuklt0b5p3q5et8w6w; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.task
    ADD CONSTRAINT fkpvt2aj1xuklt0b5p3q5et8w6w FOREIGN KEY (project_id) REFERENCES public.project(project_id);


--
-- Name: message_user fkrvh2hwa2xe5qf8ddhiwlvpc0s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message_user
    ADD CONSTRAINT fkrvh2hwa2xe5qf8ddhiwlvpc0s FOREIGN KEY (message_id) REFERENCES public.message(id);


--
-- Name: user_project fkslmdnh3o1bx85oqdjmoar5v0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT fkslmdnh3o1bx85oqdjmoar5v0 FOREIGN KEY (project_id) REFERENCES public.project(project_id);


--
-- PostgreSQL database dump complete
--

