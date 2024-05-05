<!-- Pagina principal- LOGIN -->

<!-- Incluci�n de la plantilla de header -->
<%@include file= "templates/header.jsp" %>

<style>
    <!-- Incluci�n del STYLE -->
    <%@include file= "styles/styleLogin.css" %>
</style>

<!--Formulario que se envia por POST -->
<div class="container" id="container">
    <div class="form-container sign-up">
        <form action="SvRegistrarUsuario" method="POST">
            <h1>Crear Cuenta</h1>
            <span>Ingresa tus datos</span>
            <input type="text" class="form-control" id="floatingInputValue" name="cedula" placeholder="Ingrese su c�dula" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten n�meros">
            <input type="text" class="form-control" id="floatingInputValue" name="nombre" placeholder="Ingrese su nombre" value="" required>
            <input type="text" class="form-control" id="floatingInputValue" name="apellido" placeholder="Ingrese su apellido" value="" required>
            <input type="text" class="form-control" id="floatingInputValue" name="celular" placeholder="Ingrese su n�mero celular" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten n�meros">
            <input type="email" class="form-control" id="floatingInputValue" name="correo" placeholder="Ingrese su correo electr�nico" value="" required>
            <input type="password" class="form-control" id="floatingPassword" name="contrasenia" placeholder="Ingrese su contrase�a" required>
            <select name="rol" class="form-control" >
                <option value="1" selected>Usuario</option>
                <option value="2">Administrador</option>
            </select>
            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
    </div>

    <!-- Contenedor adicional para los toastr -->
    <div class="posicion"></div>
    <div class="form-container sign-in">
        <form action="SvLoginCheck" method="POST">
            <h1>Ingresar</h1>
            <span>Ingresa tus datos de acceso</span>
            <input type="text" class="input1" name="cedula" placeholder="C�dula Usuario" maxlength="10" required pattern="[0-9]+" title="Solo se permiten n�meros">
            <input type="password" class="input1" name="contrasenia" placeholder="Contrase�a" required>
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
                <button class="hidden" id="register">Reg�strese aqui</button>
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

</script>


<!-- Incluci�n de la plantilla de footer -->
<%@include file= "templates/footer.jsp" %>