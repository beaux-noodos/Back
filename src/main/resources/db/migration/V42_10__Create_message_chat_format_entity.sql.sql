CREATE TABLE message_chat_format_entity (
                                            id VARCHAR(255) PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
                                            user_id VARCHAR(255),
                                            client_message TEXT,
                                            assistant_message TEXT,
                                            creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            CONSTRAINT fk_message_chat_format_entity_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);