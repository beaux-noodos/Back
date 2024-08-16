CREATE TABLE chat (
                      id VARCHAR(255) PRIMARY KEY,
                      creation_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      user_id VARCHAR(255) UNIQUE NOT NULL,
                      CONSTRAINT fk_chat_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE prompt (
                        id VARCHAR(255) PRIMARY KEY,
                        creation_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        prompt_category VARCHAR(255) NOT NULL,
                        chat_id VARCHAR(255) NOT NULL ,
                        body TEXT NOT NULL,
                        CONSTRAINT fk_chat
                            FOREIGN KEY (chat_id)
                                  REFERENCES "chat" (id)
);

CREATE INDEX idx_prompt_creation_datetime ON prompt (creation_datetime);
CREATE INDEX idx_prompt_updated_at ON prompt (updated_at);
CREATE INDEX idx_prompt_category ON prompt (prompt_category);
