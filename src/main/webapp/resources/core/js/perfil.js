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
            console.log("Actualización exitosa");
        },
        error: function () {
            alert('Error al obtener datos en ajax');
        }
    });

}