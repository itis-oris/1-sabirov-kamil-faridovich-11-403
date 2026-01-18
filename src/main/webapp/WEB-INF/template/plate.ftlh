<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лот ${plate.number} ${plate.region} - Аукцион автомобильных номеров</title>
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
    <#if error??>
        <div class="error-message">${error}</div>
    </#if>

    <#if success??>
        <div class="success-message">${success}</div>
    </#if>

    <div class="lot-info">
        <div class="plate-header">
            <div class="plate-number">${plate.number} ${plate.region}</div>
            <p style="color: #7f8c8d; font-size: 1.1rem;">${plate.description!''}</p>
        </div>

        <div class="current-price">${plate.currentPrice} ₽</div>

        <div class="price-info">
            <div class="price-item">
                <div class="price-label">Начальная цена</div>
                <div class="price-value">${plate.startingPrice} ₽</div>
            </div>
            <div class="price-item">
                <div class="price-label">Минимальный шаг</div>
                <div class="price-value">1000 ₽</div>
            </div>
            <div class="price-item">
                <div class="price-label">Следующая ставка</div>
                <div class="price-value">${plate.currentPrice + 1000} ₽</div>
            </div>
        </div>

        <div class="end-date">
            Завершения аукциона: <span class="date-format" data-date="${plate.endDate}">${plate.endDate}</span>
        </div>
    </div>

    <div class="bid-section">
        <h3>Сделать ставку</h3>
        <#if user??>
            <form action="${contextPath}/plate/${plate.id}" method="post">
                <input type="hidden" name="plateId" value="${plate.id}">

                <div class="form-group">
                    <label class="form-label" for="amount">Ваша ставка (₽)</label>
                    <input type="number"
                           id="amount"
                           name="amount"
                           class="form-input"
                           min="${plate.currentPrice + 1000}"
                           value="${plate.currentPrice + 1000}"
                           step="100"
                           required
                           placeholder="Введите сумму ставки">
                    <small style="color: #95a5a6;">Минимальная ставка: ${plate.currentPrice + 1000} ₽</small>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-success" style="font-size: 1.1rem; padding: 1rem 2rem;">
                        📨 Разместить ставку
                    </button>
                    <a href="${contextPath}/" class="btn btn-secondary">← Назад к списку</a>
                </div>
            </form>
        <#else>
            <div style="text-align: center; padding: 2rem;">
                <p style="font-size: 1.1rem; margin-bottom: 1rem;">Для участия в аукционе необходимо авторизоваться</p>
                <a href="${contextPath}/login" class="btn" style="margin-right: 1rem;">Войти</a>
                <a href="${contextPath}/register" class="btn btn-success">Регистрация</a>
            </div>
        </#if>
    </div>

    <div class="bid-history">
        <h3>📊 История ставок</h3>
        <#if bids?? && bids?size gt 0>
            <#list bids as bid>
                <div class="bid-item">
                    <div>
                        <div class="bid-user">
                            <#if bid.winnerUsername??>
                                ${bid.winnerUsername}
                            <#else>
                                Пользователь #${bid.userId}
                            </#if>
                        </div>
                        <div class="bid-time">
                            <span class="date-format" data-date="${bid.timestamp}">${bid.timestamp}</span>
                        </div>
                    </div>
                    <div class="bid-amount">${bid.amount} ₽</div>
                </div>
            </#list>
        <#else>
            <div style="text-align: center; padding: 2rem; color: #95a5a6;">
                <p>Ставок пока нет</p>
                <p>Будьте первым!</p>
            </div>
        </#if>
    </div>
</div>
<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>

<script src="${contextPath}/static/js/main.js"></script>

</body>
</html>