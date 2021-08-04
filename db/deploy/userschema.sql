-- Deploy ebiz:userschema to pg

BEGIN;

-- XXX Add DDLs here.
CREATE TABLE "ebiz_users" (
  "id" bigserial NOT NULL,
  "email" varchar(128) NOT NULL,
  "password_crypt" varchar(128) NOT NULL,
  "is_staff" boolean NOT NULL,
  "is_active" boolean NOT NULL,
  "node" text,
  "nickname" varchar(128),
  "avator" varchar(255),
  "date_joined" timestamp without time zone,
  unique(email),
  unique(nickname)
);


COMMIT;
