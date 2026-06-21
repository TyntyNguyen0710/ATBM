package Controller;

import dao.customerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet("/KeyManagement")
public class KeyManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String publicKey = customerDAO.getIntance().getPublicKeyByUsername(username);
        request.setAttribute("publicKey", publicKey);
        request.getRequestDispatcher("keyManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");

        if ("generateNewKey".equals(action)) {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);
                KeyPair keyPair = keyGen.generateKeyPair();

                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();

                String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
                String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

                Customer customer = customerDAO.getIntance().selectByUsername(username);
                int customerId = customer.getID();

                customerDAO.getIntance().updatePublicKey(customerId, publicKeyBase64);

                request.setAttribute("newPrivateKey", privateKeyBase64);
                request.setAttribute("newPublicKey", publicKeyBase64);
                request.setAttribute("message", "Bạn đã tạo cặp khóa mới thành công. Vui lòng lưu Private Key ngay!");

                request.getRequestDispatcher("keyManagement.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Lỗi khi tạo khóa mới: " + e.getMessage());
            }
        }
    }
}