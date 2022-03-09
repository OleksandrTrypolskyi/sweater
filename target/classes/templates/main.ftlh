<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>

<@common.page>

    <div>
        <div>Welcome to Sweater application</div>
        <br>
        <div>This application was developed during completion of course on YouTube channel: "LetsCode".</div>
        <a href="https://www.youtube.com/watch?v=jH17YkBTpI4&list=PLU2ftbIeotGoGSEUf54LQH-DgiQPF2XRO&ab_channel=letsCode">
            Spring Boot (Sweater App) - YouTube playlist.
        </a><br>
        <a href="https://github.com/OleksandrTrypolskyi/sweater">Github Repository</a>
        <br><br><br>
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Enter message">
            <input type="text" name="tag" placeholder="Enter tag">
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add message</button>
        </form>
        <br><br><br>
    </div>

    <div>
        <div>List of messages:</div>
        <br>
        <form method="get" action="/index">
            <input type="text" name="filter" placeholder="Filter by message text" value="${filter!}">
            <button type="submit">Find</button>
        </form>
        <#list messages as message>
            <div>
                <b>${message.id}</b>
                <span>${message.text}</span>
                <i>${message.tag}</i>
                <strong>${message.authorName}</strong>
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}">
                    </#if>
                </div>
            </div>
            <#else>
            <div>No message</div>
        </#list>
        <br><br><br>
    </div>

    <div>
        <@login.logout/>
    </div>
    <div>
        <a href="/users">Users list</a>
    </div>

</@common.page>