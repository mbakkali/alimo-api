create table master_tenant
(
    id        bigserial not null
        constraint master_tenant_pkey
            primary key,
    password  varchar(30),
    tenant_id varchar(30),
    url       varchar(256),
    username  varchar(30),
    version   integer   not null
);

alter table master_tenant
    owner to postgres;

INSERT INTO public.master_tenant (id, password, tenant_id, url, username, version) VALUES (1, 'root', 'tenant_1', 'jdbc:postgresql://masterdb.cysqtq99l005.eu-west-3.rds.amazonaws.com:5432/dbtenant1', 'root', 1);