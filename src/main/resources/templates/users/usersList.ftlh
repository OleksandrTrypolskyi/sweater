<#import "../parts/common.ftlh" as common>

<@common.page>
    <h1>List of users</h1>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td> <#list user.roles as role>${role}<#sep>, </#list> </td>
                <td><a href="/users/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>

</@common.page>