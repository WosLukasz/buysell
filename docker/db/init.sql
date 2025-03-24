CREATE DATABASE buysell;
CREATE USER buysell WITH ENCRYPTED PASSWORD 'buysell';
GRANT ALL PRIVILEGES ON DATABASE buysell TO buysell;

\c buysell postgres

GRANT ALL ON SCHEMA public TO buysell;

-- ATTACHMENTS

CREATE TABLE public.attachments (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    contenttype character varying(255),
    etag character varying(255),
    main boolean NOT NULL,
    attachmentorder integer,
    originalfilename character varying(255),
    path character varying(255),
    auction_id bigint
);

ALTER TABLE public.attachments OWNER TO buysell;

CREATE SEQUENCE public.attachments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.attachments_id_seq OWNER TO buysell;
ALTER SEQUENCE public.attachments_id_seq OWNED BY public.attachments.id;


-- AUCTIONS

CREATE TABLE public.auctions (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    category character varying(255),
    description character varying(255),
    enddate timestamp(6) without time zone,
    expirydate timestamp(6) without time zone,
    finishreason character varying(255),
    lastrefreshmentdate timestamp(6) without time zone,
    ownerid character varying(255),
    price_currency character varying(255),
    price_value double precision,
    sellerid bigint,
    signature character varying(255),
    startdate timestamp(6) without time zone,
    status character varying(255),
    statuschangedate timestamp(6) without time zone,
    title character varying(255),
    tocheckmanually boolean,
    CONSTRAINT auctions_finishreason_check CHECK (((finishreason)::text = ANY ((ARRAY['SOLD'::character varying, 'NOT_INTERESTED'::character varying, 'EXPIRED'::character varying, 'OTHER'::character varying])::text[]))),
    CONSTRAINT auctions_status_check CHECK (((status)::text = ANY ((ARRAY['QUEUED'::character varying, 'ACTIVE'::character varying, 'CLOSED'::character varying])::text[])))
);


ALTER TABLE public.auctions OWNER TO buysell;

CREATE SEQUENCE public.auctions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auctions_id_seq OWNER TO buysell;
ALTER SEQUENCE public.auctions_id_seq OWNED BY public.auctions.id;

CREATE SEQUENCE public.auctions_seq
    START WITH 1000000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.auctions_seq OWNER TO buysell;

CREATE TABLE public.auctions_sequencer (
    signature bigint NOT NULL
);

ALTER TABLE public.auctions_sequencer OWNER TO buysell;
ALTER SEQUENCE public.auctions_seq OWNED BY public.auctions_sequencer.signature;


-- ATTACHMENTS HISTORY

CREATE TABLE public.auctions_history (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    auction bytea,
    movedate timestamp(6) without time zone
);


ALTER TABLE public.auctions_history OWNER TO buysell;
CREATE SEQUENCE public.auctions_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.auctions_history_id_seq OWNER TO buysell;
ALTER SEQUENCE public.auctions_history_id_seq OWNED BY public.auctions_history.id;


-- AUCTIONS SEARCH REQUESTS

CREATE TABLE public.auctionsearchrequests (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    pageoffset integer,
    pagesize integer,
    sortby character varying(255),
    sortorder smallint,
    category character varying(255),
    pricefrom double precision,
    priceto double precision,
    text character varying(255),
    usersfavourite_id bigint,
    CONSTRAINT auctionsearchrequests_sortorder_check CHECK (((sortorder >= 0) AND (sortorder <= 1)))
);


ALTER TABLE public.auctionsearchrequests OWNER TO buysell;
CREATE SEQUENCE public.auctionsearchrequests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.auctionsearchrequests_id_seq OWNER TO buysell;
ALTER SEQUENCE public.auctionsearchrequests_id_seq OWNED BY public.auctionsearchrequests.id;

-- CATEGORIES

CREATE TABLE public.categories (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    code character varying(255),
    parentid bigint
);


ALTER TABLE public.categories OWNER TO buysell;
CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO buysell;
ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


-- CONTACTS

