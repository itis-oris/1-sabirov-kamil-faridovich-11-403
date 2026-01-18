<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактирование лота - Аукцион автомобильных номеров</title>
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
    <div class="edit-lot-section">
        <div class="edit-header">
            <div class="edit-title">Редактирование лота</div>
            <p style="color: #7f8c8d;">Изменение данных автомобильного номера</p>
        </div>

        <#if error??>
            <div class="error-message">${error}</div>
        </#if>

        <#if plate??>
            <div class="current-info">
                <strong>Текущий лот:</strong> ${plate.number} ${plate.region}
            </div>

            <form action="${contextPath}/edit-lot" method="post">
                <input type="hidden" name="plateId" value="${plate.id}">

                <div class="form-group">
                    <label for="number">Номер:</label>
                    <input type="text" id="number" name="number" required
                           value="${plate.number}">
                </div>

                <div class="form-group">
                    <label for="region">Регион:</label>
                    <input type="text" id="region" name="region" required
                           value="${plate.region}">
                </div>

                <div class="form-group">
                    <label for="description">Описание:</label>
                    <textarea id="description" name="description" rows="4">${plate.description!}</textarea>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-success">Сохранить изменения</button>
                    <a href="${contextPath}/profile" class="btn btn-secondary">Отмена</a>
                </div>
            </form>
        <#else>
            <div class="error-message">Лот не найден</div>
            <div style="text-align: center; margin-top: 1rem;">
                <a href="${contextPath}/profile" class="btn">Вернуться в профиль</a>
            </div>
        </#if>
    </div>
</div>

<div class="footer">
    <p>© 2025 Аукцион автомобильных номеров. Все права защищены.</p>
</div>
</body>
</html>