# XSS Vulnerability Demo — Educational Project
## Cybersecurity Course Material

---

## Files in This Project

| File | Purpose |
|---|---|
| `VulnerableServlet.java` | Java servlet with **intentional** Reflected and Stored XSS vulnerabilities |
| `SecureServlet.java` | The same servlet with all XSS mitigations applied |
| `xss_test_harness.html` | Self-contained interactive attack harness — open directly in a browser |

---

## Learning Objectives

After completing this lab, students will be able to:
1. Explain the difference between Reflected, Stored, and DOM-Based XSS
2. Identify the exact line of code that creates an XSS vulnerability
3. Craft basic XSS payloads including `<script>`, `<img onerror>`, and `<svg onload>` variants
4. Apply output encoding as the primary mitigation
5. Describe the role of Content-Security-Policy and HttpOnly cookies as defence-in-depth

---

## Setup — Running the Java Servlets

### Prerequisites
- Java 11+
- Apache Tomcat 9+ (or any Jakarta EE servlet container)
- Maven (optional)

### Steps
```bash
# 1. Place both servlets in your web app's src/main/java directory

# 2. Add the servlet API dependency (Maven):
#    <dependency>
#      <groupId>javax.servlet</groupId>
#      <artifactId>javax.servlet-api</artifactId>
#      <version>4.0.1</version>
#      <scope>provided</scope>
#    </dependency>

# 3. Deploy to Tomcat
#    Vulnerable page:  http://localhost:8080/your-app/vulnerable/comments
#    Secure page:      http://localhost:8080/your-app/secure/comments
```

### Quick test (without a server)
Open `xss_test_harness.html` directly in any browser — no server required. The harness
simulates the vulnerable responses in a sandboxed iframe using srcdoc.

---

## Using the Test Harness

The harness has four tabs:

### 1. Reflected XSS
- Browse the **Payload Library** on the left
- Click any payload to load it into the **Attack Builder**
- Click **Build URL** to generate the attack URL
- Click **Run in Sandbox** to execute it in the isolated preview frame
- Watch the **Attack Log** to see what an attacker would observe

### 2. Stored XSS
- Select a stored payload (simulates POSTing a malicious comment)
- Click **Simulate Victim Visit** to render the "victim's" browser view
- Observe that the payload fires without the victim clicking any link

### 3. DOM-Based XSS
- See how hash-fragment injection bypasses server-side defences
- The vulnerable JS pattern is: `element.innerHTML = location.hash.slice(1)`

### 4. Mitigations
- Side-by-side comparison of vulnerable vs secure code
- Live encoding demo — type any payload and see it rendered safely

---

## Key Concepts to Cover in Class

### Why output encoding is the fix (not input validation)
Input validation (blocking `<script>`) is easily bypassed:
- `<img onerror=...>` — no `<script>` tag
- `<svg onload=...>` — no `<script>` tag
- URL encoding: `%3Cscript%3E`
- Case variations: `<SCRIPT>`, `<ScRiPt>`

Output encoding converts characters to their HTML entity equivalents at the point they
are written to the page. The browser receives safe text that it cannot execute.

### Encode at render time, not storage time
- Store raw data in the database
- Encode when rendering to HTML, JavaScript, CSS, or URL contexts
- Each context has different encoding rules (HTML ≠ JS ≠ URL)

### Defence-in-depth layers
1. **Output encoding** — primary mitigation (always required)
2. **Content-Security-Policy** — blocks inline scripts even if encoding fails
3. **HttpOnly cookies** — prevents cookie theft even if XSS fires
4. **SameSite cookies** — limits CSRF via XSS chains
5. **Input validation** — reduces attack surface, not a substitute for encoding

---

## Discussion Questions

1. Why can a `<img onerror>` payload succeed when a filter blocks `<script>` tags?
2. What is the difference in severity between Reflected XSS and Stored XSS? Why?
3. Why should you encode at render time rather than when data is stored?
4. How does a Content-Security-Policy header defend against XSS? What are its limitations?
5. What happens if an attacker can steal a session cookie via XSS?
6. How does HttpOnly on a cookie prevent cookie theft, and what can an attacker still do?
7. Given DOM-based XSS never touches the server, how would you detect it with monitoring?

---

## Recommended Libraries

Instead of rolling a custom encoder (as shown for illustration), use:
- **OWASP Java Encoder**: `Encode.forHtml(input)` — covers HTML, JS, CSS, URL contexts
- **Apache Commons Text**: `StringEscapeUtils.escapeHtml4(input)`

OWASP reference: https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html

---

*For educational use only. Do not test against systems you do not own.*
