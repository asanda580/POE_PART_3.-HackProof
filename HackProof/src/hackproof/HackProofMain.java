/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackproof;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
/**
 *
 * @author Asanda Ndlovu - ST10488663
 */

public class HackProofMain extends JFrame {

  //These are my private credentials
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/cybersecurity_chatbot";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "EhuawaMatka95@"; // This is the password that i set when i installed MySQL to my computer
                                                           //You can change the password to your liking i think

    // the activity log. 
    private final ArrayList<String> activityLog = new ArrayList<>();

    // The data for the Quez 
    private final String[][] questions = {
        {"What should you do if you receive an email asking for your password?",
         "A) Reply with your password", "B) Delete the email",
         "C) Report it as phishing", "D) Ignore it", "C",
         "Reporting phishing emails helps prevent scams and protects others."},
        {"What does 2FA stand for?",
         "A) Two Factor Authentication", "B) Two File Access",
         "C) Two Firewall Application", "D) None of the above", "A",
         "2FA adds an extra layer of security beyond just a password."},
        {"True or False: Using the same password for all accounts is safe.",
         "A) True", "B) False", "C) Sometimes", "D) Only for email", "B",
         "Reusing passwords means one breach exposes all your accounts."},
        {"What is phishing?",
         "A) A type of firewall", "B) A network protocol",
         "C) A scam to steal personal info", "D) An antivirus tool", "C",
         "Phishing tricks users into revealing sensitive information."},
        {"Which is the strongest password?",
         "A) password123", "B) John1990",
         "C) qwerty", "D) @Tz#9mL!2vQ", "D",
         "Strong passwords use a mix of letters, numbers and special characters."},
        {"True or False: Public Wi-Fi is always safe to use for banking.",
         "A) True", "B) False", "C) Only with a VPN", "D) Depends on the bank", "B",
         "Public Wi-Fi can be intercepted; always use a VPN for sensitive tasks."},
        {"What does a VPN do?",
         "A) Speeds up your internet", "B) Encrypts your internet connection",
         "C) Blocks all ads", "D) Removes viruses", "B",
         "A VPN encrypts traffic and hides your IP address."},
        {"What is social engineering?",
         "A) Building social media apps", "B) Manipulating people to reveal info",
         "C) Engineering software for social platforms", "D) None of the above", "B",
         "Social engineering exploits human psychology rather than technical flaws."},
        {"True or False: Antivirus software alone is enough to stay secure.",
         "A) True", "B) False", "C) Only on Windows", "D) Only on mobile", "B",
         "Good security requires multiple layers including habits and updates."},
        {"How often should you update your passwords?",
         "A) Never", "B) Every 5 years",
         "C) Every 3–6 months", "D) Only when hacked", "C",
         "Regular password updates reduce the risk of long-term unauthorized access."},
        {"What is ransomware?",
         "A) Free security software", "B) Malware that locks files for payment",
         "C) A type of VPN", "D) An email filter", "B",
         "Ransomware encrypts your files and demands payment for the key."},
        {"True or False: Clicking unknown links in emails is generally safe.",
         "A) True", "B) False", "C) Only from friends", "D) Only in plain text emails", "B",
         "Unknown links can lead to malware or phishing sites."}
    };

    private int quizIndex   = 0;
    private int quizScore   = 0;
    private boolean quizActive = false;

