<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>

<@common.page>
    ${message!}
    <h1>Login page</h1>
    <@login.login "/login" false/>
</@common.page>