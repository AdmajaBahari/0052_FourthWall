const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('../models/User');

function buatAccessToken(user) {
  return jwt.sign(
    { id: user.id, role: user.role },
    process.env.JWT_ACCESS_SECRET,
    { expiresIn: process.env.JWT_ACCESS_EXPIRES || '15m' }
  );
}

function buatRefreshToken(user) {
  return jwt.sign(
    { id: user.id },
    process.env.JWT_REFRESH_SECRET,
    { expiresIn: process.env.JWT_REFRESH_EXPIRES || '7d' }
  );
}

async function register(req, res) {
  try {
    const { nama, email, password } = req.body;

    if (!nama || !email || !password) {
      return res.status(400).json({ message: 'Nama, email, dan password wajib diisi' });
    }
    if (password.length < 6) {
      return res.status(400).json({ message: 'Password minimal 6 karakter' });
    }

    const sudahAda = await User.findOne({ where: { email } });
    if (sudahAda) {
      return res.status(409).json({ message: 'Email sudah terdaftar' });
    }

    const password_hash = await bcrypt.hash(password, 10);
    const user = await User.create({ nama, email, password_hash });

    return res.status(201).json({
      id: user.id,
      nama: user.nama,
      email: user.email,
      role: user.role,
    });
  } catch (err) {
    return res.status(500).json({ message: 'Terjadi kesalahan server' });
  }
}

async function login(req, res) {
  try {
    const { email, password } = req.body;

    if (!email || !password) {
      return res.status(400).json({ message: 'Email dan password wajib diisi' });
    }

    const user = await User.findOne({ where: { email } });
    if (!user) {
      return res.status(401).json({ message: 'Email atau password salah' });
    }

    const cocok = await bcrypt.compare(password, user.password_hash);
    if (!cocok) {
      return res.status(401).json({ message: 'Email atau password salah' });
    }

    return res.json({
      accessToken: buatAccessToken(user),
      refreshToken: buatRefreshToken(user),
      user: {
        id: user.id,
        nama: user.nama,
        email: user.email,
        role: user.role,
      },
    });
  } catch (err) {
    return res.status(500).json({ message: 'Terjadi kesalahan server' });
  }
}

async function refresh(req, res) {
  try {
    const { refreshToken } = req.body;

    if (!refreshToken) {
      return res.status(400).json({ message: 'Refresh token wajib diisi' });
    }

    let decoded;
    try {
      decoded = jwt.verify(refreshToken, process.env.JWT_REFRESH_SECRET);
    } catch (err) {
      return res.status(401).json({ message: 'Refresh token tidak valid atau kedaluwarsa' });
    }

    const user = await User.findByPk(decoded.id);
    if (!user) {
      return res.status(401).json({ message: 'User tidak ditemukan' });
    }

    return res.json({
      accessToken: buatAccessToken(user),
    });
  } catch (err) {
    return res.status(500).json({ message: 'Terjadi kesalahan server' });
  }
}

async function profile(req, res) {
  try {
    const user = await User.findByPk(req.user.id);
    if (!user) {
      return res.status(404).json({ message: 'User tidak ditemukan' });
    }

    return res.json({
      id: user.id,
      nama: user.nama,
      email: user.email,
      role: user.role,
    });
  } catch (err) {
    return res.status(500).json({ message: 'Terjadi kesalahan server' });
  }
}

module.exports = { register, login, refresh, profile };