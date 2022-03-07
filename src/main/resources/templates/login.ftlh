<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>

<@common.page>
    <h1>Login page</h1>
    <@login.login "/login"/>
    <a href="/registration">Add new user</a>
</@common.page>