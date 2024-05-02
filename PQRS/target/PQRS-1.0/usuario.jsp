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

        // Si se ha ingresado información en la descripción y se ha seleccionado un archivo PDF
        if (descripcion.trim() !== "" && pdf.trim() !== "") {
            // Mostrar un mensaje de error
            muchaInformacion();
            // Detener el envío del formulario
            return false;
        }

        // Si se ha ingresado información en la descripción o se ha seleccionado un archivo PDF
        // Continuar con el envío del formulario
        return true;
    }
    
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
</script>


<script>
    <%@include file= "scripts/script.js" %>
</script>

<%@include file= "templates/footer.jsp" %>
