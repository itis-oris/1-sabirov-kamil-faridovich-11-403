<#import "layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <div class="plate-detail">
            <h1>🚗 ${plate.number} ${plate.region}</h1>
            <p class="description">${plate.description!''}</p>

            <#if error??>
                <div class="error-message">${error}</div>
            </#if>

            <div class="auction-info">
                <div class="current-price">
                    <h2>💰 Текущая цена: ${plate.currentPrice} ₽</h2>
                    <p>Начальная цена: ${plate.startingPrice} ₽</p>
                    <p>⏰ Аукцион завершится: ${plate.endDate?string('dd.MM.yyyy HH:mm')}</p>
                </div>

                <#if user??>
                    <form id="bid-form" class="bid-form" method="post" action="${contextPath}/bid">
                        <input type="hidden" name="plateId" value="${plate.id}">
                        <div class="form-group">
                            <label for="amount">Ваша ставка (мин. ${plate.currentPrice + 100} ₽):</label>
                            <input type="number" id="amount" name="amount"
                                   min="${plate.currentPrice + 100}" step="100" required
                                   placeholder="${plate.currentPrice + 100}">
                        </div>
                        <button type="submit" class="btn btn-primary">Сделать ставку</button>
                    </form>
                <#else>
                    <div class="bid-form">
                        <p>🔒 <a href="${contextPath}/login">Войдите</a>, чтобы сделать ставку</p>
                    </div>
                </#if>
            </div>

            <div class="bid-history">
                <h3>📊 История ставок</h3>
                <#if bids?? && bids?size gt 0>
                    <div class="bids-list">
                        <#list bids as bid>
                            <div class="bid-item">
                                <span class="bid-amount">${bid.amount} ₽</span>
                                <span class="bid-time">${bid.timestamp?string('dd.MM.yyyy HH:mm')}</span>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <p>Ставок пока нет</p>
                </#if>
            </div>
        </div>
    </div>
</@layout.layout>