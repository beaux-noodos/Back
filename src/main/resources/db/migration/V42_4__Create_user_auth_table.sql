CREATE TABLE "user_auth" (
                          id VARCHAR PRIMARY KEY,
                          user_id VARCHAR NOT NULL,
                          password VARCHAR NOT NULL,
                          CONSTRAINT fk_user
                              FOREIGN KEY (user_id)
                                  REFERENCES "user" (id)
);
