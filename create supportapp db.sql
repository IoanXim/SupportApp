-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema supportdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema supportdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `supportdb` DEFAULT CHARACTER SET utf8mb3 ;
USE `supportdb` ;

-- -----------------------------------------------------
-- Table `supportdb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`users` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`agents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`agents` (
  `ag_id` BIGINT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(255) NULL DEFAULT NULL,
  `is_available` BIT(1) NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `ssn` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`ag_id`),
  UNIQUE INDEX `UK_ica2nnf6jymrt09bvuv7e4mai` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK2vh8rg4inh3scgcguimya35my`
    FOREIGN KEY (`user_id`)
    REFERENCES `supportdb`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`customers` (
  `cust_id` BIGINT NOT NULL AUTO_INCREMENT,
  `birthday` DATE NULL DEFAULT NULL,
  `firstname` VARCHAR(255) NULL DEFAULT NULL,
  `gender` CHAR(1) NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`cust_id`),
  UNIQUE INDEX `UK_euat1oase6eqv195jvb71a93s` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKrh1g1a20omjmn6kurd35o3eit`
    FOREIGN KEY (`user_id`)
    REFERENCES `supportdb`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`products` (
  `prod_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`prod_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`sessions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`sessions` (
  `session_id` BIGINT NOT NULL AUTO_INCREMENT,
  `ended_at` DATETIME(6) NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `ag_id` BIGINT NULL DEFAULT NULL,
  `cust_id` BIGINT NULL DEFAULT NULL,
  `prod_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  INDEX `FKqsiqloxkon1v8kfk4kbcv74ok` (`ag_id` ASC) VISIBLE,
  INDEX `FKfbb1jwh5w36cqwvra2rfkk0ig` (`cust_id` ASC) VISIBLE,
  INDEX `FK3qgfwqoifhhljlii1umfxm195` (`prod_id` ASC) VISIBLE,
  CONSTRAINT `FK3qgfwqoifhhljlii1umfxm195`
    FOREIGN KEY (`prod_id`)
    REFERENCES `supportdb`.`products` (`prod_id`),
  CONSTRAINT `FKfbb1jwh5w36cqwvra2rfkk0ig`
    FOREIGN KEY (`cust_id`)
    REFERENCES `supportdb`.`customers` (`cust_id`),
  CONSTRAINT `FKqsiqloxkon1v8kfk4kbcv74ok`
    FOREIGN KEY (`ag_id`)
    REFERENCES `supportdb`.`agents` (`ag_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`messages` (
  `msg_id` BIGINT NOT NULL AUTO_INCREMENT,
  `message_text` VARCHAR(255) NULL DEFAULT NULL,
  `sent_at` DATETIME(6) NULL DEFAULT NULL,
  `recipient_id` BIGINT NULL DEFAULT NULL,
  `sender_id` BIGINT NULL DEFAULT NULL,
  `session_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`msg_id`),
  INDEX `FKhdkwfnspwb3s60j27vpg0rpg6` (`recipient_id` ASC) VISIBLE,
  INDEX `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id` ASC) VISIBLE,
  INDEX `FKtkbtam456hs6b6y3d81c08rpx` (`session_id` ASC) VISIBLE,
  CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m`
    FOREIGN KEY (`sender_id`)
    REFERENCES `supportdb`.`users` (`user_id`),
  CONSTRAINT `FKhdkwfnspwb3s60j27vpg0rpg6`
    FOREIGN KEY (`recipient_id`)
    REFERENCES `supportdb`.`users` (`user_id`),
  CONSTRAINT `FKtkbtam456hs6b6y3d81c08rpx`
    FOREIGN KEY (`session_id`)
    REFERENCES `supportdb`.`sessions` (`session_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`products_agents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`products_agents` (
  `ag_id` BIGINT NOT NULL,
  `prod_id` BIGINT NOT NULL,
  PRIMARY KEY (`ag_id`, `prod_id`),
  INDEX `FKh6ey0htvr7748dfflijd5g6te` (`prod_id` ASC) VISIBLE,
  CONSTRAINT `FKh6ey0htvr7748dfflijd5g6te`
    FOREIGN KEY (`prod_id`)
    REFERENCES `supportdb`.`products` (`prod_id`),
  CONSTRAINT `FKnndtgepdhbrxja5o8e91d2hnd`
    FOREIGN KEY (`ag_id`)
    REFERENCES `supportdb`.`agents` (`ag_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `supportdb`.`queue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supportdb`.`queue` (
  `queue_id` BIGINT NOT NULL AUTO_INCREMENT,
  `queued_at` DATETIME(6) NULL DEFAULT NULL,
  `cust_id` BIGINT NOT NULL,
  `prod_id` BIGINT NOT NULL,
  PRIMARY KEY (`queue_id`),
  INDEX `FK2v5vkeeiwk4j79sf3debm8d59` (`cust_id` ASC) VISIBLE,
  INDEX `FKd169qspqdciolqadj52ma5k70` (`prod_id` ASC) VISIBLE,
  CONSTRAINT `FK2v5vkeeiwk4j79sf3debm8d59`
    FOREIGN KEY (`cust_id`)
    REFERENCES `supportdb`.`customers` (`cust_id`),
  CONSTRAINT `FKd169qspqdciolqadj52ma5k70`
    FOREIGN KEY (`prod_id`)
    REFERENCES `supportdb`.`products` (`prod_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
