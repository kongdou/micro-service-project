CREATE SCHEMA `db_user` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `db_user`.`pe_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `realname` VARCHAR(45) NULL,
  `mobile` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `db_user`.`pe_teacher` (
  `user_id` INT NOT NULL,
  `intro` VARCHAR(256) NULL,
  `stars` INT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

