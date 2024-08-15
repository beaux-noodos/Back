CREATE TABLE location (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        like_number BIGINT DEFAULT 0,
                        dislike_number BIGINT DEFAULT 0,
                        view_number BIGINT DEFAULT 0,
                        star_number BIGINT DEFAULT 0,
                        star_medium DOUBLE PRECISION DEFAULT 2.5,
                        name VARCHAR,
                        description TEXT,
                        latitude VARCHAR,
                        longitude VARCHAR
);

CREATE TABLE location_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction VARCHAR(255),
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 location_id VARCHAR(255),
                                 CONSTRAINT fk_location_reaction_user FOREIGN KEY (user_id) REFERENCES   "user"(id),
                                 CONSTRAINT fk_location_reaction_location FOREIGN KEY (location_id) REFERENCES location(id)
);

create index if not exists location_reaction_user_id_index on "location_reaction" (user_id);
create index if not exists location_reaction_like_reaction_index on "location_reaction" (like_reaction);
create index if not exists location_reaction_vision_index on "location_reaction" (vision);
create index if not exists location_reaction_stars_number_index on "location_reaction" (stars_number);
create index if not exists location_reaction_comment_index on "location_reaction" (comment);
create index if not exists location_reaction_location_id_index on "location_reaction" (location_id);