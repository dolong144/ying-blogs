# --- !Ups

CREATE TABLE IF NOT EXISTS `user` (
                        `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        `username`  VARCHAR(50) NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        `email` VARCHAR(255) NOT NULL,
                        `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS `post` (
                      `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      `author` VARCHAR(50) NOT NULL ,
                      `title` VARCHAR(150) NOT NULL,
                      `content` TEXT NOT NULL,
                      `thumbnail` VARCHAR(255) NOT NULL,
                      `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      `modify_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
 );
# --- !Downs
drop table `post`;
drop table `user`;