    // My GUI comp"
    //ST10488663
    private JTextArea  chatArea;
    private JTextField chatInput;
    private JTextArea  quizArea;
    private JPanel     quizButtonPanel;
    private JTextArea  taskArea;
    private JTextField taskTitleField, taskDescField, taskReminderField;
    private JTextArea  logArea;

    
    public HackProofMain() {
        setTitle("HackProof – Cybersecurity Chatbot");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(15, 20, 30));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(20, 30, 45));
        tabs.setForeground(Color.CYAN);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab(" Chat",      buildChatPanel());
        tabs.addTab(" Tasks",     buildTaskPanel());
        tabs.addTab(" Quiz",      buildQuizPanel());
        tabs.addTab(" Activity Log", buildLogPanel());

        add(tabs);
        setVisible(true);
        appendChat("HackProof", "Hello! I'm HackProof, your cybersecurity assistant. "
                + "Type 'help' to see what I can do for you!");
        logAction("HackProof started.");
    }

    
    //  CHAT PANEL....

    private JPanel buildChatPanel() {
        JPanel p = darkPanel(new BorderLayout(5, 5));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(10, 15, 25));
        chatArea.setForeground(new Color(180, 255, 180));
        chatArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));

        chatInput = new JTextField();
        styleTextField(chatInput);
        chatInput.addActionListener(e -> handleChatInput());

        JButton sendBtn = cyberButton("Send");
        sendBtn.addActionListener(e -> handleChatInput());

        JPanel bottom = darkPanel(new BorderLayout(5, 0));
        bottom.add(chatInput, BorderLayout.CENTER);
        bottom.add(sendBtn,   BorderLayout.EAST);

        p.add(scroll, BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private void handleChatInput() {
        String input = chatInput.getText().trim();
        if (input.isEmpty()) return;
        chatInput.setText("");
        appendChat("You", input);
        String response = generateResponse(input.toLowerCase());
        appendChat("HackProof", response);
        logAction("Chat: user said \"" + input + "\"");
    }
    
    private void appendChat(String sender, String msg) {
        chatArea.append("[" + sender + "]: " + msg + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // The key word search engine...
    private String generateResponse(String in) {
        // Activity log request
        if (in.contains("activity log") || in.contains("what have you done") || in.contains("show log")) {
            return getActivityLogSummary();
        }
        // help reply...
        if (in.contains("help") || in.contains("what can you do")) {
            return "Here's what I can do:\n"
                 + "• Chat about cybersecurity topics\n"
                 + "• Add/view/delete tasks (go to Tasks tab)\n"
                 + "• Run a cybersecurity quiz (go to Quiz tab)\n"
                 + "• Show activity log\n\n"
                 + "Try asking: 'what is phishing?' or 'how do I stay safe online?'";
        }
        // Task shortcuts via chat
        if (in.contains("add task") || in.contains("new task") || in.contains("create task")) {
            logAction("NLP: user requested to add a task via chat.");
            return "Sure! Head over to the  Tasks tab to add a new task with a title, description and reminder date.";
        }
        if (in.contains("remind me") || in.contains("set reminder") || in.contains("reminder")) {
            logAction("NLP: user requested a reminder via chat.");
            return "Got it! Go to the  Tasks tab, fill in the task details and set a reminder date.";
        }
        if (in.contains("view task") || in.contains("show task") || in.contains("my task")) {
            logAction("NLP: user requested to view tasks via chat.");
            return "Head to the  Tasks tab to see all your tasks!";
        }
        // Quiz shortcuts
        if (in.contains("quiz") || in.contains("test me") || in.contains("game")) {
            logAction("NLP: user requested quiz via chat.");
            return "Head over to the  Quiz tab to start the cybersecurity quiz!";
        }
        // Cybersecurity topics
        if (in.contains("phishing")) {
            return "Phishing is a cyberattack where criminals send fake emails or messages to trick you into "
                 + "revealing passwords or personal info. Always verify the sender before clicking any links!";
        }
        if (in.contains("password")) {
            return "Strong passwords should be at least 12 characters long and include uppercase, lowercase, "
                 + "numbers and special characters. Never reuse passwords across different sites!";
        }
        if (in.contains("2fa") || in.contains("two factor") || in.contains("two-factor")) {
            return "Two-Factor Authentication (2FA) adds an extra security layer. Even if someone steals your "
                 + "password, they still can't access your account without the second factor (like a code sent to your phone).";
        }
        if (in.contains("vpn")) {
            return "A VPN (Virtual Private Network) encrypts your internet connection and hides your IP address. "
                 + "It's especially important when using public Wi-Fi!";
        }
        if (in.contains("malware") || in.contains("virus")) {
            return "Malware is malicious software designed to harm your system. Always keep your antivirus updated, "
                 + "avoid suspicious downloads, and don't click unknown links!";
        }
        if (in.contains("ransomware")) {
            return "Ransomware is malware that encrypts your files and demands payment. "
                 + "Always back up your data and never pay the ransom — it doesn't guarantee recovery!";
        }
        if (in.contains("firewall")) {
            return "A firewall monitors and controls incoming/outgoing network traffic based on security rules. "
                 + "It's your first line of defence against network attacks!";
        }
        if (in.contains("social engineering")) {
            return "Social engineering manipulates people into revealing confidential info. "
                 + "Be cautious of unsolicited calls, emails or messages asking for personal details!";
        }
        if (in.contains("safe") && in.contains("online")) {
            return "To stay safe online:\n"
                 + "• Use strong, unique passwords\n"
                 + "• Enable 2FA on all accounts\n"
                 + "• Keep software updated\n"
                 + "• Avoid public Wi-Fi without a VPN\n"
                 + "• Never click suspicious links!";
        }
        if (in.contains("update") || in.contains("patch")) {
            return "Keeping your software updated is crucial! Updates patch security vulnerabilities that hackers "
                 + "could exploit. Enable automatic updates where possible.";
        }
        if (in.contains("backup")) {
            return "Regular backups protect you from data loss due to ransomware, hardware failure or accidents. "
                 + "Follow the 3-2-1 rule: 3 copies, 2 different media, 1 offsite!";
        }
        if (in.contains("hello") || in.contains("hi") || in.contains("hey")) {
            return "Hey there!  I'm HackProof, your cybersecurity assistant. How can I help you stay safe today?";
        }
        if (in.contains("thank")) {
            return "You're welcome! Stay safe online! ";
        }
        // Fallback
        return "I'm not sure about that. Try asking me about topics like phishing, passwords, VPNs, malware, "
             + "2FA, or type 'help' to see all my features!";
    }

    
    //  TASK PANEL FOR MY TASKS

    private JPanel buildTaskPanel() {
        JPanel p = darkPanel(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        //The Input form 
        JPanel form = darkPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN), "Add New Task",
                TitledBorder.LEFT, TitledBorder.TOP, null, Color.CYAN));

        taskTitleField    = new JTextField(); styleTextField(taskTitleField);
        taskDescField     = new JTextField(); styleTextField(taskDescField);
        taskReminderField = new JTextField(); styleTextField(taskReminderField);
        taskReminderField.setToolTipText("Format: YYYY-MM-DD  e.g. 2025-12-31");

        form.add(cyberLabel("Task Title:"));       form.add(taskTitleField);
        form.add(cyberLabel("Description:"));      form.add(taskDescField);
        form.add(cyberLabel("Reminder (YYYY-MM-DD):")); form.add(taskReminderField);

        JButton addBtn = cyberButton("➕ Add Task");
        addBtn.addActionListener(e -> addTask());
        form.add(new JLabel()); form.add(addBtn);

        // Task display 
        taskArea = new JTextArea();
        taskArea.setEditable(false);
        taskArea.setBackground(new Color(10, 15, 25));
        taskArea.setForeground(new Color(180, 255, 180));
        taskArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(taskArea);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN), "Your Tasks",
                TitledBorder.LEFT, TitledBorder.TOP, null, Color.CYAN));

        // Thn Action buttons 
        JPanel btnRow = darkPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton refreshBtn  = cyberButton("  Refresh");
        JButton completeBtn = cyberButton(" Mark Complete");
        JButton deleteBtn   = cyberButton(" Delete Task");
        JTextField idField  = new JTextField(5); styleTextField(idField);
        idField.setToolTipText("Enter Task ID");

        refreshBtn.addActionListener(e  -> loadTasks());
        completeBtn.addActionListener(e -> markComplete(idField.getText().trim()));
        deleteBtn.addActionListener(e   -> deleteTask(idField.getText().trim()));

        btnRow.add(cyberLabel("Task ID:"));
        btnRow.add(idField);
        btnRow.add(refreshBtn);
        btnRow.add(completeBtn);
        btnRow.add(deleteBtn);

        p.add(form,   BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(btnRow, BorderLayout.SOUTH);

        loadTasks();
        return p;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void addTask() {
        String title    = taskTitleField.getText().trim();
        String desc     = taskDescField.getText().trim();
        String reminder = taskReminderField.getText().trim();
        if (title.isEmpty()) { JOptionPane.showMessageDialog(this, "Task title cannot be empty!"); return; }

        String sql = "INSERT INTO tasks (title, description, reminder_date) VALUES (?, ?, ?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, desc.isEmpty() ? "No description provided." : desc);
            ps.setString(3, reminder.isEmpty() ? null : reminder);
            ps.executeUpdate();
            appendChat("HackProof", "Task added: '" + title + "'." + (reminder.isEmpty() ? "" : " Reminder set for " + reminder + "."));
            logAction("Task added: '" + title + "'" + (reminder.isEmpty() ? "" : " (Reminder: " + reminder + ")"));
            taskTitleField.setText(""); taskDescField.setText(""); taskReminderField.setText("");
            loadTasks();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }

    private void loadTasks() {
        taskArea.setText("");
        String sql = "SELECT * FROM tasks ORDER BY id";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            boolean any = false;
            while (rs.next()) {
                any = true;
                String status   = rs.getBoolean("is_completed") ? " DONE" : " Pending";
                String reminder = rs.getString("reminder_date");
                taskArea.append("ID: " + rs.getInt("id") + "  [" + status + "]\n");
                taskArea.append("  Title      : " + rs.getString("title") + "\n");
                taskArea.append("  Description: " + rs.getString("description") + "\n");
                taskArea.append("  Reminder   : " + (reminder == null ? "None" : reminder) + "\n");
                taskArea.append("─────────────────────────────────────────\n");
            }
            if (!any) taskArea.setText("No tasks yet. Add one above!");
        } catch (SQLException ex) {
            taskArea.setText("Could not load tasks: " + ex.getMessage());
        }
    }

    private void markComplete(String id) {
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a Task ID first!"); return; }
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE tasks SET is_completed=TRUE WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(id));
            int rows = ps.executeUpdate();
            if (rows > 0) { logAction("Task ID " + id + " marked as completed."); loadTasks(); }
            else JOptionPane.showMessageDialog(this, "No task found with ID " + id);
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void deleteTask(String id) {
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a Task ID first!"); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete task ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM tasks WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(id));
            int rows = ps.executeUpdate();
            if (rows > 0) { logAction("Task ID " + id + " deleted."); loadTasks(); }
            else JOptionPane.showMessageDialog(this, "No task found with ID " + id);
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

   
    //  QUIZ PANEL FOR MY QUIZ'S - ST10488663
   
    private JPanel buildQuizPanel() {
        JPanel p = darkPanel(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        quizArea = new JTextArea();
        quizArea.setEditable(false);
        quizArea.setBackground(new Color(10, 15, 25));
        quizArea.setForeground(Color.CYAN);
        quizArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        quizArea.setLineWrap(true);
        quizArea.setWrapStyleWord(true);
        quizArea.setText("Welcome to the HackProof Quiz!\n\nTest your cybersecurity knowledge.\nPress 'Start Quiz' to begin!");
        JScrollPane scroll = new JScrollPane(quizArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));

        quizButtonPanel = darkPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton startBtn = cyberButton(" Start Quiz");
        startBtn.addActionListener(e -> startQuiz());
        quizButtonPanel.add(startBtn);

        p.add(scroll,          BorderLayout.CENTER);
        p.add(quizButtonPanel, BorderLayout.SOUTH);
        return p;
    }

    private void startQuiz() {
        quizIndex  = 0;
        quizScore  = 0;
        quizActive = true;
        logAction("Quiz started.");
        showQuestion();
    }

    private void showQuestion() {
        if (quizIndex >= questions.length) { endQuiz(); return; }
        String[] q = questions[quizIndex];
        quizArea.setText("Question " + (quizIndex + 1) + " of " + questions.length + "\n\n"
                + q[0] + "\n\n"
                + q[1] + "\n" + q[2] + "\n" + q[3] + "\n" + q[4]);

        quizButtonPanel.removeAll();
        String[] opts = {"A", "B", "C", "D"};
        for (String opt : opts) {
            JButton btn = cyberButton(opt);
            btn.addActionListener(e -> checkAnswer(opt));
            quizButtonPanel.add(btn);
        }
        quizButtonPanel.revalidate();
        quizButtonPanel.repaint();
    }

    private void checkAnswer(String chosen) {
        String[] q = questions[quizIndex];
        String correct = q[5];
        String explanation = q[6];
        boolean right = chosen.equals(correct);
        if (right) quizScore++;

        quizArea.setText((right ? " CORRECT!\n\n" : " Wrong! The correct answer was: " + correct + "\n\n")
                + explanation + "\n\n"
                + "Score so far: " + quizScore + "/" + (quizIndex + 1));

        quizButtonPanel.removeAll();
        JButton nextBtn = cyberButton(quizIndex + 1 < questions.length ? "Next ➡" : "Finish ");
        nextBtn.addActionListener(e -> { quizIndex++; showQuestion(); });
        quizButtonPanel.add(nextBtn);
        quizButtonPanel.revalidate();
        quizButtonPanel.repaint();
    }

    private void endQuiz() {
        quizActive = false;
        int total = questions.length;
        String feedback;
        if      (quizScore == total)        feedback = " Perfect score! You're a cybersecurity pro!";
        else if (quizScore >= total * 0.75) feedback = " Great job! You really know your stuff!";
        else if (quizScore >= total * 0.5)  feedback = " Not bad! Keep learning to stay safe online!";
        else                                feedback = " Keep studying — cybersecurity knowledge saves lives!";

        quizArea.setText("Quiz Complete!\n\nYour Score: " + quizScore + "/" + total + "\n\n" + feedback);
        logAction("Quiz completed — Score: " + quizScore + "/" + total);

        quizButtonPanel.removeAll();
        JButton restart = cyberButton("  Play Again");
        restart.addActionListener(e -> startQuiz());
        quizButtonPanel.add(restart);
        quizButtonPanel.revalidate();
        quizButtonPanel.repaint();
    }

   
    //  ACTIVITY LOG PANEL - ST10488663
   
    private JPanel buildLogPanel() {
        JPanel p = darkPanel(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(10, 15, 25));
        logArea.setForeground(new Color(255, 220, 100));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));

        JButton refreshBtn = cyberButton(" Refresh Log");
        refreshBtn.addActionListener(e -> refreshLog());

        JButton clearBtn = cyberButton(" Clear Log");
        clearBtn.addActionListener(e -> { activityLog.clear(); refreshLog(); });

        JPanel btnRow = darkPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        btnRow.add(refreshBtn);
        btnRow.add(clearBtn);

        p.add(scroll, BorderLayout.CENTER);
        p.add(btnRow, BorderLayout.SOUTH);
        refreshLog();
        return p;
    }

    private void logAction(String action) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        activityLog.add("[" + timestamp + "] " + action);
        if (logArea != null) refreshLog();
    }

    private void refreshLog() {
        if (logArea == null) return;
        logArea.setText("─── HackProof Activity Log ───\n\n");
        int start = Math.max(0, activityLog.size() - 10);
        for (int i = start; i < activityLog.size(); i++) {
            logArea.append((i - start + 1) + ". " + activityLog.get(i) + "\n");
        }
        if (activityLog.isEmpty()) logArea.append("No activity yet.");
    }

    private String getActivityLogSummary() {
        if (activityLog.isEmpty()) return "No activity recorded yet.";
        StringBuilder sb = new StringBuilder("Here's a summary of recent actions:\n");
        int start = Math.max(0, activityLog.size() - 5);
        for (int i = start; i < activityLog.size(); i++) {
            sb.append((i - start + 1) + ". " + activityLog.get(i) + "\n");
        }
        return sb.toString();
    }


    //  STYLE HELPERS

    private JPanel darkPanel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(new Color(15, 20, 30));
        return p;
    }

    private JButton cyberButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(0, 60, 90));
        b.setForeground(Color.CYAN);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
        return b;
    }

    private JLabel cyberLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.CYAN);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private void styleTextField(JTextField f) {
        f.setBackground(new Color(20, 30, 45));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.CYAN);
        f.setFont(new Font("Consolas", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
    }


    //  MAIIIN
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HackProofMain::new);
    }
}