CREATE TABLE public.contacts (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    type character varying(255),
    value character varying(255),
    CONSTRAINT contacts_type_check CHECK (((type)::text = ANY ((ARRAY['EMAIL'::character varying, 'PHONE'::character varying])::text[])))
);


ALTER TABLE public.contacts OWNER TO buysell;
CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contacts_id_seq OWNER TO buysell;
ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


-- REPORTS

CREATE TABLE public.reports (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    message character varying(255),
    reason character varying(255),
    userid character varying(255),
    auction_id bigint,
    CONSTRAINT reports_reason_check CHECK (((reason)::text = ANY ((ARRAY['INAPPROPRIATE_CONTENT'::character varying, 'SPAM'::character varying, 'FRAUD'::character varying, 'ATTEMPT_TO_EXTREME_MONEY'::character varying, 'OTHER'::character varying])::text[])))
);


ALTER TABLE public.reports OWNER TO buysell;
CREATE SEQUENCE public.reports_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reports_id_seq OWNER TO buysell;
ALTER SEQUENCE public.reports_id_seq OWNED BY public.reports.id;


-- USER FAVOURITES

CREATE TABLE public.usersfavourites (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    auctions character varying(255)[],
    userid character varying(255)
);


ALTER TABLE public.usersfavourites OWNER TO buysell;
CREATE SEQUENCE public.usersfavourites_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usersfavourites_id_seq OWNER TO buysell;
ALTER SEQUENCE public.usersfavourites_id_seq OWNED BY public.usersfavourites.id;


-- VENDORS

CREATE TABLE public.vendors (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp(6) without time zone NOT NULL,
    modificationdate timestamp(6) without time zone,
    modifiedby character varying(255),
    version bigint NOT NULL,
    firstname character varying(255),
    location character varying(255),
    name character varying(255),
    ownerid character varying(255)
);

ALTER TABLE public.vendors OWNER TO buysell;

CREATE TABLE public.vendors_contacts (
    sellerprofile_id bigint NOT NULL,
    contactinformation_id bigint NOT NULL
);


ALTER TABLE public.vendors_contacts OWNER TO buysell;
CREATE SEQUENCE public.vendors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vendors_id_seq OWNER TO buysell;
ALTER SEQUENCE public.vendors_id_seq OWNED BY public.vendors.id;


-- VIEWS

CREATE TABLE public.views (
    id bigint NOT NULL,
    auctionsignature character varying(255) NOT NULL,
    ipaddress character varying(255)
);


ALTER TABLE public.views OWNER TO buysell;
CREATE SEQUENCE public.views_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.views_id_seq OWNER TO buysell;
ALTER SEQUENCE public.views_id_seq OWNED BY public.views.id;


--
-- TOC entry 3283 (class 2604 OID 16453)
-- Name: attachments id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.attachments ALTER COLUMN id SET DEFAULT nextval('public.attachments_id_seq'::regclass);


--
-- TOC entry 3284 (class 2604 OID 16462)
-- Name: auctions id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions ALTER COLUMN id SET DEFAULT nextval('public.auctions_id_seq'::regclass);


--
-- TOC entry 3285 (class 2604 OID 16473)
-- Name: auctions_history id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions_history ALTER COLUMN id SET DEFAULT nextval('public.auctions_history_id_seq'::regclass);


--
-- TOC entry 3286 (class 2604 OID 16487)
-- Name: auctionsearchrequests id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctionsearchrequests ALTER COLUMN id SET DEFAULT nextval('public.auctionsearchrequests_id_seq'::regclass);


--
-- TOC entry 3287 (class 2604 OID 16497)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 3288 (class 2604 OID 16506)
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- TOC entry 3289 (class 2604 OID 16516)
-- Name: reports id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.reports ALTER COLUMN id SET DEFAULT nextval('public.reports_id_seq'::regclass);


--
-- TOC entry 3290 (class 2604 OID 16526)
-- Name: usersfavourites id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.usersfavourites ALTER COLUMN id SET DEFAULT nextval('public.usersfavourites_id_seq'::regclass);


