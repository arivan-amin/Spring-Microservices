CREATE
DATABASE IF NOT EXISTS catalog_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE
USER IF NOT EXISTS 'catalog_user'@'%' IDENTIFIED BY 'catalog@@pass'; GRANT SELECT, INSERT,
UPDATE,
DELETE
ON catalog_db.* TO 'catalog_user'@'%';

-- Liquibase migration user
CREATE
USER IF NOT EXISTS 'catalog_migration'@'%' IDENTIFIED BY 'catalog@@cmy';
GRANT ALL PRIVILEGES ON catalog_db.* TO
'catalog_migration'@'%';

FLUSH
PRIVILEGES;
