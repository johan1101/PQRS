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
<%  System.out.println((String) session.getAttribute("cedula"));
     ArrayList<Solicitudes> a=Metodos.SolicitudesUsuario((String) session.getAttribute("cedula"));

     
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
            <a href="usuario.jsp" class="active">Solicitudes</a>
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
                     <% 
                    for (Solicitudes sol: a) {
                %>
                     <%=Metodos.listarUsuario(sol, request)%>
                     <%}%>
            </div>
        </div>
    </div>
            <a href="#" class="btn btn-danger deleteButton" id="deleteButton" data-titulo="1" >
  <i class="fas fa-trash"></i>
</a>
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
                                    <input type="text" id="nombre" name="nombre" placeholder="Ingresa el nombre de la solicitud" maxlength="50" required pattern="[a-zA-Z������������\s]+" title="No se permiten n�meros">
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
                                    <label for="descripcion">Descripci�n</label>
                                    <textarea id="descripcion" name="descripcion" rows="4" cols="50" placeholder="Ingresa la descripci�n de la solicitud"></textarea>
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
                            <!-- Contenido din�mico: Aqu� se mostrar�n los detalles a editar -->
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
  // Funci�n para validar el formulario antes de enviarlo
    function validarFormulario() {
        // Obtener los valores de la descripci�n y del archivo PDF
        var descripcion = document.getElementById("descripcion").value;
        var pdf = document.getElementById("pdf").value;

        // Verificar si tanto la descripci�n como el archivo PDF est�n vac�os
        if (descripcion.trim() === "" && pdf.trim() === "") {
            // Mostrar un mensaje de error
            
            llenarInformacion();
            // Detener el env�o del formulario
            return false;
        }

        // Si se ha ingresado informaci�n en la descripci�n y se ha seleccionado un archivo PDF
        if (descripcion.trim() !== "" && pdf.trim() !== "") {
            // Mostrar un mensaje de error
            muchaInformacion();
            // Detener el env�o del formulario
            return false;
        }

        // Si se ha ingresado informaci�n en la descripci�n o se ha seleccionado un archivo PDF
        // Continuar con el env�o del formulario
        return true;
    }
    
     /**
    * Funcion editar
    */
    $(document).on('click', '#btnEditar', function () {
        var idMostrado = $(this).attr('data-nombre'); // Obtiene el valor del atributo data-nombre del bot�n
        $.ajax({
            url: 'SvVisualizar?idTutorial=' + idMostrado,
            method: 'GET',
            success: function (data) {
                $('#editar-details').html(data);
                $('#editarModal').modal('show'); // Muestra el modal despu�s de obtener los datos
            },
            error: function () {
                console.log('Error al realizar la solicitud de visualizaci�n.');
            }
        });
    });
    // Seleccionar todos los elementos con la clase "deleteButton" y agregar un event listener a cada uno
    document.querySelectorAll(".deleteButton").forEach(function (button) {
        button.addEventListener("click", function () {
            // Obtener el t�tulo del libro desde el atributo "data-titulo"
            const id = this.getAttribute("data-titulo");

            // Crear un di�logo de confirmaci�n personalizado con SweetAlert2
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-success',
                    cancelButton: 'btn btn-danger'
                },
                buttonsStyling: false
            });
            // Mostrar el di�logo de confirmaci�n
            swalWithBootstrapButtons.fire({
                title: '�Est�s seguro?',
                text: '�No podr�s revertir esto!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'S�, borrarlo',
                cancelButtonText: 'No, cancelar ',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    // Redirige al servlet con el t�tulo como par�metro en la URL
                    window.location.href = "SvEliminarEditarSolicitud?id=" + encodeURIComponent(id);
                    // Mostrar un mensaje de cancelaci�n si el usuario decide no eliminar
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Cancelado',
                            'Tu libro imaginario est� a salvo :)',
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

            // Mostrar una notificaci�n Toastr de �xito
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

            // Mostrar una notificaci�n Toastr de error
            toastr.error('Debes llenar al menos uno de los campos: Descripci�n o Subir archivo', '!Ups�');
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

            // Mostrar una notificaci�n Toastr de error
            toastr.error('Solo puedes llenar uno de los campos: Descripci�n o Subir archivo', '!Ups�');
        }
        
   
</script>


<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>
