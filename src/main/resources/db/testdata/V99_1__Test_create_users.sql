INSERT INTO "user" (
    id,
    firstname,
    lastname,
    mail,
    birthdate,
    role,
    sex,
    creation_datetime,
    last_update_datetime,
    photo_key,
    photo_banner_key,
    username,
    status
)
VALUES
    ('client1_id', 'Ryan', 'Andria', 'test@gmail.com', '01-01-1995', 'MANAGER', 'M', '2000-01-01T08:12:20.00z', '2021-11-08T08:25:24.00Z', 'client1_profile_key', 'client1_banner_key', 'username_client1', 'ENABLED'),
    ('client2_id', 'Herilala', 'Raf', 'hei.hajatiana@gmail.com', '01-01-2002', 'MANAGER', 'F', '2002-01-01T08:12:20.00z', '2020-11-08T08:25:24.00Z', null, null, 'username_client2', 'ENABLED'),
    ('manager1_id', 'Vano', 'Andria', 'test+vano@hei.school', '01-01-2000', 'MANAGER', 'M', '2000-09-01T08:12:20.00z', '2021-08-08T08:25:24.00Z', null, null, 'username_manager1', 'ENABLED');
