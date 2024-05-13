<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Metodos"%>
<%@page import="Clases.Metodos"%>
<%@page import="java.sql.Connection"%>
<%@page import="Clases.Conexion"%>
<%@include file= "templates/header.jsp" %>
<%

    ArrayList<Solicitudes> a = Metodos.getSolicitudes();

%>

<% 
    String toastr;
    toastr = (String) request.getAttribute("toastr");
    System.out.println(toastr);
    if(toastr != null && toastr.equals("siPasa")){
    %>
    <script>
    $(document).ready(function () {
        bienvenida();
    });
</script>
    <%
    }
%>

<style>
    <%@include file= "styles/style.css" %>

    .idOculto {
        display: none; /* Esto ocultará el elemento con la clase 'idOculto' */

        .article-container {
            max-width: 400px; /* Puedes ajustar este valor según tu preferencia */
            margin: 0 auto; /* Para centrar el artículo horizontalmente en la página */
        }
    }
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
            <h1>Area de solicitudes</h1>
            <div class="search">
                <form action="SvBuscarResponder" method="GET">
                    <input type="text" name="buscar" placeholder="Buscar" />
                    <button type="submit">
                        <i class="ph-magnifying-glass-bold"></i>
                    </button>
                </form>
            </div>
        </div>

        <%                String res = request.getParameter("res");
            String busqueda = request.getParameter("busqueda");
        %>
        <div class="horizontal-tabs">         
            <a href="solicitudes.jsp?res=no"<%if (res != null && res.equals("no")) {%> class="active" <%}%>>Todas las Solicitudes</a>
            <a href="solicitudes.jsp?res=Por responder" <%if (res != null && res.equals("Por responder")) {%> class="active" <%}%>>Sin Respuesta</a>
        </div>


        <div class="content">
            <div class="content-panel">
                <div class="vertical-tabs">
                    <a href="solicitudes.jsp?res=<%=res%>" <%if (request.getParameter("par") == null) {%> class="active" <%}%>>Todas</a>
                    <a href="solicitudes.jsp?par=Peticion&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Peticion")) {%> class="active" <%}%>>Peticiones</a>
                    <a href="solicitudes.jsp?par=Queja&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Queja")) {%> class="active" <%}%>>Quejas</a>
                    <a href="solicitudes.jsp?par=Reclamo&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Reclamo")) {%> class="active" <%}%>>Reclamos</a>
                    <a href="solicitudes.jsp?par=Sugerencia&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Sugerencia")) {%> class="active" <%}%>>Sugerencias </a>
                </div>
            </div>
            <div class="content-main">

                <%
                    int mensaje = 0;
                    for (Solicitudes sol : a) {

                        if (busqueda == null) {
                            if (sol.getEstado().equals(res)) {

                                if (request.getParameter("par") == null && sol.getEstado().equals("Por responder")) {
                                    mensaje = mensaje + 1;
                %>
                <%=Metodos.listarAdministradores(sol, request)%>
                <%

                } else {
                    if (request.getParameter("par").equals(sol.getTipoSolicitud()) && sol.getEstado().equals("Por responder")) {%>

                <%=Metodos.listarAdministradores(sol, request)%>
                <% mensaje = mensaje + 1;
                        }
                    }
                } else {
                    if (request.getParameter("par") == null) {
                        mensaje = mensaje + 1;
                %>
                <%=Metodos.listarAdministradores(sol, request)%>
                <%
                } else if (request.getParameter("par").equals(sol.getTipoSolicitud())) {
                    mensaje = mensaje + 1;
                %>
                <%=Metodos.listarAdministradores(sol, request)%>
                <%
                        }
                    }
                } else if (busqueda.contains(sol.getNombreSol()) || busqueda.contains(sol.getDescripcion()) || busqueda.contains(sol.getUsuario())) {
                    mensaje = mensaje + 1;
                %>
                <%=Metodos.listarAdministradores(sol, request)%>
                <%

                        }
                    }
                    if (mensaje == 0) {
                %>

                <%= Metodos.mensaje(request)%>
                <% }%>
            </div>
        </div>
    </div>
</main>
<!-- partial -->

<!-- Modal para responder una solicitud -->
<form action="SvResponderSolicitud" method="POST">
    <div class="modal fade" id="visualizar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modalVisualizar" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered custom-modal-size">
            <div class="modal-content">
                <div class="popup">
                    <div class="close-btn btn-close" data-bs-dismiss="modal">&times;</div>
                    <div class="tuto-details" id="tuto-details">

                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<script>
    $(document).on('click', '#btnVisualizar', function () {
        var idMostrado = $(this).attr('data-nombre'); // Obtiene el valor del atributo data-nombre del botón
        $.ajax({
            url: 'SvVisualizar?idTutorial=' + idMostrado,
            method: 'POST',
            success: function (data) {
                $('#tuto-details').html(data);
                $('#visualizar').modal('show'); // Muestra el modal después de obtener los datos
            },
            error: function () {
                console.log('Error al realizar la solicitud de visualización.');
            }
        });
    });

    document.addEventListener('DOMContentLoaded', function () {
        var expandirEnlaces = document.querySelectorAll('.expandir-descripcion');
        expandirEnlaces.forEach(function (enlace) {
            enlace.addEventListener('click', function (event) {
                event.preventDefault();
                var targetId = this.getAttribute('data-target');
                var descripcionCompleta = this.getAttribute('data-full-description');
                document.getElementById(targetId).innerHTML = 'Descripcion: <span class="full-description">' + descripcionCompleta + '</span>';
                this.style.display = 'none';
            });
        });
    });
    
            function bienvenida() {
        // Configurar opciones Toastr
        toastr.options = {
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-center",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };

        // Mostrar una notificación Toastr de éxito
        toastr.success('Bienvenido!', '!Hola¡');
    }
    
</script>

<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>
