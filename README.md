# FourthWall

Aplikasi mobile untuk mencatat progres baca dan catatan pribadi manhwa.

Project Akhir mata kuliah Pengembangan Aplikasi Mobile (PAM) 2026
Program Studi Teknologi Informasi, Universitas Muhammadiyah Yogyakarta (UMY)

**Nama:** Admaja Bahari
**Tema:** Hiburan

## Deskripsi

FourthWall membantu pembaca manhwa mencatat sampai chapter berapa mereka membaca, menyimpan catatan pribadi, dan mengelompokkan manhwa ke dalam koleksi. Katalog manhwa dan genre dikelola oleh Admin, sedangkan progres, catatan, dan koleksi dikelola oleh masing-masing User.

## Role

| Role | Hak akses |
|---|---|
| Admin | Mengelola katalog Manhwa dan Genre |
| User | Mengelola Progres Baca, Catatan, dan Koleksi miliknya sendiri |

## Entitas Data

1. User
2. Manhwa
3. Genre
4. Progres Baca (status: Reading, Completed, On Hold, Dropped)
5. Catatan
6. Koleksi (relasi many-to-many dengan Manhwa)

## Tech Stack

- Android: Kotlin, Jetpack Compose, Navigation Compose, arsitektur MVVM
- Backend: REST API (JSON)
- Database: MySQL
- Autentikasi: JWT (access token dan refresh token)
- Penyimpanan file: cover manhwa di storage lokal

## Struktur Repo

    FourthWall-App/
    |-- app/       -> aplikasi Android (Jetpack Compose)
    |-- backend/   -> REST API dan database
    |-- docs/      -> ERD dan dokumen pendukung
    |-- README.md

## Status Fitur

| Fitur | Status | Minggu |
|---|---|---|
| Setup repo dan project Android | Selesai | 1 |
| README awal | Selesai | 1 |
| ERD 6 entitas | Belum | 1 |
| Skema database MySQL | Belum | 1 |
| Backend dan koneksi database | Belum | 1 |
| Register (API) | Belum | 1 |
| Login dengan JWT (API) | Belum | 1 |
| Kerangka MVVM dan Navigation Compose | Belum | 1 |
| UI login/register dengan validasi | Belum | 2 |
| Refresh token dan UI per role | Belum | 2 |
| CRUDS Manhwa dan Genre (Admin) | Belum | 3 |
| Upload dan tampil cover | Belum | 3 |
| CRUDS Progres Baca | Belum | 4 |
| CRUDS Catatan | Belum | 5 |
| CRUDS Koleksi | Belum | 5 |
| Finalisasi, validasi, dan error handling | Belum | 6 |

## Log Mingguan

| Minggu | Pekerjaan | Status |
|---|---|---|
| 1 | Fondasi: repo, ERD, database, backend, register/login, kerangka Android | Berjalan |
| 2 | Auth dan Role | Belum |
| 3 | Katalog | Belum |
| 4 | Progres Baca | Belum |
| 5 | Catatan dan Koleksi | Belum |
| 6 | Finalisasi | Belum |
