<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Metodos"%>
<%@page import="Clases.Conexion"%>
<%@page import="java.sql.Connection"%>
<%@include file= "templates/header.jsp" %>

<style>
    <%@include file= "styles/style.css" %>
</style>
<!-- partial:index.partial.html -->
<%
    ArrayList<Solicitudes> a = Metodos.SolicitudesUsuario((String) session.getAttribute("cedula"));
%>
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
        <a href="#" class="button">
            <i class="ph-list-bold"></i>
            <span>Menu</span>
        </a>
    </div>
</header>

<main class="main">
    <div class="responsive-wrapper">
        <div class="main-header">
            <h1>Tus solicitudes</h1>
            <div class="search">
                <input type="text" placeholder="Buscar" />
                <button type="submit">
                    <i class="ph-magnifying-glass-bold"></i>
                </button>
            </div>
        </div>
        <div class="horizontal-tabs">
            <a href="perfilUsuario.jsp">Perfil</a>
            <a href="usuario.jsp?res=no"> Solicitudes </a>
            <a href="#">Respuestas</a>
        </div>
        <div class="content-header">
            <div class="content-header-actions">
                <a href="#" class="button">
                    <i class="ph-faders-bold"></i>
                    <span>Filters</span>
                </a>
                <a href="#" class="button" data-bs-toggle="modal" data-bs-target="#agregarSolicitud">
                    <i class="ph-plus-bold"></i>
                    <span>Agregar solicitud</span>
                </a>
            </div>
        </div>

        <%                String res = request.getParameter("res");
            String busqueda = request.getParameter("busqueda");
        %>
        <div class="content">
            <div class="content-panel">
                <div class="vertical-tabs">
                    <a href="usuario.jsp?res=<%=res%>" <%if (request.getParameter("par") == null) {%> class="active" <%}%>>Todas</a>
                    <a href="usuario.jsp?par=Peticion&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Peticion")) {%> class="active" <%}%>>Peticiones</a>
                    <a href="usuario.jsp?par=Queja&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Queja")) {%> class="active" <%}%>>Quejas</a>
                    <a href="usuario.jsp?par=Reclamo&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Reclamo")) {%> class="active" <%}%>>Reclamos</a>
                    <a href="usuario.jsp?par=Sugerencia&res=<%=res%>" <%if (request.getParameter("par") != null && request.getParameter("par").equals("Sugerencia")) {%> class="active" <%}%>>Sugerencias </a>
                </div>
            </div>
            <div class="content-main">

                <%
                    int mensaje = 0;
                    Metodos.mensaje(request);
                    for (Solicitudes sol : a) {

                        if (busqueda == null) {
                            if (sol.getEstado().equals(res)) {

                                if (request.getParameter("par") == null) {
                %>
                <%=Metodos.listarUsuario(sol, request)%>
                <%
                    mensaje = mensaje + 1;
                } else {
                    if (request.getParameter("par").equals(sol.getTipoSolicitud())) {%>
                <%=Metodos.listarUsuario(sol, request)%>
                <%
                            mensaje = mensaje + 1;
                        }
                    }
                } else {
                    if (request.getParameter("par") == null) {
                        mensaje = mensaje + 1;
                %>
                <%=Metodos.listarUsuario(sol, request)%>
                <%
                } else if (request.getParameter("par").equals(sol.getTipoSolicitud())) {
                %>
                <%=Metodos.listarUsuario(sol, request)%>
                <%                    mensaje = mensaje + 1;
                        }
                    }
                } else if (busqueda.contains(sol.getNombreSol()) || busqueda.contains(sol.getDescripcion()) || busqueda.contains(sol.getUsuario())) {
                %>
                <%=Metodos.listarUsuario(sol, request)%>
                <%                    mensaje = mensaje + 1;

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

<!-- Modal para agregar un tutorial -->
<form action="SvAgregarSolicitud" method="POST" onsubmit="return validarFormulario()" enctype="multipart/form-data">
    <div class="modal fade" id="agregarSolicitud" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered custom-modal-size">
            <div class="modal-content">
                <div class="popup">
                    <div class="close-btn btn-close" data-bs-dismiss="modal">&times;</div>
                    <div class="form">
                        <h2>Agregar solicitud</h2>
                        <hr>
                        <div class="row">
                            <div class="col">
                                <div class="form-element">
                                    <label for="nombre">Nombre</label>
                                    <input type="text" id="nombre" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+" title="No se permiten números">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-element">
                                    <label for="tipoSolicitud">Tipo de solicitud</label>
                                    <select class="form-control" id="tipo" name="tipo" required>
                                        <option value="" disabled selected>Seleccione el tipo de solicitud</option>
                                        <option value="Peticion">Peticion</option>
                                        <option value="Queja">Queja</option>
                                        <option value="Reclamo">Reclamo</option>
                                        <option value="Sugerencia">Sugerencia</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="form-element">
                                    <label for="descripcion">Descripción</label>
                                    <textarea id="descripcion" name="descripcion" rows="4" cols="50" placeholder="Ingresa la descripción de la solicitud"></textarea>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-element">
                                    <label for="pdf">Subir archivo</label>
                                    <input type="file" id="pdf" name="pdf" accept="application/pdf">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="form-element">
                                    <button type="submit">Agregar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<!-- Modal Editar -->
<form class="row g-3 needs-validation"  action="SvEliminarEditarSolicitud" method="POST"  enctype="multipart/form-data" " novalidate>
    <div class="modal fade" id="editarModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered custom-modal-size">
            <div class="modal-content">
                <div class="popup">
                    <div class="close-btn btn-close" data-bs-dismiss="modal">&times;</div>
                    <div class="modal-body">

                        <div id="editar-details">
                            <!-- Contenido dinámico: Aquí se mostrarán los detalles a editar -->
                        </div>

                    </div>
                    <div class="modal-footer">

                    </div>
                </div>
            </div>
        </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<!-- Agrega este script al final de tu archivo HTML -->
<script>
    // Función para validar el formulario antes de enviarlo
    function validarFormulario() {
        // Obtener los valores de la descripción y del archivo PDF
        var descripcion = document.getElementById("descripcion").value;
        var pdf = document.getElementById("pdf").value;

        // Verificar si tanto la descripción como el archivo PDF están vacíos
        if (descripcion.trim() === "" && pdf.trim() === "") {
            // Mostrar un mensaje de error

            llenarInformacion();
            // Detener el envío del formulario
            return false;
        }

        // Si se ha ingresado información en la descripción o se ha seleccionado un archivo PDF
        // Continuar con el envío del formulario
        return true;
    }

    /**
     * Funcion editar
     */
    $(document).on('click', '#btnEditar', function () {
        var idMostrado = $(this).attr('data-nombre'); // Obtiene el valor del atributo data-nombre del botón
        $.ajax({
            url: 'SvVisualizar?idTutorial=' + idMostrado,
            method: 'GET',
            success: function (data) {
                $('#editar-details').html(data);
                $('#editarModal').modal('show'); // Muestra el modal después de obtener los datos
            },
            error: function () {
                console.log('Error al realizar la solicitud de visualización.');
            }
        });
    });
    // Seleccionar todos los elementos con la clase "deleteButton" y agregar un event listener a cada uno
    document.querySelectorAll(".deleteButton").forEach(function (button) {
        button.addEventListener("click", function () {
            // Obtener el título del libro desde el atributo "data-titulo"
            const id = this.getAttribute("data-titulo");

            // Crear un diálogo de confirmación personalizado con SweetAlert2
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-success',
                    cancelButton: 'btn btn-danger'
                },
                buttonsStyling: false
            });
            // Mostrar el diálogo de confirmación
            swalWithBootstrapButtons.fire({
                title: '¿Estás seguro?',
                text: '¡No podrás revertir esto!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Sí, borrarlo',
                cancelButtonText: 'No, cancelar ',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    // Redirige al servlet con el título como parámetro en la URL
                    window.location.href = "SvEliminarEditarSolicitud?id=" + encodeURIComponent(id);
                    // Mostrar un mensaje de cancelación si el usuario decide no eliminar
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Cancelado',
                            'Tu libro imaginario está a salvo :)',
                            'error'
                            );
                }
            });
        });
    });
    function llenarInformacn() {
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

    function llenarInformacion() {
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
        toastr.error('Debes llenar al menos uno de los campos: Descripción o Subir archivo', '!Ups¡');
    }

    function muchaInformacion() {
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
        toastr.error('Solo puedes llenar uno de los campos: Descripción o Subir archivo', '!Ups¡');
    }

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

</script>


<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>
