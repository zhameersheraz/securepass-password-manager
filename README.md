# SecurePass

A Java CLI password manager demonstrating AES-256 encryption, PBKDF2 key
derivation, and Have I Been Pwned (HIBP) breach detection.

Built as a portfolio piece to practice cryptographic implementations
in Java.

## Features

- **Password generation** — cryptographically secure random passwords
- **Strength analysis** — feedback on length, character variety, and common patterns
- **Encrypted vault** — AES-256-CBC with per-encryption salt and IV
- **Breach detection** — HIBP k-anonymity API to check against known leaks
- **Zero-knowledge** — master password is never stored

## How it works

| Stage               | Algorithm                            |
| ------------------- | ------------------------------------ |
| Key derivation      | PBKDF2-HMAC-SHA256, 65,536 iterations|
| Cipher              | AES-256-CBC                          |
| Salt                | 16 bytes, random per encryption      |
| IV                  | 16 bytes, random per encryption      |
| Random              | `java.security.SecureRandom`         |

The master password is never persisted. Each vault file is independently
encrypted, so a leak of one file does not affect the others.

## How to run

Requires Java 8 or newer.

```bash
javac SecurePassApp.java
java SecurePassApp
```

Follow the on-screen menu to generate, analyze, or store passwords.

## Usage

### Generate a strong password
Pick option `1`, set length and character types, get a secure random password.

### Check a password
Pick option `2`, enter a password, get a strength report and breach check.

### Use the encrypted vault
Pick option `3`, set a master password, then add and retrieve entries.

## Limitations

This is a single-file demonstration project, **not** a production password
manager. Notable gaps:

- No 2FA
- Master password is held in process memory while running
- No password expiry or rotation policy
- No audit logging
- No concurrent-access safety

For real password management, use [1Password](https://1password.com),
[Bitwarden](https://bitwarden.com), or [KeePass](https://keepass.info).

## Security note

The HIBP integration uses the [k-anonymity API](https://haveibeenpwned.com/API/v3#PwnedPasswords),
so the full password is never sent — only the first 5 characters of its
SHA-1 hash. The vault stores ciphertext + IV + salt, never the master
password.

## Author

Zhameer Sheraz Tampugao — [github.com/zhameersheraz](https://github.com/zhameersheraz)

## License

MIT — see [LICENSE](LICENSE).
