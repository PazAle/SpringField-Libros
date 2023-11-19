let selectedFilters = {};
let librosResultado =[];

document.querySelectorAll('.card').forEach(card => {
    let libro = {
        nombre: card.querySelector('.title').innerText,
        id: card.querySelector('.card-text.id').innerText,
        imagen: card.querySelector('.img-card').getAttribute('src'),
        autor: card.querySelector('.author').innerText,
        editorial: card.querySelector('.publisher').innerText,
        fechaLanzamiento: card.querySelector('.date').innerText,
        precio: parseFloat(card.querySelector('.price').innerText.split('$')[1]),
    };

    // Agrega el libro al array
    librosResultado.push(libro);
});

function populateFilters() {
    const dateFilters = document.getElementById('dateFilters');
    const editorialFilters = document.getElementById('editorialFilters');

    // Obtener fechas únicas
    const uniqueDates = [...new Set(librosResultado.map(libro => libro.fechaLanzamiento))];
    dateFilters.innerHTML = uniqueDates.map(date => `<li class="list-group-item"><a href="#" onclick="filterBy('fechaLanzamiento', '${date}')">${date}</a></li>`).join('');

    // Obtener editoriales únicas
    const uniqueEditorials = [...new Set(librosResultado.map(libro => libro.editorial))];
    editorialFilters.innerHTML = uniqueEditorials.map(editorial => `<li class="list-group-item"><a href="#" onclick="filterBy('editorial', '${editorial}')">${editorial}</a></li>`).join('');
}

function populateResults() {
    const resultsList = document.getElementById('resultsList');
    resultsList.innerHTML = '';

    for (const libro of librosResultado) {
        const listItem = document.createElement('div');
        listItem.classList.add('col-md-4', 'mb-4');
        listItem.innerHTML = `
        <div class="card h-100">
          <img src="${libro.imagen}" class="card-img-top img-card" style="height: 120px;">
          <div class="card-body">
            <h5 class="card-title title">${libro.nombre}</h5>
            <p class="card-text author">${libro.autor}</p>
            <p class="card-text publisher">${libro.editorial}</p>
            <p class="card-text date">${libro.fechaLanzamiento}</p>
            <p class="card-text price">Precio: $${libro.precio}</p>
            <a href="#" class="btn btn-primary btn-add-to-cart">A&ntilde;adir al carrito</a>
            <a href="/spring/detalle-libro?id=${libro.id}" class="btn btn-secondary btn-details ms-2">Ver detalle</a>
          </div>
        </div>`;
        resultsList.appendChild(listItem);
    }
}

function filterBy(filterType, filterValue) {
    selectedFilters[filterType] = filterValue;

    // Actualizar los filtros disponibles
    updateAvailableFilters();

    // Aplicar los filtros y mostrar los resultados
    applyFilters();
}

function removeFilter(filterType) {
    delete selectedFilters[filterType];

    // Actualizar los filtros disponibles
    updateAvailableFilters();

    // Aplicar los filtros y mostrar los resultados
    applyFilters();
}

function updateAvailableFilters() {
    const filteredLibros = applyFilters();

    const dateFilters = document.getElementById('dateFilters');
    const editorialFilters = document.getElementById('editorialFilters');

    // Obtener fechas únicas según los filtros aplicados
    const uniqueDates = [...new Set(filteredLibros.map(libro => libro.fechaLanzamiento))];
    dateFilters.innerHTML = uniqueDates.map(date => `<li class="list-group-item"><a href="#" onclick="filterBy('fechaLanzamiento', '${date}')">${date}</a></li>`).join('');

    // Obtener editoriales únicas según los filtros aplicados
    const uniqueEditorials = [...new Set(filteredLibros.map(libro => libro.editorial))];
    editorialFilters.innerHTML = uniqueEditorials.map(editorial => `<li class="list-group-item"><a href="#" onclick="filterBy('editorial', '${editorial}')">${editorial}</a></li>`).join('');

    // Mostrar los filtros seleccionados
    const selectedFiltersElement = document.getElementById('selectedFilters');
    selectedFiltersElement.innerHTML = Object.entries(selectedFilters).map(([key, value]) => {
        return `<span>${key}: ${value} <span class="close-icon" onclick="removeFilter('${key}')">&#10006;</span></span>`;
    }).join('');
}

function applyFilters() {
    let filteredLibros = [...librosResultado];

    for (const [filterType, filterValue] of Object.entries(selectedFilters)) {
        filteredLibros = filteredLibros.filter(libro => libro[filterType] === filterValue);
    }

    const resultsList = document.getElementById('resultsList');
    resultsList.innerHTML = '';

    for (const libro of filteredLibros) {
        const listItem = document.createElement('div');
        listItem.classList.add('col-md-4', 'mb-4');
        listItem.innerHTML = `
        <div class="card h-100">
          <img src="${libro.imagen}" class="card-img-top img-card" style="height: 120px;">
          <div class="card-body">
            <h5 class="card-title title">${libro.nombre}</h5>
            <p class="card-text author">${libro.autor}</p>
            <p class="card-text publisher">${libro.editorial}</p>
            <p class="card-text date">${libro.fechaLanzamiento}</p>
            <p class="card-text price">Precio: $${libro.precio}</p>
            <a href="#" class="btn btn-primary btn-add-to-cart">A&ntilde;adir al carrito</a>
            <a href="/spring/detalle-libro?id=${libro.id}" class="btn btn-secondary btn-details ms-2">Ver detalle</a>
          </div>
        </div>`;
        resultsList.appendChild(listItem);
    }

    return filteredLibros;
}

// Llama a las funciones para inicializar los filtros y los resultados
populateFilters();
//populateResults();
