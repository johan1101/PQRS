<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Solicitudes"%>
<%@page import="Clases.Metodos"%>
<%@page import="Clases.Metodos"%>
<%@page import="java.sql.Connection"%>
<%@page import="Clases.Conexion"%>
<%@include file= "templates/header.jsp" %>
<%
   
    ArrayList<Solicitudes> a=Metodos.getSolicitudes();
%>
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
                    <a href="solicitudes.jsp"> Solicitudes </a>
                    <a href="agregar.jsp"> Agregar </a>

                </nav>
                <div class="header-navigation-actions">
                    <a href="#" class="button">
                        <i class="ph-lightning-bold"></i>
                        <span>Upgrade now</span>
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
                <h1>Settings</h1>                
            </div>
            <div class="content">
                <div class="content-panel">
                    <div class="vertical-tabs">
                        <a href="solicitudes.jsp" <%if(request.getParameter("par")==null ){%> class="active" <%}%>>Todas</a>
                        <a href="solicitudes.jsp?par=Peticion" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Peticion")){%> class="active" <%}%>>Peticiones</a>
                        <a href="solicitudes.jsp?par=Queja" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Queja")){%> class="active" <%}%>>Quejas</a>
                        <a href="solicitudes.jsp?par=Reclamo" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Reclamo")){%> class="active" <%}%>>Reclamos</a>
                        <a href="solicitudes.jsp?par=Sugerencia" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Sugerencia")){%> class="active" <%}%>>Sugerencias </a>
                    </div>
                </div>
                <div class="content-main">
                     <% 
                      
                    for (Solicitudes sol: a) {
                       if(sol.getEstado().equals("Por responder")){
                        if(request.getParameter("par")==null){
                %>
                 
                <article class="card">
                            <div class="card-header">
                                <div>                                
                                    <h3>Solicitud #<%= sol.getIdSolicitud() %></h3>
                                </div>
                            </div>
                            <div class="card-body">
                                <h4>Nombre:  <%= sol.getNombreSol() %></h4>
                                <h4>Tipo Solicitud:  <%= sol.getTipoSolicitud() %></h4>
                                <h4>Fecha Registro:  <%= sol.getFechaRegistro() %></h4>
                                <h4>Estado:  <%= sol.getEstado() %></h4>
                                <h4>Descripcion:  <%= sol.getDescripcion() %></h4>
                                <h4>Pdf: <%= sol.getPdf() %></h4>
                                <h4>Usuario:  <%= sol.getUsuario() %></h4>
                                
                            </div>
                            <div class="card-footer">
                                <a href="#">Responder Solicitud</a>
                            </div>
                        </article>
<% 
        }else{
            if(request.getParameter("par").equals(sol.getTipoSolicitud())){
%>
            <article class="card">
                            <div class="card-header">
                                <div>                                
                                    <h3>Solicitud #<%= sol.getIdSolicitud() %></h3>
                                </div>
                            </div>
                            <div class="card-body">
                                <h4>Nombre:  <%= sol.getNombreSol() %></h4>
                                <h4>Tipo Solicitud:  <%= sol.getTipoSolicitud() %></h4>
                                <h4>Fecha Registro:  <%= sol.getFechaRegistro() %></h4>
                                <h4>Estado:  <%= sol.getEstado() %></h4>
                                <h4>Descripcion:  <%= sol.getDescripcion() %></h4>
                                <h4>Pdf: <%= sol.getPdf() %></h4>
                                <h4>Usuario:  <%= sol.getUsuario() %></h4>
                                
                            </div>
                            <div class="card-footer">
                                <a href="#">Responder Solicitud</a>
                            </div>
                        </article>
<%
            }
        } 
    }
}
%>
                        

                </div>
            </div>
        </div>
    </main>
    <!-- partial -->

    <script>
        <%@include file= "scripts/script.js" %>
    </script>

    <%@include file= "templates/footer.jsp" %>
