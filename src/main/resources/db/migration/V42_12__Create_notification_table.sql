CREATE TABLE notification (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        user_id VARCHAR(255),
                        CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE notification_reaction (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 user_id VARCHAR(255),
                                 creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 like_reaction reaction_type,
                                 vision BOOLEAN,
                                 stars_number INT,
                                 comment TEXT,
                                 notification_id VARCHAR(255),
                                 CONSTRAINT fk_notification_reaction_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                                 CONSTRAINT fk_notification_reaction_notification FOREIGN KEY (notification_id) REFERENCES notification(id)
);

create index if not exists notification_reaction_user_id_index on "notification_reaction" (user_id);
create index if not exists notification_reaction_like_reaction_index on "notification_reaction" (like_reaction);
create index if not exists notification_reaction_vision_index on "notification_reaction" (vision);
create index if not exists notification_reaction_stars_number_index on "notification_reaction" (stars_number);
create index if not exists notification_reaction_comment_index on "notification_reaction" (comment);
create index if not exists notification_reaction_notification_id_index on "notification_reaction" (notification_id);
