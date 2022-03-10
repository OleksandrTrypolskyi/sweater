<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label>User name</label>
            <input type="text" name="username" class="form-control col-sm-4" placeholder="user name"/>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label>Email</label>
                <input type="text" name="email" class="form-control col-sm-4" placeholder="email"/>
            </div>
        </#if>
        <div class="form_group">
            <label>Password</label>
            <input type="password" name="password" class="form-control col-sm-4" placeholder="password"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <div class="mt-2">
                <a href="/registration">Add new user</a>
            </div>
        </#if>
        <div><input type="submit" value="Sign In" class="btn btn-primary mt-4"/></div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="form-inline">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign Out" class="btn btn-primary my-2 my-sm-0"/>
    </form>
</#macro>