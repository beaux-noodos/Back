DO
$$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_type WHERE typname = 'reaction_type') THEN
CREATE TYPE reaction_type AS ENUM ('LIKE', 'DISLIKE');
END IF;
END
$$;

-- Modifier le type de la colonne like_reaction
ALTER TABLE location_reaction ALTER COLUMN like_reaction TYPE reaction_type USING like_reaction::reaction_type;
ALTER TABLE project_session_reaction ALTER COLUMN like_reaction TYPE reaction_type USING like_reaction::reaction_type;
ALTER TABLE project_reaction ALTER COLUMN like_reaction TYPE reaction_type USING like_reaction::reaction_type;
