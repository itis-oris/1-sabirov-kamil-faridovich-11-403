<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Аукцион автомобильных номеров</title>
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
    </div>
</div>

<div class="content">
    <h1>Активные лоты</h1>
    <#if user??>
    </#if>

    <#if error??>
        <div class="error-message">${error}</div>
    </#if>

    <#if plates?? && plates?size gt 0>
        <div class="plates-grid">
            <#list plates as plate>
                <div class="plate-card">
                    <h3>${plate.number} ${plate.region}</h3>
                    <p>${plate.description!''}</p>
                    <p class="price"> ${plate.currentPrice} ₽</p>
                    <p class="end-date">
                        До <span class="date-format" data-date="${plate.endDate}">${plate.endDate}</span>
                    </p>
                    <a href="${contextPath}/plate/${plate.id}" class="btn">Сделать ставку</a>
                </div>
            </#list>
        </div>
    <#else>
        <p>Нет активных аукционов</p>
    </#if>

    <div style="margin-top: 2rem; text-align: center;">
        <a href="${contextPath}/create-lot" class="btn btn-success">➕ Создать лот</a>
        <a href="${contextPath}/completed" class="btn btn-secondary">🏁 Завершенные аукционы</a>
    </div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
<script src="${contextPath}/static/js/main.js"></script>
</body>
</html>