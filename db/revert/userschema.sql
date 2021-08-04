-- Revert ebiz:userschema from pg

BEGIN;

-- XXX Add DDLs here.
drop table ebiz_users;
COMMIT;
