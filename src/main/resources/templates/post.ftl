<#import "main.ftl" as main>

<@main.main css=["/css/post.css"]>

<div class="row">
    <div class="col s12">
        <div class="card">
            <div class="card-image">
                <img class="blur" src="${post.img}">
                <span class="card-title">${post.title}</span>
            </div>
            <div class="card-content">
                <p>${post.body}</p>
            </div>
        </div>
    </div>
</div>
</@main.main>