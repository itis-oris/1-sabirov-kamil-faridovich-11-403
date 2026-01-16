<#import "layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <div class="auth-form">
            <h1>Регистрация</h1>

            <#if error??>
                <div class="error-message">${error}</div>
            </#if>

            <form action="${contextPath}/register" method="post">
                <div class="form-group">
                    <label for="username">Имя пользователя:</label>
                    <input type="text" id="username" name="username" required value="${username!''}">
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required value="${email!''}">
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="passwordConfirm">Подтвердите пароль:</label>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" required>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%;">Зарегистрироваться</button>
            </form>

            <p style="text-align: center; margin-top: 1rem;">
                Уже есть аккаунт? <a href="${contextPath}/login">Войдите</a>
            </p>
        </div>
    </div>
</@layout.layout>