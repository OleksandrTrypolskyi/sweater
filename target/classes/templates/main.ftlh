<#import "parts/common.ftlh" as common>

<@common.page>

    <form method="get" action="/index">
        <div class="form-row">
            <input type="text" name="filter" placeholder="Message text" value="${filter!}">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </div>
    </form>

    <a class="btn btn-primary mt-3 mb-3" data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse mt-3" id="collapseExample">
        <div class="form-group">
            <form method="post" enctype="multipart/form-data">
                <input type="text" name="text" placeholder="Enter message" class="form-control mb-2 col-sm-4">
                <input type="text" name="tag" placeholder="Enter tag" class="form-control mb-2 col-sm-4">
                <div class="custom-file mb-2 col-sm-4">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add message</button>
                </div>
            </form>
        </div>
    </div>

    <div>
        <div>
            <h3>List of messages</h3>
        </div>

        <div class="card-columns">
            <#list messages as message>
                <div class="card my-3">
                    <#if message.filename??>
                        <img class="card-img-top" src="/img/${message.filename}">
                    </#if>
                    <div class="m-2">
                        <h5 class="card-title">${message.tag}</h5>
                        <p class="card-text">${message.text}</p>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.authorName}
                    </div>
                </div>
            <#else>
                <div>No message</div>
            </#list>
        </div>

        <br><br><br>
    </div>

</@common.page>