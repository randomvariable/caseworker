-- :name accounts-for-organisation :? :*
select account_id
     , email
     , name
     , created_by
     , created_at
     , updated_by
     , updated_at
from account
where account_id != -1
order by name;
