<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Аукцион автомобильных номеров</title>
    <link rel="stylesheet" href="C:\Users\sabir\IdeaProjects\SemestrovkaAuction\src\main\webapp\static\css">
</head>
<body>
<header>
    <nav>
        <div class="logo">
            <a href="/Auction/">🚗 Аукцион номеров</a>
        </div>
        <div class="auth">
            <a href="/Auction/login">Войти</a>
            <a href="/Auction/register">Регистрация</a>
        </div>
    </nav>
</header>

<main>
    <div class="container">
        <h1>Активные аукционы</h1>

        <#if error??>
            <div class="error">${error}</div>
        </#if>

        <#if plates?? && plates?size gt 0>
            <div class="plates-grid">
                <#list plates as plate>
                    <div class="plate-card">
                        <h3>${plate.number} ${plate.region}</h3>
                        <p class="description">${plate.description!''}</p>
                        <p class="price">${plate.currentPrice} ₽</p>
                        <a href="/Auction/plate/${plate.id}" class="btn">Подробнее</a>
                    </div>
                </#list>
            </div>
        <#else>
            <p>Нет активных аукционов</p>
        </#if>
    </div>
</main>

<footer>
    <p>Аукцион автомобильных номеров</p>
</footer>
</body>
</html>