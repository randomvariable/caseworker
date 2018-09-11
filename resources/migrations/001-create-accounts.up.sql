create table account (
  account_id serial primary key,
  name       text not null,
  email      text unique not null,
  oauth_id   text unique,
  created_at timestamp not null default current_timestamp,
  created_by integer not null,
  updated_at timestamp not null default current_timestamp,
  updated_by integer not null
);

create table account_history (
  account_history_id serial primary key,
  account_id         integer not null references account(account_id),
  name               text not null,
  email              text not null,
  oauth_id           text,
  created_at         timestamp not null default current_timestamp,
  created_by         integer not null,
  updated_at         timestamp not null default current_timestamp,
  updated_by         integer not null references account(account_id),
  deleted_at         timestamp,
  deleted_by         integer references account(account_id)
);

insert into account
(account_id, name, email, oauth_id, created_by, updated_by)
values
(-1, 'Caseworker', 'info@caseworker-oss.org', '00000000-0000-0000-0000-000000000000', -1, -1);

insert into account_history
select -1, *
from account where account_id = -1;

alter table account
add constraint fk_created_by foreign key (created_by) references account(account_id);

alter table account
add constraint fk_updated_by foreign key (updated_by) references account(account_id);
