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
            
            <%
            String res=request.getParameter("res");
            String busqueda=request.getParameter("busqueda");
            %>
            <div class="horizontal-tabs">         
            <a href="solicitudes.jsp?res=no"<%if(res!=null && res.equals("no") ){%> class="active" <%}%>>Todas las Solicitudes</a>
            <a href="solicitudes.jsp?res=Por responder" <%if(res!=null && res.equals("Por responder") ){%> class="active" <%}%>>Sin Respuesta</a>
        </div>
       
            
            <div class="content">
                <div class="content-panel">
                    <div class="vertical-tabs">
                        <a href="solicitudes.jsp?res=<%=res%>" <%if(request.getParameter("par")==null ){%> class="active" <%}%>>Todas</a>
                        <a href="solicitudes.jsp?par=Peticion&res=<%=res%>" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Peticion")){%> class="active" <%}%>>Peticiones</a>
                        <a href="solicitudes.jsp?par=Queja&res=<%=res%>" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Queja")){%> class="active" <%}%>>Quejas</a>
                        <a href="solicitudes.jsp?par=Reclamo&res=<%=res%>" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Reclamo")){%> class="active" <%}%>>Reclamos</a>
                        <a href="solicitudes.jsp?par=Sugerencia&res=<%=res%>" <%if(request.getParameter("par")!=null && request.getParameter("par").equals("Sugerencia")){%> class="active" <%}%>>Sugerencias </a>
                    </div>
                </div>
                <div class="content-main">
                    
                     <% 
  
                         for (Solicitudes sol : a) {

                             if (busqueda == null) {
                                 if (sol.getEstado().equals(res)) {
                                        
                         
                                     if (request.getParameter("par") == null) {
                                         %>
                                             <%=Metodos.listarAdministradores(sol)%>
                                         <%

                                     } else {
                                         if (request.getParameter("par").equals(sol.getTipoSolicitud())) {%>
                                             <%=Metodos.listarAdministradores(sol)%>
                                         <%}
                                     }
                                 } else {
                                     if (request.getParameter("par") == null) {

                                        %>
                                             <%=Metodos.listarAdministradores(sol)%>
                                         <%
                                     } else if (request.getParameter("par").equals(sol.getTipoSolicitud())) {
                                         %>
                                             <%=Metodos.listarAdministradores(sol)%>
                                         <%
                                     }
                                 }
                             } else if (busqueda.contains(sol.getNombreSol()) || busqueda.contains(sol.getDescripcion()) || busqueda.contains(sol.getUsuario())) {
                                 %>
                                             <%=Metodos.listarAdministradores(sol)%>
                                         <%

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
