<#import "parts/common.ftlh" as common>

<@common.page>
    <h5>${username}</h5>
    ${message!}
    <form method="post">
        <div class="form-group">
            <label>Email</label>
            <input type="text" name="email" class="form-control col-sm-4" placeholder="email" value="${email!''}"/>
        </div>
        <div class="form_group">
            <label>Password</label>
            <input type="password" name="password" class="form-control col-sm-4" placeholder="password"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Save" class="btn btn-primary mt-4"/></div>
    </form>
</@common.page>