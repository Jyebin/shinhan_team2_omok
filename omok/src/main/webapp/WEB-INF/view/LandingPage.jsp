<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>
    <link rel="stylesheet" href="/css/reset.css" />
    <link rel="stylesheet" href="/css/common.css" />
    <link rel="stylesheet" href="/css/index.css" />
    <script src="/js/index.js"></script>
</head>
<body class="body">
<main class="main">
    <form action="landing" method="post">
    <section class="body-item">
        <section class="login-container">
            <section class="login-item1">

            </section>
            <section class="login-item2">
                <div class="login-item2-container">
                    <div class="login-item2-div">ID</div><input class="login-item2-input" type="text" name="id">
                </div>
                <div class="login-item2-container">
                    <div class="login-item2-div">PW</div> <input class="login-item2-input" type="password" name="pwd">
                </div>
            </section>

            <section class="login-item3">
                <input type="submit" value="로그인"> <input class='signup-button' type="button" value="회원가입">
            </section>
        </section>



    </section>
        </form>
</main>
</body>
</html>