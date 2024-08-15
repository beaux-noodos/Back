do
$$
begin
        if not exists(select from pg_type where typname = 'course_status') then
create type course_status as enum ('PLANNING', 'CONFIRMED', 'IN_PROGRESS', 'COMPLETED');
end if;
end
$$;

CREATE TABLE course (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        user_id VARCHAR(255),
                        like_number BIGINT DEFAULT 0,
                        dislike_number BIGINT DEFAULT 0,
                        view_number BIGINT DEFAULT 0,
                        star_number BIGINT DEFAULT 0,
                        star_medium DOUBLE PRECISION DEFAULT 2.5,
                        title VARCHAR,
                        description TEXT,
                        course_status course_status,
                        image_key VARCHAR,
                        price DOUBLE PRECISION,
                        CONSTRAINT fk_course_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE course_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction VARCHAR(255),
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 course_id VARCHAR(255),
                                 CONSTRAINT fk_course_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_course_reaction_course FOREIGN KEY (course_id) REFERENCES course(id)
);

create index if not exists course_reaction_user_id_index on "course_reaction" (user_id);
create index if not exists course_reaction_like_reaction_index on "course_reaction" (like_reaction);
create index if not exists course_reaction_vision_index on "course_reaction" (vision);
create index if not exists course_reaction_stars_number_index on "course_reaction" (stars_number);
create index if not exists course_reaction_comment_index on "course_reaction" (comment);
create index if not exists course_reaction_course_id_index on "course_reaction" (course_id);


CREATE TABLE course_followers (
                                  course_id VARCHAR REFERENCES course(id),
                                  user_id VARCHAR REFERENCES "user"(id),
                                  PRIMARY KEY (course_id, user_id)
);

CREATE TABLE course_interested (
                                   course_id VARCHAR REFERENCES course(id),
                                   user_id VARCHAR REFERENCES "user"(id),
                                   PRIMARY KEY (course_id, user_id)
);
