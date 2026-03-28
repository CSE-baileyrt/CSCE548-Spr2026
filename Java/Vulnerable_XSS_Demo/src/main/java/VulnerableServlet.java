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
 * EDUCATIONAL DEMO - XSS VULNERABLE SERVLET
 * ==========================================
 * This servlet is INTENTIONALLY VULNERABLE to Cross-Site Scripting (XSS).
 * It demonstrates a "Reflected XSS" and "Stored XSS" vulnerability.
 *
 * VULNERABILITY: User-supplied input is rendered directly into the HTML response
 * without any sanitization or output encoding. An attacker can inject arbitrary
 * JavaScript that executes in the victim's browser.
 *
 * DO NOT USE IN PRODUCTION.
 */
@WebServlet("/vulnerable/comments")
public class VulnerableServlet extends HttpServlet {

    // Simulated in-memory comment store (demonstrates Stored XSS)
    private static final List<String[]> commentStore = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // -----------------------------------------------------------------------
        // VULNERABILITY 1: REFLECTED XSS
        // The "search" parameter is read from the query string and written
        // directly into the HTML response. No encoding is applied.
        //
        // Attack URL: /vulnerable/comments?search=<script>alert('XSS')</script>
        // -----------------------------------------------------------------------
        String searchTerm = request.getParameter("search");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Community Comments (Vulnerable)</title></head><body>");
        out.println("<h1>Community Comments</h1>");
        out.println("<hr>");

        // VULNERABLE: raw user input injected into HTML output
        if (searchTerm != null) {
            out.println("<p>Showing results for: " + searchTerm + "</p>"); // <-- BUG HERE
        }

        // Display stored comments
        out.println("<h2>Comments</h2>");
        if (commentStore.isEmpty()) {
            out.println("<p><em>No comments yet. Be the first!</em></p>");
        } else {
            for (String[] comment : commentStore) {
                // VULNERABLE: stored comment written back to page without encoding
                out.println("<div style='border:1px solid #ccc; margin:8px; padding:8px;'>");
                out.println("<strong>" + comment[0] + "</strong>: "); // <-- BUG HERE
                out.println(comment[1]);                               // <-- BUG HERE
                out.println("</div>");
            }
        }

        // Comment submission form
        out.println("<hr><h2>Post a Comment</h2>");
        out.println("<form method='POST' action='/vulnerable/comments'>");
        out.println("  Name: <input type='text' name='name'><br>");
        out.println("  Comment: <textarea name='comment' rows='3' cols='40'></textarea><br>");
        out.println("  <input type='submit' value='Post Comment'>");
        out.println("</form>");

        // Search form
        out.println("<hr><h2>Search Comments</h2>");
        out.println("<form method='GET' action='/vulnerable/comments'>");
        out.println("  <input type='text' name='search' placeholder='Search...'>");
        out.println("  <input type='submit' value='Search'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------------------------------------------------
        // VULNERABILITY 2: STORED XSS
        // The "name" and "comment" POST parameters are stored without sanitization
        // and later rendered to every user who visits the page.
        //
        // An attacker posts a comment containing a <script> tag.
        // Every subsequent visitor executes that script automatically.
        // -----------------------------------------------------------------------
        String name    = request.getParameter("name");
        String comment = request.getParameter("comment");

        if (name != null && comment != null && !name.isBlank() && !comment.isBlank()) {
            // VULNERABLE: storing raw, unsanitized input
            commentStore.add(new String[]{name, comment}); // <-- BUG HERE
        }

        response.sendRedirect("/vulnerable/comments");
    }
}
