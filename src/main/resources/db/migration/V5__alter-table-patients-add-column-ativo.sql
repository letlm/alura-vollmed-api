alter table patients add ativo tinyint;
update patients set ativo = 1;
alter table patients modify ativo tinyint not null;
