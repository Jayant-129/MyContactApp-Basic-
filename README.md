# MyContacts App

## Use Cases

### UC1 — User Registration
- **Classes**: `User` (abstract), `FreeUser`, `PremiumUser`, `UserRole` (enum), `PasswordHasher`, `InputValidator`, `UserRepository`
- **OOP**: Encapsulation (private fields + getters/setters), Inheritance (`FreeUser`/`PremiumUser` extend `User`), Abstraction (`getUserType()`)
- **Java**: `UUID`, `MessageDigest` (SHA-256), regex validation, `HashMap` in-memory store
