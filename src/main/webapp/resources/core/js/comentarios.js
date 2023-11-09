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
                //$("#mensajeComentario").html('<div class="alert alert-danger">Error: ' + errorThrown + '</div>');
                $("#mensajeComentario").html('<div class="alert alert-danger">Error al agregar el comentario</div>');
            }
        });
    });

    // Función para actualizar la lista de comentarios

});








