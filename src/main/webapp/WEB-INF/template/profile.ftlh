<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя - Аукцион автомобильных номеров</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/style.css">

</head>
<body>
<div class="header">
    <div>
        <h1>Аукцион автомобильных номеров</h1>
    </div>
    <div>
        <span>Привет, ${user.username}!</span>
        <a href="${contextPath}/profile" class="btn">Профиль</a>
            <form action="${contextPath}/logout" method="post" style="display: inline;">
                <button type="submit" class="btn btn-secondary">Выйти</button>
            </form>
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

    <div class="profile-info">
        <div class="profile-header">
            <div class="username">${user.username}</div>
            <p style="color: #7f8c8d; font-size: 1.1rem;">Управление вашим профилем и лотами</p>
        </div>

        <div class="user-stats">
            <div class="stat-card">
                <div class="stat-number">${userBids?size!0}</div>
                <div class="stat-label">Всего ставок</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">
                    <#if userBids?? && userBids?size gt 0>
                        ${userBids?first.amount} ₽
                    <#else>
                        0 ₽
                    </#if>
                </div>
                <div class="stat-label">Последняя ставка</div>
            </div>
        </div>

        <div style="background: #f8f9fa; padding: 1.5rem; border-radius: 8px;">
            <h3 style="margin-top: 0; color: #2c3e50;">📧 Контактная информация</h3>
            <p><strong>Email:</strong> ${user.email}</p>
        </div>
    </div>

    <div class="user-lots">
        <h2>🏷️ Мои лоты</h2>

        <#if userLots?? && userLots?size gt 0>
            <#list userLots as lot>
                <div class="lot-item">
                    <div class="lot-price">${lot.currentPrice} ₽</div>
                    <div class="lot-info">
                        <div class="lot-title">${lot.number} ${lot.region}</div>
                        <div class="lot-end-date">
                            Завершается: <span class="date-format" data-date="${lot.endDate}">${lot.endDate}</span>
                        </div>
                        <#if lot.description??>
                            <div style="color: #7f8c8d; font-size: 0.9rem; margin-top: 0.5rem;">
                                ${lot.description}
                            </div>
                        </#if>
                    </div>
                    <div class="lot-actions">
                        <a href="${contextPath}/plate/${lot.id}" class="btn">👁️ Просмотр</a>
                        <a href="${contextPath}/edit-lot?id=${lot.id}" class="btn btn-warning">✏️ Редактировать</a>
                        <form action="${contextPath}/manage-lot" method="post" style="display: inline;">
                            <input type="hidden" name="plateId" value="${lot.id}">
                            <input type="hidden" name="action" value="extend">
                            <button type="submit" class="btn btn-success">⏰ Продлить</button>
                        </form>
                        <form action="${contextPath}/manage-lot" method="post" style="display: inline;">
                            <input type="hidden" name="plateId" value="${lot.id}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Удалить лот?')">🗑️ Удалить</button>
                        </form>
                    </div>
                </div>
            </#list>
        <#else>
            <div class="empty-state">
                <h3>📦 Лотов пока нет</h3>
                <p>Вы еще не создали ни одного лота</p>
                <a href="${contextPath}/create-lot" class="btn btn-success" style="margin-top: 1rem;">
                    ➕ Создать первый лот
                </a>
            </div>
        </#if>
    </div>

    <div class="user-bids">
        <h2>📊 История моих ставок</h2>

        <#if userBids?? && userBids?size gt 0>
            <#list userBids as bid>
                <div class="bid-item">
                    <div class="bid-amount">${bid.amount} ₽</div>
                    <div class="bid-info">
                        <div class="bid-lot">Лот #${bid.plateId}</div>
                        <div class="bid-time">
                            <span class="date-format" data-date="${bid.timestamp}">${bid.timestamp}</span>
                        </div>
                    </div>
                    <a href="${contextPath}/plate/${bid.plateId}" class="btn">Перейти к лоту</a>
                </div>
            </#list>
        <#else>
            <div class="empty-state">
                <h3>🤔 Ставок пока нет</h3>
                <p>Вы еще не участвовали в аукционах</p>
                <a href="${contextPath}/" class="btn btn-success" style="margin-top: 1rem;">
                    🚀 Найти лоты для ставок
                </a>
            </div>
        </#if>
    </div>

    <div style="text-align: center; margin-top: 2rem;">
        <a href="${contextPath}/" class="btn btn-secondary">← Назад к списку аукционов</a>
    </div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>

<script src="${contextPath}/static/js/main.js"></script>

</body>
</html>