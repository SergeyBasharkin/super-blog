<#import "main.ftl" as main>

<@main.main>

    <div class="row">
        <#list posts as post>
            <div class="col m4 s12 l3">
                <div class="card">
                    <div class="card-image waves-effect waves-block waves-light">
                        <img class="activator" src="${post.img}">
                    </div>
                    <div class="card-content">
                        <span class="card-title activator grey-text text-darken-4">${post.title}<i class="material-icons right">more_vert</i></span>
                        <p><a href="/post/${post.id}">Show more</a></p>
                    </div>
                    <div class="card-reveal">
                        <span class="card-title grey-text text-darken-4">Card Title<i class="material-icons right">close</i></span>
                        <p>${post.body}</p>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@main.main>