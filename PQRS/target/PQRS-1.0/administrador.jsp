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
                    <nav class="header-navigation-links">
                        <a href="administrador.jsp" > Home </a>
                        <a href="solicitudes.jsp?res=no"> Solicitudes </a>
                        <a href="agregar.jsp"> Agregar </a>
                    </nav>
                    <div class="header-navigation-actions">
                        <a href="index.jsp" class="button">
                            <i class="ph-lightning-bold"></i>
                            <span>Cerrar Sesion</span>
                        </a>
                        <a href="#" class="icon-button">
                            <i class="ph-gear-bold"></i>
                        </a>
                        <a href="#" class="icon-button">
                            <i class="ph-bell-bold"></i>
                        </a>
                        <a href="#" class="avatar">
                            <img src="https://uifaces.co/our-content/donated/gPZwCbdS.jpg" alt="" />
                        </a>
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
                    <h1>Pagina Principal</h1>                
                </div>
                <div class="content">

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
        <!-- partial -->

        <script>
            <%@include file= "scripts/script.js" %>
        </script>

        <%@include file= "templates/footer.jsp" %>
