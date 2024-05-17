<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alert Example</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<script>
    function showAlert(alertTitle, alertText, alertIcon, alertConfirmButtonText, url) {
        Swal.fire({
            title: alertTitle,
            text: alertText,
            icon: alertIcon,
            confirmButtonText: alertConfirmButtonText
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = url;
            }
        });
    }
    // showAlert 함수 호출 시 URL을 매개변수로 전달
    showAlert('', '${msg}', 'info', '확인', '${url}');
</script>
</body>
</html>
