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
    ('client1_id', 'Ryan', 'Andria', 'test@gmail.com', '01-01-1995', 'TECHNICAL_SOLUTION', 'M', '2000-01-01T08:12:20.00z', '2021-11-08T08:25:24.00Z', 'client1_profile_key', 'client1_banner_key', 'username_client1', 'ENABLED'),
    ('client2_id', 'Herilala', 'Raf', 'hei.hajatiana@gmail.com', '01-01-2002', 'PROJECT_OWNER', 'F', '2002-01-01T08:12:20.00z', '2020-11-08T08:25:24.00Z', null, null, 'username_client2', 'ENABLED'),
    ('manager1_id', 'Vano', 'Andria', 'test+vano@hei.school', '01-01-2000', 'MANAGER', 'M', '2000-09-01T08:12:20.00z', '2021-08-08T08:25:24.00Z', null, null, 'username_manager1', 'ENABLED');

INSERT INTO "user_auth" (
    id,
    user_id,
    password
)
VALUES
    ('client1_auth_id', 'client1_id', '$2a$10$15YS98M8iBdLjBqFs9UND.vM9PXElyueBZfBofBpZSnWlXKFClY62'), --'client1_password'
    ('client2_auth_id', 'client2_id', '$2a$10$FXHmCbUvNnHsUk9OM/g15umW6v/4VlrmALkIEgks5RP41sWiUeBJ6'), --'client2_password'
    ('manager1_auth_id', 'manager1_id', '$2a$10$bijji7vf6G9INYnajSWa0O4I7o4W/H0qNcpvENOBitEZPXq.V4WHS'); --'manager1_password'




-- Insertion de données pour la table dummy
INSERT INTO dummy (id) VALUES ('dummy-table-id-1'), ('dummy-table-id-2'), ('dummy-table-id-3');

-- Insertion de données pour la table dummy_uuid
INSERT INTO dummy_uuid (id) VALUES ('dummy-uuid-id-1'), ('dummy-uuid-id-2'), ('dummy-uuid-id-3');

-- Insertion de données pour la table user
INSERT INTO "user" (id, firstname, lastname, mail, birthdate, role, sex, creation_datetime, last_update_datetime, photo_key, photo_banner_key, username, status)
VALUES
   (UUID_GENERATE_V4(), 'John', 'Doe', 'john.doe@example.com', '1985-06-15', 'CLIENT', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo1.jpg', 'banner1.jpg', 'johndoe', 'ENABLED'),
    (UUID_GENERATE_V4(), 'Jane', 'Smith', 'jane.smith@example.com', '1990-08-20', 'MANAGER', 'F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo2.jpg', 'banner2.jpg', 'janesmith', 'ENABLED'),
    (UUID_GENERATE_V4(), 'Alice', 'Johnson', 'alice.johnson@example.com', '1995-12-10', 'CLIENT', 'F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo3.jpg', 'banner3.jpg', 'alicejohnson', 'BANISHED');

-- Insertion de données pour la table user_auth
INSERT INTO "user_auth" (id, user_id, password)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'password123'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'password456'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), 'password789');

-- Insertion de données pour la table location
INSERT INTO location (id, creation_datetime, last_update_datetime, like_number, dislike_number, view_number, star_number, star_medium, name, description, latitude, longitude)
VALUES
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10, 2, 100, 5, 4.5, 'Location 1', 'Description of Location 1', '48.8566', '2.3522'),
   (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 20, 5, 200, 10, 4.8, 'Location 2', 'Description of Location 2', '34.0522', '-118.2437'),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 15, 3, 150, 8, 4.7, 'Location 3', 'Description of Location 3', '51.5074', '-0.1278');

-- Insertion de données pour la table location_reaction
INSERT INTO location_reaction (id, user_id, creation_datetime, last_update_datetime, like_reaction, vision, stars_number, comment, location_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 5, 'Great place!', (SELECT id FROM location WHERE name = 'Location 1')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DISLIKE', FALSE, 2, 'Not what I expected.', (SELECT id FROM location WHERE name = 'Location 2')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 4, 'Pretty nice.', (SELECT id FROM location WHERE name = 'Location 3'));

-- Insertion de données pour la table project_category
INSERT INTO project_category (id, creation_datetime, last_update_datetime, name, description)
VALUES
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Category 1', 'Description for Category 1'),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Category 2', 'Description for Category 2'),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Category 3', 'Description for Category 3');

-- Insertion de données pour la table project
INSERT INTO project (id, creation_datetime, last_update_datetime, user_id, like_number, dislike_number, view_number, star_number, star_medium, title, description, project_status, image_key, price)
VALUES
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 30, 5, 300, 20, 4.6, 'Project 1', 'Description of Project 1', 'PLANNING', 'image1.jpg', 100.0),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 40, 2, 400, 25, 4.9, 'Project 2', 'Description of Project 2', 'IN_PROGRESS', 'image2.jpg', 200.0),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), 50, 3, 500, 30, 5.0, 'Project 3', 'Description of Project 3', 'COMPLETED', 'image3.jpg', 300.0);

