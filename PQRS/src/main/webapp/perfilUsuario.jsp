<%@include file= "templates/header.jsp" %>

<style>
    <%@include file= "styles/style.css" %>
</style>
<!-- partial:index.partial.html -->
<header class="header">
    <div class="header-content responsive-wrapper">
        <div class="header-logo">
            <a href="#">
                <div>
                    <img src="https://assets.codepen.io/285131/untitled-ui-icon.svg" />
                </div>
                <img src="https://assets.codepen.io/285131/untitled-ui.svg" />
            </a>
        </div>
        <div class="header-navigation">
            <div class="header-navigation-actions">
            </div>
        </div>
        <a href="#" class="button">
            <i class="ph-list-bold"></i>
            <span>Menu</span>
        </a>
    </div>
</header>
<main class="main">
    <div class="responsive-wrapper">
        <div class="main-header">
            <h1>Mi perfil</h1>
            <div class="search">
                <button type="submit">
                    <i class="ph-magnifying-glass-bold"></i>
                </button>
            </div>
        </div>
        <div class="horizontal-tabs">
            <a href="perfilUsuario" class="active">Perfil</a>
            <a href="usuario.jsp">Solicitudes</a>
            <a href="#">API</a>
        </div>
        <div class="content-header">
            <div class="content-header-actions">
                <a href="#" class="button">
                    <i class="ph-faders-bold"></i>
                    <span>Filters</span>
                </a>
                <a href="#" class="button">
                    <i class="ph-plus-bold"></i>
                    <span>Editar perfil</span>
                </a>
            </div>
        </div>
        <div class="content">
            <div class="content-main">
                <div class="card-grid">
                    <article class="card">
                        <div class="card-header">
                            <div>
                                <span><img src="img/icono.png" /></span>
                                <h3>Información personal</h3>
                            </div>
                        </div>
                        <div class="card-body">
                            <p>Link pull requests and automate workflows.</p>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- partial -->

<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>