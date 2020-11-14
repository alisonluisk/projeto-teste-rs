CREATE TABLE public.conta_pagar
(
    id serial,
    nome character varying(255) NOT NULL,
    valor numeric(15,2) NOT NULL,
    valor_corrigido numeric(15,2) NOT NULL,
    dias_atraso integer NOT NULL,
    data_vencimento date NOT NULL,
    data_pagamento date NOT NULL,
    multa numeric(15,2) NOT NULL,
    juros_dia numeric(15,2) NOT NULL,
    CONSTRAINT conta_pagar_pkey PRIMARY KEY (id)
);
