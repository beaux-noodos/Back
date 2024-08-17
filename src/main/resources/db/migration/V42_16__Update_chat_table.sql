ALTER TABLE message_chat_format_entity ADD COLUMN IF NOT EXISTS chat_id varchar(255);
ALTER TABLE message_chat_format_entity ADD CONSTRAINT fk_chat_message FOREIGN KEY (chat_id) REFERENCES chat(id);