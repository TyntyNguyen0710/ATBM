<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-Content-Type-Options" content="nosniff">
                <meta http-equiv="X-Frame-Options" content="DENY">
                <title>Kết quả xác thực chữ ký số</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Arial, sans-serif;
                        margin: 0;
                        padding: 40px 20px;
                        background: #f4f7fb;
                    }

                    .result {
                        max-width: 720px;
                        margin: 0 auto;
                        padding: 28px 32px;
                        border-radius: 14px;
                        border: 1px solid #dce4ef;
                        background: #fff;
                    }

                    .result--valid {
                        border-color: #1d9e75;
                        background: #eafaf3;
                    }

                    .result--danger {
                        border-color: #e24b4a;
                        background: #fdecec;
                    }

                    .result--warning {
                        border-color: #e8a020;
                        background: #fff7e8;
                    }

                    .result--neutral {
                        border-color: #888780;
                        background: #f1efe8;
                    }

                    .result__title {
                        font-size: 1.3rem;
                        font-weight: 700;
                        margin: 0 0 4px;
                        color: #0d1b2a;
                    }

                    .result__message {
                        font-size: .95rem;
                        color: #444;
                        line-height: 1.6;
                        margin-bottom: 20px;
                    }

                    .result__row {
                        display: flex;
                        gap: 8px;
                        margin-bottom: 10px;
                        font-size: .85rem;
                    }

                    .result__label {
                        flex-shrink: 0;
                        width: 160px;
                        color: #6b7c93;
                    }

                    .result__value {
                        font-family: 'Consolas', monospace;
                        word-break: break-all;
                        background: #f8f9fa;
                        padding: 6px 10px;
                        border-radius: 6px;
                        border: 1px solid #e2e6ea;
                        flex: 1;
                    }

                    .hash-match {
                        color: #1d9e75;
                    }

                    .hash-mismatch {
                        color: #e24b4a;
                        font-weight: 700;
                    }

                    .btn {
                        display: inline-block;
                        margin-top: 24px;
                        padding: 10px 22px;
                        background: #1a5c8a;
                        color: #fff;
                        text-decoration: none;
                        border-radius: 8px;
                        font-size: .9rem;
                    }
                </style>
            </head>

            <body>

                <div class="${boxClass}">
                    <p class="result__title">${icon} ${title}</p>
                    <p class="result__message">${fn:escapeXml(message)}</p>

                    <c:if test="${not empty signedAt}">
                        <div class="result__row">
                            <span class="result__label">Thời điểm ký:</span>
                            <span class="result__value">
                                <fmt:formatDate value="${signedAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                            </span>
                        </div>
                    </c:if>

                    <c:if test="${not empty savedHash}">
                        <div class="result__row">
                            <span class="result__label">Hash lúc ký (đã lưu):</span>
                            <span class="result__value">${savedHash}</span>
                        </div>
                        <div class="result__row">
                            <span class="result__label">Hash hiện tại (vừa tính):</span>
                            <span
                                class="result__value ${savedHash == invoiceHash ? 'hash-match' : 'hash-mismatch'}">${invoiceHash}</span>
                        </div>
                    </c:if>

                    <c:if test="${not empty signature}">
                        <div class="result__row">
                            <span class="result__label">Chữ ký số:</span>
                            <span class="result__value">${signature}</span>
                        </div>
                    </c:if>

                    <a href="trangchu.jsp" class="btn">Quay về trang chủ</a>
                </div>

            </body>

            </html>