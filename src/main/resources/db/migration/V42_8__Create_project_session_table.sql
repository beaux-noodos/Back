CREATE TABLE project_session (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        project_id VARCHAR(255),
                        location_id VARCHAR(255),
                        like_number BIGINT DEFAULT 0,
                        dislike_number BIGINT DEFAULT 0,
                        view_number BIGINT DEFAULT 0,
                        star_number BIGINT DEFAULT 0,
                        star_medium DOUBLE PRECISION DEFAULT 2.5,
                        title VARCHAR,
                        description TEXT,
                        end_datetime TIMESTAMP,
                        CONSTRAINT fk_project_session_project FOREIGN KEY (project_id) REFERENCES project(id),
                        CONSTRAINT fk_project_session_location FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE project_session_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction VARCHAR(255),
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 project_session_id VARCHAR(255),
                                 CONSTRAINT fk_project_session_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_project_session_reaction_project_session FOREIGN KEY (project_session_id) REFERENCES project_session(id)
);

create index if not exists project_session_reaction_user_id_index on "project_session_reaction" (user_id);
create index if not exists project_session_reaction_like_reaction_index on "project_session_reaction" (like_reaction);
create index if not exists project_session_reaction_vision_index on "project_session_reaction" (vision);
create index if not exists project_session_reaction_stars_number_index on "project_session_reaction" (stars_number);
create index if not exists project_session_reaction_comment_index on "project_session_reaction" (comment);
create index if not exists project_session_reaction_project_session_id_index on "project_session_reaction" (project_session_id);


CREATE TABLE project_project_category (
                                        project_id VARCHAR REFERENCES project(id),
                                        project_category_id VARCHAR REFERENCES project_category(id),
                                        PRIMARY KEY (project_id, project_category_id)
);