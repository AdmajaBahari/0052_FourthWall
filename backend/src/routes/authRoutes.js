const express = require('express');
const { register, login, refresh, profile } = require('../controllers/authController');
const { verifyToken } = require('../middleware/auth');

const router = express.Router();

router.post('/register', register);
router.post('/login', login);
router.post('/refresh', refresh);
router.get('/profile', verifyToken, profile);

module.exports = router;