--
-- TOC entry 3291 (class 2604 OID 16535)
-- Name: vendors id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.vendors ALTER COLUMN id SET DEFAULT nextval('public.vendors_id_seq'::regclass);


--
-- TOC entry 3292 (class 2604 OID 16547)
-- Name: views id; Type: DEFAULT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.views ALTER COLUMN id SET DEFAULT nextval('public.views_id_seq'::regclass);


--
-- TOC entry 3299 (class 2606 OID 16457)
-- Name: attachments attachments_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.attachments
    ADD CONSTRAINT attachments_pkey PRIMARY KEY (id);


--
-- TOC entry 3301 (class 2606 OID 16553)
-- Name: auctions auction_signature; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions
    ADD CONSTRAINT auction_signature UNIQUE (signature);


--
-- TOC entry 3305 (class 2606 OID 16477)
-- Name: auctions_history auctions_history_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions_history
    ADD CONSTRAINT auctions_history_pkey PRIMARY KEY (id);


--
-- TOC entry 3303 (class 2606 OID 16468)
-- Name: auctions auctions_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions
    ADD CONSTRAINT auctions_pkey PRIMARY KEY (id);


--
-- TOC entry 3307 (class 2606 OID 16482)
-- Name: auctions_sequencer auctions_sequencer_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctions_sequencer
    ADD CONSTRAINT auctions_sequencer_pkey PRIMARY KEY (signature);


--
-- TOC entry 3309 (class 2606 OID 16492)
-- Name: auctionsearchrequests auctionsearchrequests_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctionsearchrequests
    ADD CONSTRAINT auctionsearchrequests_pkey PRIMARY KEY (id);


--
-- TOC entry 3311 (class 2606 OID 16501)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3313 (class 2606 OID 16511)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);

--
-- TOC entry 3315 (class 2606 OID 16521)
-- Name: reports reports_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.reports
    ADD CONSTRAINT reports_pkey PRIMARY KEY (id);


--
-- TOC entry 3321 (class 2606 OID 16555)
-- Name: vendors_contacts uk_sprynwisgt7jg3o1wuw3jj0nl; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.vendors_contacts
    ADD CONSTRAINT uk_sprynwisgt7jg3o1wuw3jj0nl UNIQUE (contactinformation_id);


--
-- TOC entry 3317 (class 2606 OID 16530)
-- Name: usersfavourites usersfavourites_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.usersfavourites
    ADD CONSTRAINT usersfavourites_pkey PRIMARY KEY (id);


--
-- TOC entry 3319 (class 2606 OID 16539)
-- Name: vendors vendors_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.vendors
    ADD CONSTRAINT vendors_pkey PRIMARY KEY (id);


--
-- TOC entry 3324 (class 2606 OID 16558)
-- Name: views views_auction_signature_ip_address; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.views
    ADD CONSTRAINT views_auction_signature_ip_address UNIQUE (auctionsignature, ipaddress);


--
-- TOC entry 3326 (class 2606 OID 16551)
-- Name: views views_pkey; Type: CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.views
    ADD CONSTRAINT views_pkey PRIMARY KEY (id);

--
-- TOC entry 3322 (class 1259 OID 16556)
-- Name: views_auction_signature; Type: INDEX; Schema: public; Owner: buysell
--

CREATE INDEX views_auction_signature ON public.views USING btree (auctionsignature);

--
-- TOC entry 3342 (class 2606 OID 16560)
-- Name: attachments fk40kt354ip16yut1hhq5vgu31b; Type: FK CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.attachments
    ADD CONSTRAINT fk40kt354ip16yut1hhq5vgu31b FOREIGN KEY (auction_id) REFERENCES public.auctions(id);


--
-- TOC entry 3345 (class 2606 OID 16580)
-- Name: vendors_contacts fkhqxg2lb15utwwna2jaem4e34a; Type: FK CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.vendors_contacts
    ADD CONSTRAINT fkhqxg2lb15utwwna2jaem4e34a FOREIGN KEY (sellerprofile_id) REFERENCES public.vendors(id);


