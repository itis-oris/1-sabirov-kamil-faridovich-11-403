<#import "layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <div class="auth-form">
            <h1>Вход в систему</h1>

            <#if error??>
                <div class="error-message">${error}</div>
            </#if>

            <form action="${contextPath}/login" method="post">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required value="${email!''}">
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%;">Войти</button>
            </form>

            <p style="text-align: center; margin-top: 1rem;">
                Нет аккаунта? <a href="${contextPath}/register">Зарегистрируйтесь</a>
            </p>
        </div>
    </div>
</@layout.layout>