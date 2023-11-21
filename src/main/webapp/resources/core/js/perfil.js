document.addEventListener("DOMContentLoaded", function () {
    // Obtener todas las opciones del panel izquierdo
    var opciones = document.querySelectorAll(".list-group-item-action");

    // Agregar evento de clic a cada opción
    opciones.forEach(function (opcion) {
        opcion.addEventListener("click", function () {
            // Eliminar la clase activa de todas las opciones
            opciones.forEach(function (o) {
                o.classList.remove("active");
            });

            // Agregar la clase activa a la opción clicada
            opcion.classList.add("active");
        });
    });
});

window.onload = function () {
    // Este bloque de código se ejecuta cuando la página completa se ha cargado
    ejecutarServicio("obtenerDatos");
};
function ejecutarServicio(metodo) {

    var idUsuario ="";

    idUsuario = obtenerIdUsuario();

    $.ajax({
        url: '/spring/'+ metodo, // Reemplaza con la ruta real de tu servicio
        method: 'POST',
        dataType: 'json',
        data: {idUsuario:idUsuario},
        success: function (response) {
            switch (metodo){
                case "obtenerDatos":
                    mostrarFormularioModificarPerfil(response);
                    break;
                case 'cambioClave':
                    mostrarFormularioCambioClave();
                    break;
                case 'cambiarEmail':
                    mostrarFormularioCambioEmail(response);
                    break;
                default:
                    mostrarFormularioModificarPerfil(response);
                    break;
            }
        },
        error: function () {
            alert('Error al obtener datos en ajax');
        }
    });
}

function obtenerIdUsuario(){
    return $("#idUsuario").val();
}
/*
function mostrarFormularioModificarPerfil(response) {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'actualizarPerfil'); return false;" >
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${response.nombre}">
            </div>
            <div class="form-group">
                <label for="apellido">Apellido:</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="${response.apellido}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${response.email}" readonly>
            </div>
            <hr>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;
    $('#panel-principal').html(formulario);
}

function mostrarFormularioCambioClave() {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'cambiarClave'); return false;">
            <div class="form-group">
                <label for="claveActual">Clave Actual:</label>
                <input type="password" class="form-control" id="claveActual" name="claveActual">
            </div>
            <div class="form-group">
                <label for="nuevaClave">Nueva Clave:</label>
                <input type="password" class="form-control" id="nuevaClave" name="nuevaClave">
            </div>
            <div class="form-group">
                <label for="confirmarClave">Confirmar Nueva Clave:</label>
                <input type="password" class="form-control" id="confirmarClave" name="confirmarClave">
            </div>
            <hr>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;

    $('#panel-principal').html(formulario);
}

function mostrarFormularioCambioEmail(response) {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'actualizarEmail'); return false;">
            <div class="form-group">
                <label for="nuevoEmail">Nuevo Email:</label>
                <input type="email" class="form-control" id="nuevoEmail" name="nuevoEmail" value="${response.email}">
            </div>
            <hr>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;

    $('#panel-principal').html(formulario);
}
*/

function mostrarFormularioModificarPerfil(response) {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'actualizarPerfil'); return false;">
            <h2>Modificar Perfil</h2>
            <hr>
            <div class="form-group row">
                <label for="nombre" class="col-sm-2 col-form-label">Nombre:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${response.nombre}">
                </div>
            </div>
            <div class="form-group row">
                <label for="apellido" class="col-sm-2 col-form-label">Apellido:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="apellido" name="apellido" value="${response.apellido}">
                </div>
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control non-editable" id="email" name="email" value="${response.email}" readonly>
                </div>
            </div>
            <hr>
            <div class="form-group text-right">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;
    $('#panel-principal').html(formulario);
}

<!-- Modificaciones en el formulario de cambio de clave -->
function mostrarFormularioCambioClave() {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'cambiarClave'); return false;">
            <h2>Cambio de Clave</h2>
            <hr>
            <div class="form-group row">
                <label for="claveActual" class="col-sm-3 col-form-label">Clave Actual:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="claveActual" name="claveActual">
                </div>
            </div>
            <div class="form-group row">
                <label for="nuevaClave" class="col-sm-3 col-form-label">Nueva Clave:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="nuevaClave" name="nuevaClave">
                </div>
            </div>
            <div class="form-group row">
                <label for="confirmarClave" class="col-sm-3 col-form-label">Confirmar Nueva Clave:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="confirmarClave" name="confirmarClave">
                </div>
            </div>
            <hr>
            <div class="form-group text-right">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;

    $('#panel-principal').html(formulario);
}

<!-- Modificaciones en el formulario de cambio de email -->
function mostrarFormularioCambioEmail(response) {
    var formulario = `
        <form onsubmit="realizarActualizacion(obtenerDatosFormulario(this), 'actualizarEmail'); return false;">
            <h2>Cambio de Email</h2>
            <hr>
            <div class="form-group row">
                <label for="nuevoEmail" class="col-sm-3 col-form-label">Nuevo Email:</label>
                <div class="col-sm-9">
                    <input type="email" class="form-control" id="nuevoEmail" name="nuevoEmail" value="${response.email}">
                </div>
            </div>
            <hr>
            <div class="form-group text-right">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </div>
        </form>
    `;

    $('#panel-principal').html(formulario);
}

function obtenerDatosFormulario(formulario) {
    var datos = {};

    $(formulario).serializeArray().forEach(function (item) {
        datos[item.name] = item.value;
    });
    return datos;
}

function realizarActualizacion(datos, metodo) {
    var idUsuario ="";

    idUsuario = obtenerIdUsuario();

    datos.idUsuario = idUsuario;

    $.ajax({
        url: '/spring/'+ metodo,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(datos),
        success: function (response) {
            if(response){
                console.log("Actualización exitosa");
                mostrarMensajeExito();
            }else{
                mostrarMensajeError();
            }
        },
        error: function () {
            alert('Error al obtener datos en ajax');
        }
    });
}

function mostrarMensajeExito() {
    var mensajeExito = `
        <div class="alert alert-success mt-3" role="alert">
            Cambios realizados con exito.
        </div>
    `;
    $('#panel-principal').append(mensajeExito);
}

function mostrarMensajeError() {
    var mensajeError = `
        <div class="alert alert-danger mt-3" role="alert">
            Los datos ingresados no son validos.
        </div>
    `;
    $('#panel-principal').append(mensajeError);
}