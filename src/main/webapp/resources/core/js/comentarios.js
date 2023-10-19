$(document).ready(function () {
    $("#comentarioForm").submit(function (event) {
        event.preventDefault();

        var comentario = $("#comentario").val();
        var libroId = $("#libroID").val();

        $.ajax({
            method: "POST",
            url: "/spring/agregar",
            data: { libroId: libroId, texto: comentario },
            success: function (response, status, xhr) {
                if (xhr.status === 200) {

                    // Si se pudo agregar el comentario, actualiza la lista de comentarios en la vista
                    if (response === "Comentario enviado exitosamente") {
                        // Limpia el campo de comentario
                        $("#comentario").val("");
                        // Agrega el nuevo comentario a la lista en la vista
                        var nuevoComentario = '<div class="card"><div class="card-body"><p class="card-text">' + comentario + '</p></div></div>';
                        $("#comentarios").append(nuevoComentario);
                        alert("Comentario agregado exitosamente");
                    }
                } else {
                    alert("Ocurrio un error al enviar el comentario.");
                }
            },
            error: function () {
                alert("Ocurrio un error al enviar el comentario.");
            }
        });
    });
});

