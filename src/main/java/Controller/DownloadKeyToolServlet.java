package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/DownloadKeyTool")
public class DownloadKeyToolServlet extends HttpServlet {

    private static final String FILE_NAME = "CipherTool.rar"; 
    private static final String FILE_PATH = "/downloads/" + FILE_NAME; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fullPath = getServletContext().getRealPath(FILE_PATH);
        File file = new File(fullPath);

        if (!file.exists()) {
            response.getWriter().println("File không tồn tại!");
            return;
        }

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + "\"");
        response.setContentLength((int) file.length());

        // Gửi file về client
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}