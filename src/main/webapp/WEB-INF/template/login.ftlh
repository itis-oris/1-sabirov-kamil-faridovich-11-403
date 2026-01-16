<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" type="text/css" href="/Auction/static/css/style.css">

</head>
<body>
<div class="header">
    <div>
        <h1>Аукцион автомобильных номеров</h1>
    </div>
    <div>
        <a href="/Auction/" class="btn btn-secondary">На главную</a>
    </div>
</div>

<div class="content">
    <div class="section">
        <h2>Вход в систему</h2>

        <#if error??>
            <div class="error-message">${error}</div>
        </#if>

        <form action="/Auction/login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required value="${email!''}">
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-success">Войти</button>
                <a href="/Auction/" class="btn btn-secondary">Отмена</a>
            </div>
        </form>

        <p style="text-align: center; margin-top: 1rem;">
            Нет аккаунта? <a href="/Auction/register">Зарегистрируйтесь</a>
        </p>
    </div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
</body>
</html>