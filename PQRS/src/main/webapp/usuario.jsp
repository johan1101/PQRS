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
            <h1>Area de solicitudes</h1>
            <div class="search">
                <input type="text" placeholder="Buscar" />
                <button type="submit">
                    <i class="ph-magnifying-glass-bold"></i>
                </button>
            </div>
        </div>
        <div class="horizontal-tabs">
            <a href="perfilUsuario.jsp">Perfil</a>
            <a href="usuario.jsp" class="active">Solicitudes</a>
            <a href="#">API</a>
        </div>
        <div class="content-header">
            <div class="content-header-intro">
                <h2>Intergrations and connected apps</h2>
                <p>Supercharge your workflow and connect the tool you use every day.</p>
            </div>
            <div class="content-header-actions">
                <a href="#" class="button">
                    <i class="ph-faders-bold"></i>
                    <span>Filters</span>
                </a>
                <a href="#" class="button">
                    <i class="ph-plus-bold"></i>
                    <span>Agregar solicitud</span>
                </a>
            </div>
        </div>
        <div class="content">
            <div class="content-panel">
                <div class="vertical-tabs">
                    <a href="#" class="active">Peticiones</a>
                    <a href="#">Quejas</a>
                    <a href="#">Reclamos</a>
                    <a href="#">Sugerencias</a>
                </div>
            </div>
            <div class="content-main">
                <div class="card-grid">
                    <article class="card">
                        <div class="card-header">
                            <div>
                                <span><img src="https://assets.codepen.io/285131/zeplin.svg" /></span>
                                <h3>Zeplin</h3>
                            </div>
                            <label class="toggle">
                                <input type="checkbox" checked>
                                <span></span>
                            </label>
                        </div>
                        <div class="card-body">
                            <p>Collaboration between designers and developers.</p>
                        </div>
                        <div class="card-footer">
                            <a href="#">View integration</a>
                        </div>
                    </article>
                    <article class="card">
                        <div class="card-header">
                            <div>
                                <span><img src="https://assets.codepen.io/285131/github.svg" /></span>
                                <h3>GitHub</h3>
                            </div>
                            <label class="toggle">
                                <input type="checkbox" checked>
                                <span></span>
                            </label>
                        </div>
                        <div class="card-body">
                            <p>Link pull requests and automate workflows.</p>
                        </div>
                        <div class="card-footer">
                            <a href="#">View integration</a>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </div>
</main>


<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>
