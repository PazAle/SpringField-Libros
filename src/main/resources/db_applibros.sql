-- MySQL Script generated by MySQL Workbench
-- Wed Oct  4 23:44:51 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_applibros
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_applibros
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_applibros` DEFAULT CHARACTER SET utf8 ;
USE `db_applibros` ;

-- -----------------------------------------------------
-- Table `db_applibros`.`Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Usuario` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Usuario` (
  `id` BIGINT(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `rol` VARCHAR(20) NOT NULL,
  `activo` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_applibros`.`Compra`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Compra` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Compra` (
  `id` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `libros` VARCHAR(255) NOT NULL,
  `precio_total` DOUBLE NOT NULL,
  `Usuario_id` BIGINT(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Compra_Usuario_idx` (`Usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Compra_Usuario`
    FOREIGN KEY (`Usuario_id`)
    REFERENCES `db_applibros`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_applibros`.`Categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Categoria` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Categoria` (
  `id` INT NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_applibros`.`Autor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Autor` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Autor` (
  `id` INT NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `fecha_nacimiento` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_applibros`.`Libro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Libro` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Libro` (
  `id` BIGINT(100) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `precio` DOUBLE NOT NULL,
  `fecha_lanzamiento` DATE NULL,
  `idioma` VARCHAR(100) NOT NULL,
  `cantidad_paginas` INT NULL,
  `imagen` VARCHAR(255) NULL,
  `resenia` VARCHAR(255) NULL,
  `Compra_id` INT NOT NULL,
  `Categoria_id` INT NOT NULL,
  `Autor_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Libro_Compra1_idx` (`Compra_id` ASC) VISIBLE,
  INDEX `fk_Libro_Categoria1_idx` (`Categoria_id` ASC) VISIBLE,
  INDEX `fk_Libro_Autor1_idx` (`Autor_id` ASC) VISIBLE,
  CONSTRAINT `fk_Libro_Compra1`
    FOREIGN KEY (`Compra_id`)
    REFERENCES `db_applibros`.`Compra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Libro_Categoria1`
    FOREIGN KEY (`Categoria_id`)
    REFERENCES `db_applibros`.`Categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Libro_Autor1`
    FOREIGN KEY (`Autor_id`)
    REFERENCES `db_applibros`.`Autor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_applibros`.`Comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_applibros`.`Comentario` ;

CREATE TABLE IF NOT EXISTS `db_applibros`.`Comentario` (
  `id` INT NOT NULL,
  `texto` VARCHAR(255) NOT NULL,
  `fecha` DATE NOT NULL,
  `Libro_id` BIGINT(100) NOT NULL,
  `Usuario_id` BIGINT(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Comentario_Libro1_idx` (`Libro_id` ASC) VISIBLE,
  INDEX `fk_Comentario_Usuario1_idx` (`Usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Comentario_Libro1`
    FOREIGN KEY (`Libro_id`)
    REFERENCES `db_applibros`.`Libro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comentario_Usuario1`
    FOREIGN KEY (`Usuario_id`)
    REFERENCES `db_applibros`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
