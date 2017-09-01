-- MySQL Script generated by MySQL Workbench
-- Fri Sep  1 11:13:32 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema xintern
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema xintern
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `xintern` DEFAULT CHARACTER SET utf8 ;
USE `xintern` ;

-- -----------------------------------------------------
-- Table `xintern`.`xi_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xintern`.`xi_account` (
  `a_id` BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `a_role` SMALLINT(3) UNSIGNED NULL,
  `a_phone` VARCHAR(15) NOT NULL,
  `a_wechat` VARCHAR(255) NULL,
  `a_password` VARCHAR(255) NULL,
  PRIMARY KEY (`a_id`),
  UNIQUE INDEX `account_wechat` (`a_wechat` ASC),
  UNIQUE INDEX `account_phone` (`a_phone` ASC, `a_role` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `xintern`.`xi_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xintern`.`xi_student` (
  `s_id` BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `s_accountid` BIGINT(64) UNSIGNED NOT NULL,
  `s_name` VARCHAR(45) NULL,
  `s_phone` VARCHAR(15) NULL,
  `s_gender` TINYINT(1) NULL,
  `s_education` INT(3) UNSIGNED NULL COMMENT '0：大专\n1：本科\n2：硕士\n3：博士',
  `s_major` VARCHAR(45) NULL,
  `s_area` VARCHAR(45) NULL,
  `s_email` VARCHAR(45) NULL,
  `s_school` VARCHAR(45) NULL,
  `s_language` VARCHAR(45) NULL,
  `s_langlevel` INT(3) UNSIGNED NULL COMMENT '0: 一般\n1：良好\n2：熟练\n3：精通',
  `s_grade` INT(3) UNSIGNED NULL COMMENT '0: 一年级\n1：二年级\n…',
  `s_stucard` VARCHAR(255) NULL,
  `s_certs` VARCHAR(2000) NULL,
  `s_avatar` VARCHAR(255) NULL,
  PRIMARY KEY (`s_id`, `s_accountid`),
  INDEX `fk_student_account` (`s_accountid` ASC),
  CONSTRAINT `fk_xi_student_xi_account1`
    FOREIGN KEY (`s_accountid`)
    REFERENCES `xintern`.`xi_account` (`a_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `xintern`.`xi_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xintern`.`xi_company` (
  `c_id` BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_accountid` BIGINT(64) UNSIGNED NOT NULL,
  `c_name` VARCHAR(45) NULL,
  `c_addr` VARCHAR(255) NULL,
  `c_type` VARCHAR(45) NULL,
  `c_scale` VARCHAR(45) NULL,
  `c_contact` VARCHAR(45) NULL,
  `c_contactphone` VARCHAR(45) NULL,
  `c_email` VARCHAR(45) NULL,
  `c_phone` VARCHAR(45) NULL,
  `c_industry` VARCHAR(45) NULL,
  `c_code` VARCHAR(45) NULL,
  `c_intro` TEXT(1000) NULL,
  PRIMARY KEY (`c_id`, `c_accountid`),
  INDEX `fk_company_account` (`c_accountid` ASC),
  CONSTRAINT `fk_xi_company_xi_account1`
    FOREIGN KEY (`c_accountid`)
    REFERENCES `xintern`.`xi_account` (`a_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `xintern`.`xi_position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xintern`.`xi_position` (
  `p_id` BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_companyid` BIGINT(64) UNSIGNED NOT NULL,
  `p_title` VARCHAR(45) NULL,
  `p_area` VARCHAR(45) NULL,
  `p_addr` VARCHAR(255) NULL,
  `p_startdate` DATE NULL,
  `p_enddate` DATE NULL,
  `p_starttime` TIME NULL,
  `p_endtime` TIME NULL,
  `p_salary` INT UNSIGNED NULL COMMENT 'salary paid by company',
  `p_stusalary` INT UNSIGNED NULL COMMENT '扣除手续费后的工资',
  `p_unit` VARCHAR(45) NULL COMMENT 'salary unit',
  `p_mindays` INT UNSIGNED NULL COMMENT '每周最少工作天数',
  `p_continuous` TINYINT(1) NULL COMMENT '是否要求连续工作',
  `p_count` INT UNSIGNED NULL,
  `p_retention` TINYINT(1) NULL COMMENT '是否可留用',
  `p_intro` TEXT(1000) NULL,
  `p_reqgender` INT(1) UNSIGNED NULL COMMENT 'NULL: 不限\n0: male\n1: female',
  `p_reqedu` INT(3) UNSIGNED NULL,
  `p_reqgrade` INT(3) UNSIGNED NULL,
  `p_reqmajor` VARCHAR(45) NULL,
  `p_reqlang` VARCHAR(45) NULL,
  `p_reqlanglevel` INT(3) UNSIGNED NULL COMMENT '0: 一般\n1：良好\n2：熟练\n3：精通',
  PRIMARY KEY (`p_id`, `p_companyid`),
  INDEX `refer_company_idx` (`p_companyid` ASC),
  CONSTRAINT `fk_position_belongs_to_company`
    FOREIGN KEY (`p_companyid`)
    REFERENCES `xintern`.`xi_company` (`c_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `xintern`.`xi_resume`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xintern`.`xi_resume` (
  `r_id` BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `r_positionid` BIGINT(64) UNSIGNED NOT NULL,
  `r_stuid` BIGINT(64) UNSIGNED NOT NULL,
  `r_state` INT(4) UNSIGNED NULL,
  `r_commentstu` VARCHAR(255) NULL,
  `r_commentcomp` VARCHAR(255) NULL,
  PRIMARY KEY (`r_id`, `r_positionid`, `r_stuid`),
  INDEX `fk_resume_stu` (`r_stuid` ASC),
  INDEX `fk_resume_position` (`r_positionid` ASC),
  CONSTRAINT `fk_resume_positionid`
    FOREIGN KEY (`r_positionid`)
    REFERENCES `xintern`.`xi_position` (`p_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_stuid`
    FOREIGN KEY (`r_stuid`)
    REFERENCES `xintern`.`xi_student` (`s_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE USER 'xintern' IDENTIFIED BY 'ddcreative';

GRANT ALL ON `xintern`.* TO 'xintern';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;