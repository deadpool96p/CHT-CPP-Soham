# Collaborative Habit Tracker - Environment Setup

## System Requirements
- **Java**: Version 21
- **Maven**: Version 3.6+
- **MySQL**: Version 8.0+ (via XAMPP)
- **Ollama**: Latest version (for AI features)
- **Node.js**: Not required (frontend is server-side rendered)

## Database Setup
1. Install XAMPP
2. Start Apache and MySQL services
3. Create database: `habit_tracker`
4. Application will auto-create tables via Hibernate

## AI Setup (Optional but Recommended)
1. Install Ollama from https://ollama.ai
2. Run: `ollama pull mistral`
3. Run: `ollama serve`

## Build & Run
```bash
mvn clean compile
mvn spring-boot:run