--
-- TOC entry 3346 (class 2606 OID 16575)
-- Name: vendors_contacts fkk1iokldxxpgbctkvqqebuuss7; Type: FK CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.vendors_contacts
    ADD CONSTRAINT fkk1iokldxxpgbctkvqqebuuss7 FOREIGN KEY (contactinformation_id) REFERENCES public.contacts(id);


--
-- TOC entry 3343 (class 2606 OID 16565)
-- Name: auctionsearchrequests fkkepv9hj0i375wuisvgsp4w2xp; Type: FK CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.auctionsearchrequests
    ADD CONSTRAINT fkkepv9hj0i375wuisvgsp4w2xp FOREIGN KEY (usersfavourite_id) REFERENCES public.usersfavourites(id);


--
-- TOC entry 3344 (class 2606 OID 16570)
-- Name: reports fkqd2juomqlomdfqaidqomdj2kr; Type: FK CONSTRAINT; Schema: public; Owner: buysell
--

ALTER TABLE ONLY public.reports
    ADD CONSTRAINT fkqd2juomqlomdfqaidqomdj2kr FOREIGN KEY (auction_id) REFERENCES public.auctions(id);






--CREATE TABLE public.jv_commit (
--    commit_pk bigint NOT NULL,
--    author character varying(200),
--    commit_date timestamp without time zone,
--    commit_date_instant character varying(30),
--    commit_id numeric(22,2)
--);
--
--
--ALTER TABLE public.jv_commit OWNER TO buysell;
--
----
---- TOC entry 243 (class 1259 OID 16606)
---- Name: jv_commit_pk_seq; Type: SEQUENCE; Schema: public; Owner: buysell
----
--
--CREATE SEQUENCE public.jv_commit_pk_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--
--
--ALTER SEQUENCE public.jv_commit_pk_seq OWNER TO buysell;
--
----
---- TOC entry 244 (class 1259 OID 16607)
---- Name: jv_commit_property; Type: TABLE; Schema: public; Owner: buysell
----
--
--CREATE TABLE public.jv_commit_property (
--    property_name character varying(191) NOT NULL,
--    property_value character varying(600),
--    commit_fk bigint NOT NULL
--);
--
--
--ALTER TABLE public.jv_commit_property OWNER TO buysell;
--
----
---- TOC entry 240 (class 1259 OID 16585)
---- Name: jv_global_id; Type: TABLE; Schema: public; Owner: buysell
----
--
--CREATE TABLE public.jv_global_id (
--    global_id_pk bigint NOT NULL,
--    local_id character varying(191),
--    fragment character varying(200),
--    type_name character varying(200),
--    owner_id_fk bigint
--);
--
--
--ALTER TABLE public.jv_global_id OWNER TO buysell;
--
----
---- TOC entry 241 (class 1259 OID 16599)
---- Name: jv_global_id_pk_seq; Type: SEQUENCE; Schema: public; Owner: buysell
----
--
--CREATE SEQUENCE public.jv_global_id_pk_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--
--
--ALTER SEQUENCE public.jv_global_id_pk_seq OWNER TO buysell;
--
----
---- TOC entry 245 (class 1259 OID 16621)
---- Name: jv_snapshot; Type: TABLE; Schema: public; Owner: buysell
----
--
--CREATE TABLE public.jv_snapshot (
--    snapshot_pk bigint NOT NULL,
--    type character varying(200),
--    version bigint,
--    state text,
--    changed_properties text,
--    managed_type character varying(200),
--    global_id_fk bigint,
--    commit_fk bigint
--);
--
--
--ALTER TABLE public.jv_snapshot OWNER TO buysell;
--
----
---- TOC entry 246 (class 1259 OID 16640)
---- Name: jv_snapshot_pk_seq; Type: SEQUENCE; Schema: public; Owner: buysell
----
--
--CREATE SEQUENCE public.jv_snapshot_pk_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--
--
--ALTER SEQUENCE public.jv_snapshot_pk_seq OWNER TO buysell;
--
----
---- TOC entry 3333 (class 2606 OID 16604)
---- Name: jv_commit jv_commit_pk; Type: CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_commit
--    ADD CONSTRAINT jv_commit_pk PRIMARY KEY (commit_pk);
--
--
----
---- TOC entry 3336 (class 2606 OID 16613)
---- Name: jv_commit_property jv_commit_property_pk; Type: CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_commit_property
--    ADD CONSTRAINT jv_commit_property_pk PRIMARY KEY (commit_fk, property_name);
--
--
----
---- TOC entry 3330 (class 2606 OID 16591)
---- Name: jv_global_id jv_global_id_pk; Type: CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_global_id
--    ADD CONSTRAINT jv_global_id_pk PRIMARY KEY (global_id_pk);
--
--
----
---- TOC entry 3341 (class 2606 OID 16627)
---- Name: jv_snapshot jv_snapshot_pk; Type: CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_snapshot
--    ADD CONSTRAINT jv_snapshot_pk PRIMARY KEY (snapshot_pk);
--
--
----
---- TOC entry 3331 (class 1259 OID 16605)
---- Name: jv_commit_commit_id_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_commit_commit_id_idx ON public.jv_commit USING btree (commit_id);
--
--
----
---- TOC entry 3334 (class 1259 OID 16619)
---- Name: jv_commit_property_commit_fk_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_commit_property_commit_fk_idx ON public.jv_commit_property USING btree (commit_fk);
--
--
----
---- TOC entry 3337 (class 1259 OID 16620)
---- Name: jv_commit_property_property_name_property_value_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_commit_property_property_name_property_value_idx ON public.jv_commit_property USING btree (property_name, property_value);
--
--
----
---- TOC entry 3327 (class 1259 OID 16597)
---- Name: jv_global_id_local_id_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_global_id_local_id_idx ON public.jv_global_id USING btree (local_id);
--
--
----
---- TOC entry 3328 (class 1259 OID 16598)
---- Name: jv_global_id_owner_id_fk_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_global_id_owner_id_fk_idx ON public.jv_global_id USING btree (owner_id_fk);
--
--
----
---- TOC entry 3338 (class 1259 OID 16639)
---- Name: jv_snapshot_commit_fk_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_snapshot_commit_fk_idx ON public.jv_snapshot USING btree (commit_fk);
--
--
----
---- TOC entry 3339 (class 1259 OID 16638)
---- Name: jv_snapshot_global_id_fk_idx; Type: INDEX; Schema: public; Owner: buysell
----
--
--CREATE INDEX jv_snapshot_global_id_fk_idx ON public.jv_snapshot USING btree (global_id_fk);
--
----
---- TOC entry 3348 (class 2606 OID 16614)
---- Name: jv_commit_property jv_commit_property_commit_fk; Type: FK CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_commit_property
--    ADD CONSTRAINT jv_commit_property_commit_fk FOREIGN KEY (commit_fk) REFERENCES public.jv_commit(commit_pk);
--
--
----
---- TOC entry 3347 (class 2606 OID 16592)
---- Name: jv_global_id jv_global_id_owner_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_global_id
--    ADD CONSTRAINT jv_global_id_owner_id_fk FOREIGN KEY (owner_id_fk) REFERENCES public.jv_global_id(global_id_pk);
--
--
----
---- TOC entry 3349 (class 2606 OID 16633)
---- Name: jv_snapshot jv_snapshot_commit_fk; Type: FK CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_snapshot
--    ADD CONSTRAINT jv_snapshot_commit_fk FOREIGN KEY (commit_fk) REFERENCES public.jv_commit(commit_pk);
--
--
----
---- TOC entry 3350 (class 2606 OID 16628)
---- Name: jv_snapshot jv_snapshot_global_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: buysell
----
--
--ALTER TABLE ONLY public.jv_snapshot
--    ADD CONSTRAINT jv_snapshot_global_id_fk FOREIGN KEY (global_id_fk) REFERENCES public.jv_global_id(global_id_pk);







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









