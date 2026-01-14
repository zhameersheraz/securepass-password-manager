# SecurePass - Professional Password Security Tool

A production-ready password manager built with Java, demonstrating enterprise-level security practices.

## Features

- **Secure Password Generation** - Cryptographically secure random passwords
- **Strength Analysis** - Detailed feedback on password security
- **Encrypted Vault** - AES-256 encryption with PBKDF2 key derivation
- **Breach Detection** - Checks against 10+ billion compromised passwords
- **Zero-Knowledge Architecture** - Master password never stored

## Security Features

- AES-256-CBC encryption
- PBKDF2 key derivation (65,536 iterations)
- Secure random number generation
- Salt and IV per encryption
- Have I Been Pwned API integration

## How to Run
```bash
javac SecurePassApp.java
java SecurePassApp
```

## Usage

### Generate Strong Password
1. Choose option 1
2. Set length and character types
3. Get cryptographically secure password

### Check Password Security
1. Choose option 2
2. Enter password to analyze
3. Receive detailed security report

### Password Vault
1. Choose option 3
2. Create/enter master password
3. Store unlimited passwords securely

## Security Notice

This is a demonstration project. For production use:
- Add 2FA authentication
- Implement secure key storage
- Add password expiration
- Enable audit logging

## Author

**Zhameer Sheraz Tampugao**
Computer Science Student | Cybersecurity Enthusiast

Built in 2026 as part of my GitHub portfolio demonstrating:
- Cryptographic implementations
- Security best practices
- Clean code architecture
- Real-world problem solving

## License

MIT License - Educational purposes

---

**Note:** Never reuse the master password. Choose a strong, unique master password for the vault.