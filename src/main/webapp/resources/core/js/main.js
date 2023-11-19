$(document).ready(function () {
    $(".agregarLibroBtn").click(function () {
        var idLibro = $(this).attr("data-idLibro");
        $.get("/agregarLibroACarrito/" + idLibro, function (data) {
            alert(data);
        });
    });
});
$.get("/agregarLibroACarrito/" + idLibro, function (data) {
    alert(data); // Muestra el mensaje de respuesta en un cuadro de diálogo
}).fail(function (jqXHR, textStatus, errorThrown) {
    console.error("Error en la solicitud: " + errorThrown);
});