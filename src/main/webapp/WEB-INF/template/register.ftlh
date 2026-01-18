<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/style.css">

</head>
<body>
<div class="header">
    <div>
        <h1>Аукцион регистрация</h1>
    </div>
    <div>
        <a href="${contextPath}/" class="btn btn-secondary">На главную</a>
    </div>
</div>

<div class="content">
    <div class="section">
        <h2>Регистрация</h2>
        <p>Создайте учетную запись для участия в аукционах:</p>

        <#if error??>
            <div class="error-message">${error}</div>
        </#if>

        <form action="${contextPath}/register" method="post">
            <div class="form-group">
                <label for="username">Имя пользователя:</label>
                <input type="text" id="username" name="username" required value="${username!''}">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required value="${email!''}">
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="passwordConfirm">Подтвердите пароль:</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required>
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-success">Зарегистрироваться</button>
                <a href="${contextPath}/" class="btn btn-secondary">Отмена</a>
            </div>
        </form>

        <p style="text-align: center; margin-top: 1rem;">
            Уже есть аккаунт? <a href="${contextPath}/login">Войти</a>
        </p>
    </div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
</body>
</html>