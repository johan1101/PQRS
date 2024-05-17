<!-- Pagina principal- LOGIN -->

<!-- Inclución de la plantilla de header -->
<%@include file= "templates/header.jsp" %>

<style>
    <!-- Inclución del STYLE -->
    <%@include file= "styles/styleLogin.css" %>
</style>

<%  //Manejo de alertas
    String toastr;
    toastr = (String) request.getAttribute("toastr");

    if(toastr != null && toastr.equals("registrado")){
    %>
    <script>
    $(document).ready(function () {
        registrado();//Registrado
    });
</script>
    <%
    }else if(toastr != null && toastr.equals("noRegistrado")){
    %>
    <script>
    $(document).ready(function () {
        noRegistrado();//No registrado
    });
</script>
    <%
    }else if(toastr != null && toastr.equals("noPasa")){
    %>
    <script>
    $(document).ready(function () {
        noPasa();//Datos incorrectos
    });
</script>
    <%
    }
%>


<!--Formulario que se envia por POST -->
<div class="container" id="container">
    <div class="form-container sign-up">
        <form action="SvRegistrarUsuario" method="POST">
            <h1>Crear Cuenta</h1>
            <span>Ingresa tus datos</span>
            <input type="text" class="form-control" id="floatingInputValue" name="cedula" placeholder="Ingrese su cédula" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten números">
            <input type="text" class="form-control" id="floatingInputValue" name="nombre" placeholder="Ingrese su nombre" value="" maxlength="50" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+" title="No se permiten números" required>
            <input type="text" class="form-control" id="floatingInputValue" name="apellido" placeholder="Ingrese su apellido" value="" required pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+" title="No se permiten números" required>
            <input type="text" class="form-control" id="floatingInputValue" name="celular" placeholder="Ingrese su número celular" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten números">
            <input type="email" class="form-control" id="floatingInputValue" name="correo" placeholder="Ingrese su correo electrónico" value="" required>
            <input type="password" class="form-control" id="floatingPassword" name="contrasenia" placeholder="Ingrese su contraseña" required>
            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
    </div>

    <!-- Contenedor adicional para los toastr -->
    <div class="posicion"></div>
    <div class="form-container sign-in">
        <form action="SvLoginCheck" method="POST">
            <h1>Ingresar</h1>
            <span>Ingresa tus datos de acceso</span>
            <input type="text" class="input1" name="cedula" placeholder="Cédula Usuario" maxlength="10" required pattern="[0-9]+" title="Solo se permiten números">
            <input type="password" class="input1" name="contrasenia" placeholder="Contraseña" required>
            <button type="submit" class="btn">Ingresar</button>
        </form>
    </div>
    <!-- Contenedor derecho -->

    <div class="toggle-container">
        <div class="toggle">
            <div class="toggle-panel toggle-left">
                <img src="img/vector.png" width="100%">
                <button class="hidden" id="login">Regresar</button>
            </div>
            <div class="toggle-panel toggle-right">
                <h1 id="nalmi">PQRSolution</h1>
                <img id="im" src="img/vector2.png" width="80%">
                <p id="cuenta">No tiene una cuenta?</p>
                <!-- Boton que muestra opcion registrar -->
                <button class="hidden" id="register">Regístrese aqui</button>
            </div>
        </div>
    </div>
</div>

<script>
    // Manejo boton REGISTRAR y REGRESAR 
    const container = document.getElementById('container');
    const registerBtn = document.getElementById('register');
    const loginBtn = document.getElementById('login');

    registerBtn.addEventListener('click', () => {
        container.classList.add("active");
    });

    loginBtn.addEventListener('click', () => {
        container.classList.remove("active");
    });

    function registrado() {
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
        toastr.success('Se ha registrado exitosamente!', 'Registrado');
    }
    
        function noRegistrado() {
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
    
        function noPasa() {
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
        toastr.error('cedula o contraseña incorrectas', '!Ups¡');
    }
</script>


<!-- Inclución de la plantilla de footer -->
<%@include file= "templates/footer.jsp" %>