//package src.main.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * EDUCATIONAL DEMO - XSS SECURE SERVLET
 * ======================================
 * This servlet is the SECURE version of VulnerableServlet.java.
 * It demonstrates the correct mitigations for both Reflected and Stored XSS.
 *
 * MITIGATIONS APPLIED:
 *  1. Output encoding — all user-supplied data is HTML-encoded before rendering.
 *     Characters like <, >, ", ', & are converted to their HTML entity equivalents,
 *     so the browser treats them as text, not markup.
 *  2. Content-Security-Policy header — restricts which scripts the browser allows.
 *  3. HttpOnly cookies — prevents JavaScript from reading session cookies
 *     (added for completeness; not shown in servlet logic but noted below).
 *
 * The single most important fix is #1: always encode output at the point of rendering.
 */
@WebServlet("/secure/comments")
public class SecureServlet extends HttpServlet {

    private static final List<String[]> commentStore = new ArrayList<>();

    /**
     * HTML-encodes a string to prevent XSS.
     *
     * Replaces the five characters that have special meaning in HTML:
     *   &  ->  &amp;
     *   <  ->  &lt;
     *   >  ->  &gt;
     *   "  ->  &quot;
     *   '  ->  &#x27;
     *
     * NOTE: In real applications, use a well-tested library such as:
     *   - OWASP Java Encoder  (org.owasp.encoder.Encode)
     *   - Apache Commons Text (StringEscapeUtils.escapeHtml4)
     * Rolling your own encoder is error-prone; this example is for illustration only.
     */
    private static String htmlEncode(String input) {
        if (input == null) return "";
        return input
            .replace("&",  "&amp;")   // must be first to avoid double-encoding
            .replace("<",  "&lt;")
            .replace(">",  "&gt;")
            .replace("\"", "&quot;")
            .replace("'",  "&#x27;");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // FIX 2: Content-Security-Policy header
        // Prevents inline scripts and restricts script sources to same origin.
        response.setHeader("Content-Security-Policy",
            "default-src 'self'; script-src 'self'; object-src 'none';");

        // FIX 3 (reminder): When creating session cookies, set HttpOnly and Secure flags:
        //   Cookie sessionCookie = new Cookie("JSESSIONID", sessionId);
        //   sessionCookie.setHttpOnly(true);   // JS cannot read this cookie
        //   sessionCookie.setSecure(true);     // only sent over HTTPS

        PrintWriter out = response.getWriter();
        String searchTerm = request.getParameter("search");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Community Comments (Secure)</title></head><body>");
        out.println("<h1>Community Comments</h1>");
        out.println("<hr>");

        // FIX 1: Reflected XSS — encode the search term before rendering
        if (searchTerm != null) {
            String safe = htmlEncode(searchTerm);           // <-- FIX APPLIED HERE
            out.println("<p>Showing results for: " + safe + "</p>");
        }

        // Display stored comments
        out.println("<h2>Comments</h2>");
        if (commentStore.isEmpty()) {
            out.println("<p><em>No comments yet. Be the first!</em></p>");
        } else {
            for (String[] comment : commentStore) {
                // FIX 1: Stored XSS — encode at render time, not at storage time
                // (Encoding at storage corrupts data; always encode at render time)
                out.println("<div style='border:1px solid #ccc; margin:8px; padding:8px;'>");
                out.println("<strong>" + htmlEncode(comment[0]) + "</strong>: ");  // <-- FIXED
                out.println(htmlEncode(comment[1]));                                // <-- FIXED
                out.println("</div>");
            }
        }

        // Comment submission form
        out.println("<hr><h2>Post a Comment</h2>");
        out.println("<form method='POST' action='/secure/comments'>");
        out.println("  Name: <input type='text' name='name'><br>");
        out.println("  Comment: <textarea name='comment' rows='3' cols='40'></textarea><br>");
        out.println("  <input type='submit' value='Post Comment'>");
        out.println("</form>");

        // Search form
        out.println("<hr><h2>Search Comments</h2>");
        out.println("<form method='GET' action='/secure/comments'>");
        out.println("  <input type='text' name='search' placeholder='Search...'>");
        out.println("  <input type='submit' value='Search'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name    = request.getParameter("name");
        String comment = request.getParameter("comment");

        if (name != null && comment != null && !name.isBlank() && !comment.isBlank()) {
            // Store raw data — encode only at render time (see doGet above)
            commentStore.add(new String[]{name, comment});
        }

        response.sendRedirect("/secure/comments");
    }
}
