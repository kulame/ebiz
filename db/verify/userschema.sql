-- Verify ebiz:userschema on pg

BEGIN;

-- XXX Add verifications here.
select count(*) from ebiz_users limit 1;
ROLLBACK;
