CREATE TABLE signalisation (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        user_id VARCHAR(255),
                        CONSTRAINT fk_signalisation_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE signalisation_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction reaction_type,
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 signalisation_id VARCHAR(255),
                                 CONSTRAINT fk_signalisation_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_signalisation_reaction_signalisation FOREIGN KEY (signalisation_id) REFERENCES signalisation(id)
);

create index if not exists signalisation_reaction_user_id_index on "signalisation_reaction" (user_id);
create index if not exists signalisation_reaction_like_reaction_index on "signalisation_reaction" (like_reaction);
create index if not exists signalisation_reaction_vision_index on "signalisation_reaction" (vision);
create index if not exists signalisation_reaction_stars_number_index on "signalisation_reaction" (stars_number);
create index if not exists signalisation_reaction_comment_index on "signalisation_reaction" (comment);
create index if not exists signalisation_reaction_signalisation_id_index on "signalisation_reaction" (signalisation_id);
