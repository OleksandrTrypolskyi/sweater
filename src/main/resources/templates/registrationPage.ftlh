<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>

<@common.page>
    <h1>Add new user</h1>
    ${message!}
    <@login.login "/registration"/>
</@common.page>