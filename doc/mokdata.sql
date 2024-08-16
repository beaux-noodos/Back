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




-- Insérer des données dans la table "user"
INSERT INTO "user" (id, firstname, lastname, mail, birthdate, role, sex, creation_datetime, last_update_datetime, photo_key, photo_banner_key, username, status)
VALUES
    (UUID_GENERATE_V4(), 'John', 'Doe', 'john.doe@example.com', '1985-01-01', 'MANAGER', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo_john_key', 'banner_john_key', 'johndoe', 'ENABLED'),
    (UUID_GENERATE_V4(), 'Jane', 'Smith', 'jane.smith@example.com', '1990-02-02', 'INVESTOR', 'F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo_jane_key', 'banner_jane_key', 'janesmith', 'ENABLED'),
    (UUID_GENERATE_V4(), 'Alice', 'Wonder', 'alice.wonder@example.com', '1987-03-03', 'TECHNICAL_SOLUTION', 'F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo_alice_key', 'banner_alice_key', 'alicewonder', 'ENABLED'),
    (UUID_GENERATE_V4(), 'Bob', 'Builder', 'bob.builder@example.com', '1982-04-04', 'PROJECT_OWNER', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'photo_bob_key', 'banner_bob_key', 'bobbuilder', 'ENABLED');

-- Insérer des données dans la table "user_auth"
INSERT INTO "user_auth" (id, user_id, password)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'password123'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'securepassword'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'), 'alicepassword'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'bob.builder@example.com'), 'builderpassword');

-- Insérer des données dans la table "location"
INSERT INTO location (id, name, description, latitude, longitude)
VALUES
    (UUID_GENERATE_V4(), 'Antananarivo', 'Capital city of Madagascar', '-18.8792', '47.5079'),
    (UUID_GENERATE_V4(), 'Toamasina', 'Major port city in Madagascar', '-18.1492', '49.4022');

-- Insérer des données dans la table "project_category"
INSERT INTO project_category (id, name, description)
VALUES
    (UUID_GENERATE_V4(), 'Software Development', 'Projects related to software development'),
    (UUID_GENERATE_V4(), 'Infrastructure', 'Projects related to building infrastructure');

-- Insérer des données dans la table "project"
INSERT INTO project (id, user_id, title, description, project_status, image_key, price, investor_id, technical_solution_id, localisation_id, investor_need, technical_solution_need, start_datetime, end_datetime, picture_is_implemented)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'E-commerce Platform', 'Building a scalable e-commerce platform.', 'IN_PROGRESS', 'ecommerce_image_key', 50000.00, (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'), (SELECT id FROM location WHERE name = 'Antananarivo'), TRUE, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + interval '30 days', TRUE),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'bob.builder@example.com'), 'Road Construction', 'Constructing a new highway.', 'PLANNING', 'road_image_key', 2000000.00, NULL, (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'), (SELECT id FROM location WHERE name = 'Toamasina'), TRUE, TRUE, CURRENT_TIMESTAMP + interval '10 days', CURRENT_TIMESTAMP + interval '90 days', FALSE);

-- Insérer des données dans la table "project_reaction"
INSERT INTO project_reaction (id, user_id, like_reaction, vision, stars_number, comment, project_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'LIKE', TRUE, 5, 'Great project!', (SELECT id FROM project WHERE title = 'E-commerce Platform')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'DISLIKE', FALSE, 2, 'Not convinced.', (SELECT id FROM project WHERE title = 'Road Construction'));

-- Insérer des données dans la table "project_session"
INSERT INTO project_session (id, project_id, location_id, title, description, end_datetime)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM project WHERE title = 'E-commerce Platform'), (SELECT id FROM location WHERE name = 'Antananarivo'), 'Development Sprint 1', 'First sprint for development.', CURRENT_TIMESTAMP + interval '15 days'),
    (UUID_GENERATE_V4(), (SELECT id FROM project WHERE title = 'Road Construction'), (SELECT id FROM location WHERE name = 'Toamasina'), 'Initial Survey', 'Surveying the construction site.', CURRENT_TIMESTAMP + interval '20 days');

-- Insérer des données dans la table "project_session_reaction"
INSERT INTO project_session_reaction (id, user_id, like_reaction, vision, stars_number, comment, project_session_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'), 'LIKE', TRUE, 4, 'Good progress on the project.', (SELECT id FROM project_session WHERE title = 'Development Sprint 1')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'DISLIKE', FALSE, 1, 'Too slow.', (SELECT id FROM project_session WHERE title = 'Initial Survey'));

-- Insérer des données dans la table "project_project_category"
INSERT INTO project_project_category (project_id, project_category_id)
VALUES
    ((SELECT id FROM project WHERE title = 'E-commerce Platform'), (SELECT id FROM project_category WHERE name = 'Software Development')),
    ((SELECT id FROM project WHERE title = 'Road Construction'), (SELECT id FROM project_category WHERE name = 'Infrastructure'));

-- Insérer des données dans la table "message_chat_format_entity"
INSERT INTO message_chat_format_entity (id, user_id, client_message, assistant_message)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'Hello, can you assist me with my project?', 'Sure, what help do you need?'),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'What is the status of the road construction?', 'It is currently in the planning phase.');

-- Insérer des données dans la table "signalisation"
INSERT INTO signalisation (id, user_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com')),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'bob.builder@example.com'));

-- Insérer des données dans la table "signalisation_reaction"
INSERT INTO signalisation_reaction (id, user_id, like_reaction, vision, stars_number, comment, signalisation_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'LIKE', TRUE, 4, 'Well flagged.', (SELECT id FROM signalisation WHERE user_id = (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'))),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'jane.smith@example.com'), 'DISLIKE', FALSE, 1, 'Not helpful.', (SELECT id FROM signalisation WHERE user_id = (SELECT id FROM "user" WHERE mail = 'bob.builder@example.com')));

-- Insérer des données dans la table "notification"
INSERT INTO notification (id, user_id, title, body, is_read)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'alice.wonder@example.com'), 'New Project Assigned', 'You have been assigned to a new project.', FALSE),
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'bob.builder@example.com'), 'Milestone Reached', 'Your project has reached its first milestone.', TRUE);


-- Insérer des données dans la table "notification_reaction"
INSERT INTO notification_reaction (id, user_id, like_reaction, vision, stars_number, comment, notification_id)
VALUES
    (UUID_GENERATE_V4(), (SELECT id FROM "user" WHERE mail = 'john.doe@example.com'), 'LIKE', TRUE, 5, 'Great news!', (SELECT id FROM notification WHERE title = 'New Project Assigned'));