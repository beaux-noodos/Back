CREATE TABLE course_session (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        course_id VARCHAR(255),
                        location_id VARCHAR(255),
                        like_number BIGINT DEFAULT 0,
                        dislike_number BIGINT DEFAULT 0,
                        view_number BIGINT DEFAULT 0,
                        star_number BIGINT DEFAULT 0,
                        star_medium DOUBLE PRECISION DEFAULT 2.5,
                        professor_id VARCHAR,
                        title VARCHAR,
                        description TEXT,
                        start_datetime TIMESTAMP,
                        end_datetime TIMESTAMP,
                        CONSTRAINT fk_course_session_user FOREIGN KEY (professor_id) REFERENCES "user"(id),
                        CONSTRAINT fk_course_session_course FOREIGN KEY (course_id) REFERENCES course(id),
                        CONSTRAINT fk_course_session_location FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE course_session_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction VARCHAR(255),
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 course_session_id VARCHAR(255),
                                 CONSTRAINT fk_course_session_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_course_session_reaction_course_session FOREIGN KEY (course_session_id) REFERENCES course_session(id)
);

create index if not exists course_session_reaction_user_id_index on "course_session_reaction" (user_id);
create index if not exists course_session_reaction_like_reaction_index on "course_session_reaction" (like_reaction);
create index if not exists course_session_reaction_vision_index on "course_session_reaction" (vision);
create index if not exists course_session_reaction_stars_number_index on "course_session_reaction" (stars_number);
create index if not exists course_session_reaction_comment_index on "course_session_reaction" (comment);
create index if not exists course_session_reaction_course_session_id_index on "course_session_reaction" (course_session_id);


CREATE TABLE course_course_category (
                                        course_id VARCHAR REFERENCES course(id),
                                        course_category_id VARCHAR REFERENCES course_category(id),
                                        PRIMARY KEY (course_id, course_category_id)
);