# ERD FourthWall

6 entitas utama dan 1 tabel penghubung, sesuai `backend/database/schema.sql`.

```mermaid
erDiagram
    USERS {
        int id PK
        varchar nama
        varchar email UK
        varchar password_hash
        enum role "admin | user"
    }
    GENRES {
        int id PK
        varchar nama UK
    }
    MANHWA {
        int id PK
        int genre_id FK
        varchar judul
        varchar author
        text sinopsis
        varchar cover_path
    }
    PROGRES_BACA {
        int id PK
        int user_id FK
        int manhwa_id FK
        int chapter_terakhir
        enum status "Reading | Completed | On Hold | Dropped"
    }
    CATATAN {
        int id PK
        int user_id FK
        int manhwa_id FK
        varchar judul
        text isi
    }
    KOLEKSI {
        int id PK
        int user_id FK
        varchar nama
        text deskripsi
    }
    KOLEKSI_MANHWA {
        int koleksi_id PK, FK
        int manhwa_id PK, FK
    }

    GENRES ||--o{ MANHWA : "memiliki"
    USERS ||--o{ PROGRES_BACA : "memiliki"
    MANHWA ||--o{ PROGRES_BACA : "dilacak dalam"
    USERS ||--o{ CATATAN : "menulis"
    MANHWA ||--o{ CATATAN : "dicatat pada"
    USERS ||--o{ KOLEKSI : "membuat"
    KOLEKSI ||--o{ KOLEKSI_MANHWA : "berisi"
    MANHWA ||--o{ KOLEKSI_MANHWA : "masuk ke"
```

## Aturan Penting

| Aturan | Implementasi |
|---|---|
| Satu manhwa punya satu genre utama | `manhwa.genre_id` wajib diisi |
| Genre yang masih dipakai tidak bisa dihapus | `ON DELETE RESTRICT` |
| Satu user hanya punya satu progres per manhwa | `UNIQUE (user_id, manhwa_id)` |
| Koleksi dan manhwa many-to-many | Tabel `koleksi_manhwa` dengan primary key gabungan |
| Data pribadi punya pemilik | `user_id` pada progres, catatan, dan koleksi |
| Password tidak disimpan polos | `password_hash` (bcrypt) |