<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание лота - Аукцион автомобильных номеров</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/style.css">

</head>
<body>
<div class="header">
    <div>
        <h1>Аукцион автомобильных номеров</h1>
    </div>
    <div>
        <#if user??>
        <span>Привет, ${user.username}!</span>
        <a href="${contextPath}/profile" class="btn">Профиль</a>
        <form action="${contextPath}/logout" method="post" style="display: inline;">
            <button type="submit" class="btn btn-secondary">Выйти</button>
        </form>
        <#else>
        <a href="${contextPath}/login" class="btn">Войти</a>
        <a href="${contextPath}/register" class="btn btn-success">Регистрация</a>
    </#if>
    <a href="${contextPath}/" class="btn btn-secondary">На главную</a>
</div>
</div>

<div class="content">
    <div class="create-lot-section">
        <div class="create-header">
            <div class="create-title">Создание нового лота</div>
            <p style="color: #7f8c8d;">Добавьте автомобильный номер для аукциона</p>
        </div>

        <#if error??>
        <div class="error-message">${error}</div>
    </#if>

    <form action="${contextPath}/create-lot" method="post">
        <div class="form-group">
            <label for="number">Номер:</label>
            <input type="text" id="number" name="number" required
                   placeholder="A123BC">
            <div class="input-hint">Только латинские буквы A,B,E,K,M,H,O,P,C,T,Y,X и цифры</div>
        </div>

        <div class="form-group">
            <label for="region">Регион:</label>
            <input type="text" id="region" name="region" required
                   placeholder="777">
            <div class="input-hint">Только цифры (2-3 цифры)</div>
        </div>

        <div class="form-group">
            <label for="description">Описание:</label>
            <textarea id="description" name="description" rows="4"
                      placeholder="Описание номера (необязательно)"></textarea>
        </div>

        <div class="form-group">
            <label for="startingPrice">Начальная цена (₽):</label>
            <input type="number" id="startingPrice" name="startingPrice"
                   min="1000" step="100" required value="10000">
        </div>

        <div class="btn-group">
            <button type="submit" class="btn btn-success">Создать лот</button>
            <a href="${contextPath}/" class="btn btn-secondary">Отмена</a>
        </div>
    </form>
</div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
<script src="${contextPath}/static/js/main.js"></script>
</body>
</html>