CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

do
$$
begin
        if not exists(select from pg_type where typname = 'role') then
create type "role" as enum ('TECHNICAL_SOLUTION', 'MANAGER', 'INVESTOR', 'PROJECT_OWNER');
end if;
        if not exists(select from pg_type where typname = 'sex') then
create type sex as enum ('M', 'F', 'OTHER');
end if;
        if not exists(select from pg_type where typname = 'user_status') then
create type user_status as enum ('ENABLED', 'BANISHED');
end if;
end
$$;

CREATE TABLE IF NOT EXISTS "user" (
   id VARCHAR PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
   firstname VARCHAR(100),
   lastname VARCHAR(100),
   mail VARCHAR(250) UNIQUE NOT NULL,
   birthdate DATE,
   role role NOT NULL,
    sex sex,
   creation_datetime TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp NOT NULL,
   last_update_datetime TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
                                      photo_key VARCHAR,
                                      photo_banner_key VARCHAR,
                                      username VARCHAR,
                                      status user_status
);

create index if not exists user_firstname_index on "user" (firstname);
create index if not exists user_lastname_index on "user" (lastname);