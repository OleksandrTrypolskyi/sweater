<#import "../parts/common.ftlh" as common>

<@common.page>
    <h1>User Editor</h1>
    <form action="/users" method="post">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="text" value="${user.username}" name="username">
        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                </label>
            </div>
        </#list>
        <button type="submit">Save</button>
    </form>
</@common.page>