HackProof — Cybersecurity Awareness Chatbot

> A Java-based GUI cybersecurity chatbot built with Swing, featuring NLP simulation, a task manager, a quiz game, and an activity log — all connected to a MySQL database.

Author
- **Name:** Asanda Ndlovu
- **Project:** Cybersecurity Awareness Chatbot — Part 3
- **IDE:** Apache NetBeans 25
- **Language:** Java (Swing GUI)
- **Database:** MySQL 8.0

Features

 1. Chat with NLP Simulation
- Chat with HackProof about cybersecurity topics
- Recognises keywords like `phishing`, `password`, `VPN`, `malware`, `2FA`, `ransomware`, `firewall`, `social engineering`
- Understands different phrasings e.g. "add task", "new task", "create task" all trigger the same response
- Type `help` to see all available commands
- Type `show activity log` or `what have you done` to view recent actions

 2. Task Assistant
- Add cybersecurity tasks with a title, description and optional reminder date
- All tasks are stored in a MySQL database
- View all tasks with their current status (Pending / Done)
- Mark tasks as completed
- Delete tasks
- All changes reflect instantly in the database

 3. Cybersecurity Quiz
- 12 cybersecurity questions covering:
  - Phishing
  - Password safety
  - Two-Factor Authentication (2FA)
  - VPNs
  - Malware & Ransomware
  - Social Engineering
  - Safe browsing
- Mix of multiple choice and true/false questions
- Questions appear one at a time
- Immediate feedback with explanations after each answer
- Final score and performance feedback at the end
- Option to replay the quiz

 4. Activity Log
- Automatically records all chatbot actions with timestamps
- Logs tasks added, completed and deleted
- Logs quiz started and completed with score
- Logs NLP interactions and recognised commands
- Displays the last 10 actions
- Accessible via the Activity Log tab or by typing `show activity log` in chat
- Clear log button to reset history

 Setup Instructions

 Prerequisites
- Java JDK 17 or higher
- Apache NetBeans IDE
- MySQL Server 8.0
- MySQL Connector/J (JDBC Driver)

 Step 1 — Set Up the Database
Open MySQL and run the following commands:

```sql
CREATE DATABASE cybersecurity_chatbot;
USE cybersecurity_chatbot;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    reminder_date DATE,
    is_completed BOOLEAN DEFAULT FALSE
);
```

Step 2 — Configure the Database Password
In `HackProofMain.java`, find line 23 and update your MySQL password:

```java
private static final String DB_PASS = "your_password_here";
```

Step 3 — Add the JDBC Driver
1. Download MySQL Connector/J from https://dev.mysql.com/downloads/connector/j/
2. In NetBeans, right-click **Libraries** under the HackProof project
3. Click **Add JAR/Folder** and select the downloaded `.jar` file

 Step 4 — Run the Project
1. Open the project in NetBeans
2. Right-click the project → **Set as Main Project**
3. Click **Run → Run Project** or press **F6**

Project Structure

```
HackProof/
├── src/
│   └── hackproof/
│       └── HackProofMain.java   ← Main application file
├── Libraries/
│   └── mysql-connector-j.jar    ← MySQL JDBC Driver
└── README.md
```
Example Chat Commands

| What you type | What happens |
|---|---|
| `hello` | HackProof greets you |
| `what is phishing` | Explains phishing attacks |
| `what is a VPN` | Explains VPNs |
| `how do I stay safe online` | Lists safety tips |
| `add task` | Directs you to the Tasks tab |
| `remind me` | Directs you to set a reminder |
| `quiz` | Directs you to the Quiz tab |
| `show activity log` | Shows recent actions |
| `help` | Lists all features |

 UI Design
- Dark cyber-themed interface with cyan accents
- Tabbed layout for easy navigation
- Colour coded responses (green for chat, cyan for quiz, yellow for log)
- Responsive buttons and scrollable text areas

 Notes
- Make sure MySQL is running before launching the app
- Reminder dates must be in `YYYY-MM-DD` format e.g. `2026-07-29`
- The activity log only shows the last 10 actions to keep it concise



*HackProof — Stay safe. Stay smart. Stay secure.* 🔐
