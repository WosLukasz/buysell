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
