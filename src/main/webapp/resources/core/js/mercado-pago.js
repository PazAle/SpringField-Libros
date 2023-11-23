$(document).ready(function () {
    // Manejar clic en el botón "Pagar"
    $("#pagar").click(function () {
        // Obtener el precio total desde el elemento HTML
        var precioTotal = parseFloat($("#precioTotal").text().replace('$', '').trim());
        // Llamar a la función para generar la preferencia de pago
        generarPreferencia(precioTotal);
    });
});

function generarPreferencia(precioTotal) {
    $.ajax({
        method: "POST",
        url: "/spring/preferencia",
        data: {precioTotal: precioTotal},
        //contentType: "application/json",
        success: function (response) {
            const mp = new MercadoPago('TEST-f7137dd6-42b4-4fb4-84fb-e23adaf83adb', {
                locale: 'es-AR'
            });

            mp.bricks();

            mp.bricks().create("wallet", "wallet_container", {
                initialization: {
                    preferenceId: response,
                    redirectMode: "blank"
                },
            });
            setTimeout(function() {
                finalizarCompra();
            }, 15000);
        },
        error: function (xhr, status, error) {
            // Ocurrió un error al realizar la solicitud
            console.log(error);
        }
    });
}

function finalizarCompra(){
    generarCompra();
    vaciarCarrito();
    irAlHome();
}
function irAlHome(){
    window.location.href="/spring/home";
}
function generarCompra(){

}
function vaciarCarrito(){

}