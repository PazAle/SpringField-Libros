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
    // Aquí puedes hacer una solicitud AJAX para obtener los datos del usuario del servicio
    // y luego llenar el formulario con los datos obtenidos.
    $.ajax({
        url: '/spring/'+ metodo, // Reemplaza con la ruta real de tu servicio
        method: 'POST',
        dataType: 'json',
        success: function (response) {
            switch (metodo){
                case "obtenerDatos":
                    modificarPerfil(response);
                    break;
                default:
                    modificarPerfil(response);
                    break;
            }
        },
        error: function () {
            alert('Error al obtener datos en ajax');
        }
    });
}


function modificarPerfil(response){
    var formulario = `
        <form>
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${response.nombre}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${response.email}">
            </div>
            <!-- Agrega más campos según sea necesario -->
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
        </form>
    `;
    // Muestra el formulario en el panel principal
    $('#panel-principal').html(formulario);
}