-- Insertion de données pour la table project_reaction
INSERT INTO project_reaction (id, user_id, creation_datetime, last_update_datetime, like_reaction, vision, stars_number, comment, project_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 5, 'Excellent project!', (SELECT id FROM project WHERE title = 'Project 1')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DISLIKE', FALSE, 2, 'Not satisfied.', (SELECT id FROM project WHERE title = 'Project 2')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 4, 'Very informative.', (SELECT id FROM project WHERE title = 'Project 3'));

-- Insertion de données pour la table project_followers
INSERT INTO project_followers (project_id, user_id)
VALUES
    ((SELECT id FROM project WHERE title = 'Project 1'), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com')),
    ((SELECT id FROM project WHERE title = 'Project 2'), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com')),
    ((SELECT id FROM project WHERE title = 'Project 3'), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'));

-- Insertion de données pour la table project_interested
INSERT INTO project_interested (project_id, user_id)
VALUES
    ((SELECT id FROM project WHERE title = 'Project 1'), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com')),
    ((SELECT id FROM project WHERE title = 'Project 2'), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com')),
    ((SELECT id FROM project WHERE title = 'Project 3'), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'));

-- Insertion de données pour la table project_session
INSERT INTO project_session (id, creation_datetime, last_update_datetime, project_id, location_id, like_number, dislike_number, view_number, star_number, star_medium, professor_id, title, description, start_datetime, end_datetime)
VALUES
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM project WHERE title = 'Project 1'), (SELECT id FROM location WHERE name = 'Location 1'), 100, 10, 1000, 50, 4.8, (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'Session 1', 'Description of Session 1', CURRENT_TIMESTAMP + INTERVAL '7 days', CURRENT_TIMESTAMP + INTERVAL '14 days'),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM project WHERE title = 'Project 2'), (SELECT id FROM location WHERE name = 'Location 2'), 200, 15, 2000, 70, 4.9, (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'Session 2', 'Description of Session 2', CURRENT_TIMESTAMP + INTERVAL '10 days', CURRENT_TIMESTAMP + INTERVAL '20 days'),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM project WHERE title = 'Project 3'), (SELECT id FROM location WHERE name = 'Location 3'), 150, 20, 1500, 60, 5.0, (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), 'Session 3', 'Description of Session 3', CURRENT_TIMESTAMP + INTERVAL '15 days', CURRENT_TIMESTAMP + INTERVAL '30 days');

-- Insertion de données pour la table project_session_reaction
INSERT INTO project_session_reaction (id, user_id, creation_datetime, last_update_datetime, like_reaction, vision, stars_number, comment, project_session_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 5, 'Amazing session!', (SELECT id FROM project_session WHERE title = 'Session 1')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DISLIKE', FALSE, 3, 'Could be better.', (SELECT id FROM project_session WHERE title = 'Session 2')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 4, 'Good session.', (SELECT id FROM project_session WHERE title = 'Session 3'));

-- Insertion de données pour la table project_project_category
INSERT INTO project_project_category (project_id, project_category_id)
VALUES
    ((SELECT id FROM project WHERE title = 'Project 1'), (SELECT id FROM project_category WHERE name = 'Category 1')),
    ((SELECT id FROM project WHERE title = 'Project 2'), (SELECT id FROM project_category WHERE name = 'Category 2')),
    ((SELECT id FROM project WHERE title = 'Project 3'), (SELECT id FROM project_category WHERE name = 'Category 3'));

-- Insertion de données pour la table message_chat_format_entity
INSERT INTO message_chat_format_entity (id, user_id, client_message, assistant_message, creation_datetime)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'Hello, how can I assist you today?', 'Sure, I can help with that!', CURRENT_TIMESTAMP),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'I need some information about your services.', 'Certainly, here are the details...', CURRENT_TIMESTAMP),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), 'Can you guide me through the process?', 'Of project, let me explain step by step.', CURRENT_TIMESTAMP);

-- Insertion de données pour la table signalisation
INSERT INTO signalisation (id, creation_datetime, last_update_datetime, user_id)
VALUES
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'john.doe@example.com')),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com')),
    (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'));

-- Insertion de données pour la table signalisation_reaction
INSERT INTO signalisation_reaction (id, user_id, creation_datetime, last_update_datetime, like_reaction, vision, stars_number, comment, signalisation_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 4, 'This needs attention.', (SELECT id FROM signalisation WHERE user_id = (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'))),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DISLIKE', FALSE, 2, 'Not a big issue.', (SELECT id FROM signalisation WHERE user_id = (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'))),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 5, 'Urgent attention needed!', (SELECT id FROM signalisation WHERE user_id = (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com')));


-- Insertion de données pour la table notification
INSERT INTO notification (id, user_id, title, body, is_read, creation_datetime)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'Notification 1', 'This is your first notification.', FALSE, CURRENT_TIMESTAMP),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'Notification 2', 'This is your second notification.', TRUE, CURRENT_TIMESTAMP),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), 'Notification 3', 'This is your third notification.', FALSE, CURRENT_TIMESTAMP);

-- Insertion de données pour la table notification_reaction
INSERT INTO notification_reaction (id, user_id, creation_datetime, last_update_datetime, like_reaction, vision, stars_number, comment, notification_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 5, 'Very helpful notification.', (SELECT id FROM notification WHERE title = 'Notification 1')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DISLIKE', FALSE, 2, 'Not relevant to me.', (SELECT id FROM notification WHERE title = 'Notification 2')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LIKE', TRUE, 4, 'Good to know.', (SELECT id FROM notification WHERE title = 'Notification 3'));