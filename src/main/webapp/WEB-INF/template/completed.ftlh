<#import "layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <h1>🏁 Завершенные аукционы</h1>

        <#if error??>
            <div class="error-message">${error}</div>
        </#if>

        <nav class="page-nav">
            <a href="${contextPath}/" class="btn">⬅️ Назад к активным аукционам</a>
        </nav>

        <#if completedAuctions?? && completedAuctions?size gt 0>
            <div class="plates-grid">
                <#list completedAuctions as auction>
                    <div class="plate-card completed">
                        <div class="status-badge">Завершен</div>
                        <h3>${auction.number} ${auction.region}</h3>
                        <p class="description">${auction.description!''}</p>

                        <div class="auction-result">
                            <p class="final-price">🏆 Финальная цена: <strong>${auction.currentPrice} ₽</strong></p>

                            <#if auction.winnerUsername??>
                                <p class="winner">👑 Победитель: <strong>${auction.winnerUsername}</strong></p>
                                <#if auction.winningAmount??>
                                    <p class="winning-bid">💎 Выигрышная ставка: ${auction.winningAmount} ₽</p>
                                </#if>
                            <#else>
                                <p class="winner">❌ Победителя нет</p>
                            </#if>

                            <p class="end-date">⏰ Завершен: ${auction.endDate?string('dd.MM.yyyy HH:mm')}</p>
                        </div>

                        <a href="${contextPath}/plate/${auction.id}" class="btn btn-secondary">Посмотреть историю</a>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="empty-state">
                <h2>📭 Нет завершенных аукционов</h2>
                <p>Здесь появятся лоты, время торгов по которым истекло.</p>
                <a href="${contextPath}/" class="btn btn-primary">Перейти к активным аукционам</a>
            </div>
        </#if>
    </div>
</@layout.layout>