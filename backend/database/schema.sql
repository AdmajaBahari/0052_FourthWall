CREATE DATABASE IF NOT EXISTS fourthwall
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE fourthwall;

CREATE TABLE IF NOT EXISTS users (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
    PRIMARY KEY (id),
    UNIQUE KEY uq_users_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS genres (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_genres_nama (nama)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS manhwa (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    genre_id INT UNSIGNED NOT NULL,
    judul VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    sinopsis TEXT NOT NULL,
    cover_path VARCHAR(500) NULL,
    PRIMARY KEY (id),
    KEY idx_manhwa_genre_id (genre_id),
    CONSTRAINT fk_manhwa_genre
        FOREIGN KEY (genre_id) REFERENCES genres (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS koleksi (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    nama VARCHAR(150) NOT NULL,
    deskripsi TEXT NULL,
    PRIMARY KEY (id),
    KEY idx_koleksi_user_id (user_id),
    CONSTRAINT fk_koleksi_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS progres_baca (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    manhwa_id INT UNSIGNED NOT NULL,
    chapter_terakhir INT UNSIGNED NOT NULL DEFAULT 0,
    status ENUM('Reading', 'Completed', 'On Hold', 'Dropped')
        NOT NULL DEFAULT 'Reading',
    PRIMARY KEY (id),
    UNIQUE KEY uq_progres_user_manhwa (user_id, manhwa_id),
    KEY idx_progres_manhwa_id (manhwa_id),
    CONSTRAINT fk_progres_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_progres_manhwa
        FOREIGN KEY (manhwa_id) REFERENCES manhwa (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS catatan (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    manhwa_id INT UNSIGNED NOT NULL,
    judul VARCHAR(200) NOT NULL,
    isi TEXT NOT NULL,
    PRIMARY KEY (id),
    KEY idx_catatan_user_id (user_id),
    KEY idx_catatan_manhwa_id (manhwa_id),
    CONSTRAINT fk_catatan_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_catatan_manhwa
        FOREIGN KEY (manhwa_id) REFERENCES manhwa (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS koleksi_manhwa (
    koleksi_id INT UNSIGNED NOT NULL,
    manhwa_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (koleksi_id, manhwa_id),
    KEY idx_koleksi_manhwa_manhwa_id (manhwa_id),
    CONSTRAINT fk_koleksi_manhwa_koleksi
        FOREIGN KEY (koleksi_id) REFERENCES koleksi (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_koleksi_manhwa_manhwa
        FOREIGN KEY (manhwa_id) REFERENCES manhwa (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;
