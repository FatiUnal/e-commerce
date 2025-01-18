CREATE OR REPLACE FUNCTION delete_expired_otps()
    RETURNS TRIGGER AS $$
BEGIN
DELETE FROM otps
WHERE expired < NOW() OR is_valid = false;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_delete_expired_otps
    AFTER INSERT OR UPDATE ON otps
                        FOR EACH STATEMENT
                        EXECUTE FUNCTION delete_expired_otps();
