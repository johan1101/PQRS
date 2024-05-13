<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Metodos"%>
<%@page import="Clases.Metodos"%>
<%@page import="java.sql.Connection"%>
<%@page import="Clases.Conexion"%>
<%@include file= "templates/header.jsp" %>

<style>
    <%@include file= "styles/style.css" %>
     .login .input .input1
    {
        font-size: 16px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: transparent;
        border: none;
        outline: none;
        border-bottom: 2px solid black;
        color: black;
        width: 100%;
        height: 100%;
    }


    .login .input i
    {
        position: relative;
        right: -370px;
        bottom: 27px;
        color: black;
    }

    .login .button
    {
        width: 100%;
        height: 40px;
        margin-bottom: 15px;
    }

    button
    {
        width: 100%;
        height: 40px;
        background-color: #499EBF !important;
        border: none;
        outline: none;
        font-size: 20px;
        font-weight: 700;
        border-radius: 7px;
        color: #fff !important;
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
            <h1>Ingresa los datos</h1>
           
            <div class="horizontal-tabs">         
            <a href="solicitudes.jsp?res=no" class="active">Agregar Administrador</a>

        </div>
        </div>
            
            <div class="content">

                <div class="content-main">
                       
        <form action="SvRegistrarUsuario" method="POST">

            <input type="hidden" name="redireccion" value="agregar">
            <input type="text" class="form-control" id="floatingInputValue" name="cedula" placeholder="Ingrese su cédula" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten números"><br>
            <input type="text" class="form-control" id="floatingInputValue" name="nombre" placeholder="Ingrese su nombre" value="" required><br>
            <input type="text" class="form-control" id="floatingInputValue" name="apellido" placeholder="Ingrese su apellido" value="" required><br>
            <input type="text" class="form-control" id="floatingInputValue" name="celular" placeholder="Ingrese su número celular" value="" maxlength="10" required pattern="[0-9]+" title="Solo se permiten números"><br>
            <input type="email" class="form-control" id="floatingInputValue" name="correo" placeholder="Ingrese su correo electrónico" value="" required><br>
            <input type="password" class="form-control" id="floatingPassword" name="contrasenia" placeholder="Ingrese su contraseña" required><br>
            <select name="rol" class="form-control" >
                <option value="1">Usuario</option>
                <option value="2" selected>Administrador</option>
            </select><br>
            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
  
                
                        

                </div>
            </div>
        </div>
    </main>
    <!-- partial -->

    <script>
        <%@include file= "scripts/script.js" %>
    </script>

    <%@include file= "templates/footer.jsp" %>
