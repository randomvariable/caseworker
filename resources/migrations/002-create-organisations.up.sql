create table organisation (
  organisation_id serial primary key,
  name            text not null,
  slug            text unique not null,
  created_at      timestamp not null default current_timestamp,
  created_by      integer not null references account(account_id),
  updated_at      timestamp not null default current_timestamp,
  updated_by      integer not null references account(account_id)
);

create table organisation_history (
  organisation_history_id serial primary key,
  organisation_id         integer not null references organisation(organisation_id),
  name                    text not null,
  slug                    text unique not null,
  created_at              timestamp not null default current_timestamp,
  created_by              integer not null references account(account_id),
  updated_at              timestamp not null default current_timestamp,
  updated_by              integer not null references account(account_id),
  deleted_at              timestamp,
  deleted_by              integer references account(account_id)
);

create table organisation_account (
  organisation_account_id serial primary key,
  organisation_id         integer not null references organisation(organisation_id),
  account_id              integer not null references account(account_id),
  created_at              timestamp not null default current_timestamp,
  created_by              integer not null references account(account_id),
  updated_at              timestamp not null default current_timestamp,
  updated_by              integer not null references account(account_id),
  unique (organisation_id, account_id)
);

create table organisation_account_history (
  organisation_account_history_id serial primary key,
  organisation_account_id         integer not null references organisation_account(organisation_account_id),
  organisation_id                 integer not null references organisation(organisation_id),
  account_id                      integer not null references account(account_id),
  created_at                      timestamp not null default current_timestamp,
  created_by                      integer not null references account(account_id),
  updated_at                      timestamp not null default current_timestamp,
  updated_by                      integer not null references account(account_id),
  deleted_at                      timestamp,
  deleted_by                      integer references account(account_id)
);
