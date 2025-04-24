CREATE DATABASE buysell_admin;
CREATE USER buysell_admin WITH ENCRYPTED PASSWORD 'buysell_admin';
GRANT ALL PRIVILEGES ON DATABASE buysell_admin TO buysell_admin;

\c buysell_admin postgres

GRANT ALL ON SCHEMA public TO buysell_admin;

-- DICTIONARIES

CREATE TABLE public.dictionaries (
    id bigint NOT NULL,
    version bigint NOT NULL,
    code character varying(255),
    valuetype character varying(255)
);


ALTER TABLE public.dictionaries OWNER TO buysell_admin;
CREATE SEQUENCE public.dictionaries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dictionaries_id_seq OWNER TO buysell_admin;
ALTER SEQUENCE public.dictionaries_id_seq OWNED BY public.dictionaries.id;


CREATE TABLE public.dictionarieselements (
    id bigint NOT NULL,
    version bigint NOT NULL,
    code character varying(255),
    value json,
    dictionary_id bigint
);


ALTER TABLE public.dictionarieselements OWNER TO buysell_admin;
CREATE SEQUENCE public.dictionarieselements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dictionarieselements_id_seq OWNER TO buysell_admin;
ALTER SEQUENCE public.dictionarieselements_id_seq OWNED BY public.dictionarieselements.id;


-- PATCHES


CREATE TABLE public.patches (
    id character varying(255) NOT NULL,
    installationdate timestamp(6) without time zone,
    status character varying(255),
    CONSTRAINT patches_status_check CHECK (((status)::text = ANY ((ARRAY['IN_PROGRESS'::character varying, 'SUCCESS'::character varying, 'FAILED'::character varying])::text[])))
);


ALTER TABLE public.patches OWNER TO buysell_admin;


-- ROLES


CREATE TABLE public.roles (
    id bigint NOT NULL,
    version bigint NOT NULL,
    code character varying(255),
    rights character varying(255)[]
);


ALTER TABLE public.roles OWNER TO buysell_admin;
CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO buysell_admin;
ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


-- SYNCHRONIZATIONS


CREATE TABLE public.synchronizations (
    id bigint NOT NULL,
    version bigint NOT NULL,
    code character varying(255),
    enddate timestamp(6) without time zone,
    startdate timestamp(6) without time zone,
    status character varying(255),
    CONSTRAINT synchronizations_status_check CHECK (((status)::text = ANY ((ARRAY['QUEUED'::character varying, 'IN_PROGRESS'::character varying, 'SUCCESS'::character varying, 'FAILED'::character varying])::text[])))
);


ALTER TABLE public.synchronizations OWNER TO buysell_admin;
CREATE SEQUENCE public.synchronizations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.synchronizations_id_seq OWNER TO buysell_admin;
ALTER SEQUENCE public.synchronizations_id_seq OWNED BY public.synchronizations.id;


-- USERS


CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    email character varying(255),
    firstname character varying(255),
    name character varying(255)
);


ALTER TABLE public.users OWNER TO buysell_admin;




ALTER TABLE ONLY public.dictionaries ALTER COLUMN id SET DEFAULT nextval('public.dictionaries_id_seq'::regclass);


--
-- TOC entry 3234 (class 2604 OID 16405)
-- Name: dictionarieselements id; Type: DEFAULT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.dictionarieselements ALTER COLUMN id SET DEFAULT nextval('public.dictionarieselements_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 16422)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3236 (class 2604 OID 16431)
-- Name: synchronizations id; Type: DEFAULT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.synchronizations ALTER COLUMN id SET DEFAULT nextval('public.synchronizations_id_seq'::regclass);


--
-- TOC entry 3240 (class 2606 OID 16400)
-- Name: dictionaries dictionaries_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.dictionaries
    ADD CONSTRAINT dictionaries_pkey PRIMARY KEY (id);


--
-- TOC entry 3242 (class 2606 OID 16409)
-- Name: dictionarieselements dictionarieselements_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.dictionarieselements
    ADD CONSTRAINT dictionarieselements_pkey PRIMARY KEY (id);


--
-- TOC entry 3244 (class 2606 OID 16417)
-- Name: patches patches_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.patches
    ADD CONSTRAINT patches_pkey PRIMARY KEY (id);


--
-- TOC entry 3246 (class 2606 OID 16426)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3248 (class 2606 OID 16436)
-- Name: synchronizations synchronizations_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.synchronizations
    ADD CONSTRAINT synchronizations_pkey PRIMARY KEY (id);


--
-- TOC entry 3250 (class 2606 OID 16443)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 16444)
-- Name: dictionarieselements fk4gx7ocs5t532838yl5db9v7vo; Type: FK CONSTRAINT; Schema: public; Owner: buysell_admin
--

ALTER TABLE ONLY public.dictionarieselements
    ADD CONSTRAINT fk4gx7ocs5t532838yl5db9v7vo FOREIGN KEY (dictionary_id) REFERENCES public.dictionaries(id);








Nr
Data
Numery
7186.22-04-202511928313338Wygrane7185.19-04-2025347363738Wygrane7184.17-04-202521015283349Wygrane7183.15-04-202551519212526Wygrane7182.12-04-202561119273149Wygrane7181.10-04-202512223244244Wygrane7180.08-04-2025162327353844Wygrane7179.05-04-202572125293739Wygrane7178.03-04-2025458102344Wygrane7177.01-04-202561520212740Wygrane7176.29-03-2025152529373941Wygrane7175.27-03-2025161720313545Wygrane7174.25-03-20253515202636Wygrane7173.22-03-2025171935394648Wygrane7172.20-03-2025182223323639Wygrane7171.18-03-2025242530394446Wygrane7170.15-03-202561118202834Wygrane7169.13-03-202511420404143Wygrane7168.11-03-20252910162749Wygrane7167.08-03-2025142021232649Wygrane7166.06-03-202531011131833Wygrane7165.04-03-202591824303148Wygrane7164.01-03-20253710294046Wygrane7163.27-02-2025167293738Wygrane7162.25-02-20251310151744Wygrane7161.22-02-2025256183639Wygrane7160.20-02-20254719203846Wygrane7159.18-02-202535681542Wygrane7158.15-02-2025202226324445Wygrane7157.13-02-2025213035363738Wygrane7156.11-02-202531718202930Wygrane7155.08-02-202571023244849Wygrane7154.06-02-202581011173646Wygrane7153.04-02-2025171922253138Wygrane7152.01-02-2025236172030Wygrane7151.30-01-202541621304349Wygrane7150.28-01-20257823293143Wygrane7149.25-01-2025283032364048Wygrane7148.23-01-202591228404245Wygrane7147.21-01-202551232343639Wygrane7146.18-01-2025203032344348Wygrane7145.16-01-2025262830404449Wygrane7144.14-01-202561026344043Wygrane7143.11-01-202571117183036Wygrane7142.09-01-202511119264244Wygrane7141.07-01-2025101118254146Wygrane7140.04-01-2025111924313445Wygrane7139.02-01-202521217244243Wygrane

