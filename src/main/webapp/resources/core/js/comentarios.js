$(document).ready(function() {
    // Captura el clic en el botón "Enviar"
    $("#enviarComentario").click(function() {
        // Recopila el texto del comentario
        var texto = $("#comentario").val();
        var libroId = $("#libroID").val();

        // Realiza una solicitud AJAX para agregar el comentario
        $.ajax({
            type: "POST",
            url: "/spring/agregar", // La URL de tu controlador
            data: { libroId: libroId, texto: texto },
            success: function(response) {
                // Maneja la respuesta del servidor y muestra una alerta
                $("#mensajeComentario").html('<div class="alert alert-success">' + response + '</div>');
                // Limpia el campo del comentario
                $("#comentario").val('');
                setTimeout(function() {
                    window.location.reload();
                }, 2000);
            },
            error: function(xhr, textStatus, errorThrown) {
                // Maneja los errores y muestra una alerta de error
                $("#mensajeComentario").html('<div class="alert alert-danger">Error: ' + errorThrown + '</div>');
            }
        });
    });

    // Función para actualizar la lista de comentarios
    function actualizarComentarios() {
        console.log("Actualizando comentarios...");
        $.ajax({
            type: "GET", // O el método que uses para obtener la lista de comentarios
            url: "/spring/obtenerComentarios", // La URL para obtener los comentarios
            data: { libroId: $("#libroID").val() }, // Puedes pasar el libroId como parámetro si es necesario
            success: function(response) {
                // Actualiza la lista de comentarios en la vista
                $("#comentarios").html(response);
                console.log("Comentarios actualizados");
                // Procesa los comentarios y genera el HTML
                for (var i = 0; i < response.length; i++) {
                    var comentario = response[i];
                    var comentarioHTML = '<div class="card">';
                    comentarioHTML += '<div class="card-body">';
                    comentarioHTML += '<p class="card-text">' + comentario.texto + '</p>';
                    comentarioHTML += '</div>';
                    comentarioHTML += '</div>';
                    // Agrega el comentario al contenedor de comentarios
                    $("#comentarios").append(comentarioHTML);
                }
            },
            error: function(xhr, textStatus, errorThrown) {
                // Maneja los errores y muestra una alerta de error
                console.log("Error al actualizar comentarios: " + errorThrown);
            }
        });
    }
});








