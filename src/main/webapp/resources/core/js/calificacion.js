$(document).ready(function () {

    // Al pasar el ratón por encima de una estrella, cambia el color de las estrellas hasta ese índice.
    $(".star").on("mouseover", function () {

        let index = $(this).data("index");
        $(".star").removeClass("star-hover");
        for (let i = 1; i <= index; i++) {
            $(".star[data-index=" + i + "]").addClass("star-hover");
        }
    });

    // Cuando se quita el ratón de las estrellas, restaura el color original.
    $(".rating").on("mouseout", function () {
        $(".star").removeClass("star-hover");
    });

    // Al hacer clic en una estrella, guarda la calificación y retiene el color.
    $(".star").on("click", function () {

        let index = $(this).data("index");
        $(".star").removeClass("star-selected");

        for (let i = 1; i <= index; i++) {
            $(".star[data-index=" + i + "]").addClass("star-selected");
        }
        // Actualiza el valor del campo oculto con la calificación seleccionada
        document.getElementById("valoracion").value = index;
    });
});