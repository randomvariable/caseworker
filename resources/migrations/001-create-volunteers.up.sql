create table volunteer (
  volunteer_id integer primary key,
  first_name text not null,
  last_name text not null,
  email text not null,
  password_hash text not null,
  date_created timestamp not null default current_timestamp,
  date_updated timestamp not null default current_timestamp,
  date_deleted timestamp
);
