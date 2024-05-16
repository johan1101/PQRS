<%@page import="Clases.MetodosUsuarios"%>
<%@page import="Clases.Usuarios"%>
<%@page import="java.sql.Connection"%>
<%@page import="Clases.Conexion"%>
<%@page import="Clases.Metodos"%>
<%@include file= "templates/header.jsp" %>

<style>
    <%@include file= "styles/style.css" %>
</style>

<!-- toastr -->
<% 
    String toastr;
    toastr = (String) request.getAttribute("toastr");
    System.out.println(toastr);

    if(toastr != null && toastr.equals("existente")){
    %>
    <script>
    $(document).ready(function () {
        cedulaExistente();
    });
</script>
    <%
    }else if(toastr != null && toastr.equals("noExistente")){
    %>
    <script>
    $(document).ready(function () {
        cedulaR();
    });
</script>
    <%
    }
%>





<%
    Metodos metodo = new Metodos();
    Conexion conectar = new Conexion();
    Usuarios usuario = new Usuarios();
    Connection conn = conectar.establecerConexion();
    int idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
    usuario = MetodosUsuarios.obtenerUsuarioPorId(idUsuario, conn);
%>
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
                <a href="index.jsp" class="button">
                    <i class="ph-lightning-bold"></i>
                    <span>Cerrar Sesion</span>
                </a>
            </div>
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
            <a href="usuario.jsp?res=Por responder">Solicitudes</a>
            <a href="usuario.jsp?res=Respondido">Respuestas</a>
        </div>
        <div class="content-header">
            <div class="content-header-actions">
                <a href="#" class="button">
                    <i class="ph-faders-bold"></i>
                    <span>Filters</span>
                </a>
                <a href="#" id="btnEditarUsuario" data-nombre="<%= usuario.getIdUsuario()%>" class="button">
                    <i class="ph-plus-bold"></i>
                    <span>Editar perfil</span>
                </a>
            </div>
        </div>


        <div class="content">
            <div class="content-main" >
                <div class="card-grid" style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
                    <article class="user" style="width: 1200px; text-align: center;">
                        <div class="card-body">
                            <div class="modal-dialog modal-dialog-centered custom-modal-size"  style="width: 1200px;">
                                <div class="modal-content">
                                    <div class="popup">
                                        <div class="form">
                                            <div>
                                                <span><img src="img/icono.png" /></span>
                                                <h3>Información personal</h3>
                                            </div>
                                            <hr>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-element">
                                                        <label for="nombre">Nombre</label>
                                                        <input type="text" id="nombre" value="<%= usuario.getNombre()%>" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números" readonly>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-element">
                                                        <label for="nombre">Apellido</label>
                                                        <input type="text" id="nombre" value="<%= usuario.getApellido()%>" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-element">
                                                        <label for="nombre">Cedula</label>
                                                        <input type="text" id="nombre" value="<%= usuario.getCedula()%>" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números" readonly>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-element">
                                                        <label for="nombre">Celular</label>
                                                        <input type="text" id="nombre" value="<%= usuario.getCelular()%>" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-element">
                                                        <div class="form-element">
                                                            <label for="nombre">Correo</label>
                                                            <input type="text" id="nombre" value="<%= usuario.getCorreo()%>" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números" readonly>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- partial -->

<!-- Modal para editar el usuario -->
<form action="SvEditarUsuario" method="POST">
    <div class="modal fade" id="modalEditar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modalEditarLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered custom-modal-size">
            <div class="modal-content" style="text-align: center;">

                <div class="popup">
                    <div class="close-btn btn-close" data-bs-dismiss="modal">&times;</div>
                    <div class="user-details" id="user-details">
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>



<script>
    $(document).on('click', '#btnEditarUsuario', function () {
        var idMostrado = $(this).attr('data-nombre'); // Obtiene el valor del atributo data-nombre del botón
        $.ajax({
            url: 'SvEditarUsuario?idUsuario=' + idMostrado,
            method: 'GET',
            success: function (data) {
                $('#user-details').html(data);
                $('#modalEditar').modal('show'); // Muestra el modal después de obtener los datos
            },
            error: function () {
                console.log('Error al realizar la solicitud de visualización.');
            }
        });
    });
    
        function cedulaExistente() {
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

        // Mostrar una notificación Toastr de error
        toastr.error('Ya existe un usuario con esa cedula', '!Ups¡');
    }
    
        function cedulaR() {
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
        toastr.success('Se ha editado exitosamente!', 'Editado');
    }
</script>

<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>