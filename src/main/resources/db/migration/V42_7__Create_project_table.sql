do
$$
begin
        if not exists(select from pg_type where typname = 'project_status') then
create type project_status as enum ('PLANNING', 'CONFIRMED', 'IN_PROGRESS', 'COMPLETED');
end if;
end
$$;

CREATE TABLE project (
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
                        project_status project_status,
                        image_key VARCHAR,
                        price DOUBLE PRECISION,
                        investor_id VARCHAR(255),
                        technical_solution_id VARCHAR(255),
                        localisation_id VARCHAR(255),
                        investor_need BOOLEAN DEFAULT FALSE,
                        technical_solution_need BOOLEAN DEFAULT FALSE,
                        start_datetime TIMESTAMP,
                        end_datetime TIMESTAMP,
                        CONSTRAINT fk_project_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                        CONSTRAINT fk_project_investor FOREIGN KEY (investor_id) REFERENCES "user"(id),
                        CONSTRAINT fk_project_technical_solution FOREIGN KEY (technical_solution_id) REFERENCES "user"(id),
                        CONSTRAINT fk_project_localisation FOREIGN KEY (localisation_id) REFERENCES location(id)
);

CREATE TABLE project_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction VARCHAR(255),
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 project_id VARCHAR(255),
                                 CONSTRAINT fk_project_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_project_reaction_project FOREIGN KEY (project_id) REFERENCES project(id)
);

create index if not exists project_reaction_user_id_index on "project_reaction" (user_id);
create index if not exists project_reaction_like_reaction_index on "project_reaction" (like_reaction);
create index if not exists project_reaction_vision_index on "project_reaction" (vision);
create index if not exists project_reaction_stars_number_index on "project_reaction" (stars_number);
create index if not exists project_reaction_comment_index on "project_reaction" (comment);
create index if not exists project_reaction_project_id_index on "project_reaction" (project_id);
;
