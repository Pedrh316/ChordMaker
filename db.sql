-- Database: testdb

DROP DATABASE IF EXISTS testdb;

CREATE DATABASE testdb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Tabela unificada de usuários e artistas
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,

    is_artista BOOLEAN NOT NULL DEFAULT FALSE,

    -- nulo se não for artista
    biografia TEXT,
    genero TEXT
);

-- Tabela de álbuns
CREATE TABLE album (
    id SERIAL PRIMARY KEY,
    titulo TEXT NOT NULL,
    data_lancamento DATE,
    arquivo_capa TEXT,
    descricao TEXT,
    artista_id INT NOT NULL,
    FOREIGN KEY (artista_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de músicas
CREATE TABLE musica (
    id SERIAL PRIMARY KEY,
    titulo TEXT NOT NULL,
    duracao INT,
    genero TEXT,
    data_lancamento DATE,
    faixa BYTEA,
    album_id INT NOT NULL,
    artista_id INT NOT NULL,
    FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE,
    FOREIGN KEY (artista_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de playlists
CREATE TABLE playlist (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela associativa entre playlists e músicas
CREATE TABLE playlist_musica (
    playlist_id INT NOT NULL,
    musica_id INT NOT NULL,
    PRIMARY KEY (playlist_id, musica_id),
    FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
    FOREIGN KEY (musica_id) REFERENCES musica(id) ON DELETE CASCADE
);
