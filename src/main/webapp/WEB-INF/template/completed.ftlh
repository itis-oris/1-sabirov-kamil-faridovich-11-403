<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Завершенные аукционы</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/style.css">

</head>
<body>
<div class="header">
    <div>
        <h1>Аукцион завершенные</h1>
    </div>
    <div>
        <a href="${contextPath}/" class="btn btn-secondary">На главную</a>
    </div>
</div>

<div class="content">
    <h1>Завершенные аукционы</h1>

    <#if completedAuctions?? && completedAuctions?size gt 0>
        <div class="plates-grid">
            <#list completedAuctions as auction>
                <div class="plate-card">
                    <div class="status-badge">Завершен</div>
                    <h3>${auction.number} ${auction.region}</h3>
                    <p>${auction.description!''}</p>

                    <div class="auction-result">
                        <p class="final-price">🏆 Финальная цена: <strong>${auction.currentPrice} ₽</strong></p>

                        <#if auction.winnerUsername??>
                            <p class="winner">👑 Победитель: <strong>${auction.winnerUsername}</strong></p>
                            <#if auction.winningAmount??>
                                <p>💎 Выигрышная ставка: ${auction.winningAmount} ₽</p>
                            </#if>
                        <#else>
                            <p class="winner">❌ Победителя нет</p>
                        </#if>

                        <p class="end-date">
                            ⏰ Завершен <span class="date-format" data-date="${auction.endDate}">${auction.endDate}</span>
                        </p>
                    </div>

                    <a href="${contextPath}/plate/${auction.id}" class="btn btn-secondary">Посмотреть историю</a>
                </div>
            </#list>
        </div>
    <#else>
        <div class="empty-state">
            <h2>📭 Нет завершенных аукционов</h2>
            <p>Здесь появятся лоты, время торгов по которым истекло.</p>
            <a href="${contextPath}/" class="btn btn-success">Перейти к активным аукционам</a>
        </div>
    </#if>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
<script src="${contextPath}/static/js/main.js"></script>
</body>
</html>