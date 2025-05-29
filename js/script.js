// Función para filtrar las novelas según la búsqueda
function filterNovels() {
    let input = document.getElementById('searchInput').value.toUpperCase();
    let novels = document.querySelectorAll('.novel-card');
    
    novels.forEach(function(novel) {
        let title = novel.getAttribute('data-title').toUpperCase();
        
        if (title.includes(input)) {
            novel.style.display = "";
        } else {
            novel.style.display = "none";
        }
    });
}


$(document).ready(function() {
    $('#toggleBtn').click(function() {
        $('#contenido').toggle();
        $(this).text(function(i, text) {
            return text === "Mostrar Contenido" ? "Ocultar Contenido" : "Mostrar Contenido";
        });
    });
});

//MOSTRAR 50 CAPITULOS
document.addEventListener('DOMContentLoaded', function() {
    const itemsPerPage = 50;
    const chapterBlocks = document.querySelectorAll('.chapter-block');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const pageInfo = document.getElementById('pageInfo');
            
    let currentPage = 1;
    const totalPages = Math.ceil(chapterBlocks.length / itemsPerPage);
            
    // Actualizar la información de la página
    function updatePageInfo() {
        pageInfo.textContent = `Página ${currentPage} de ${totalPages}`;
                
        // Deshabilitar botones según corresponda
        prevBtn.disabled = currentPage === 1;
        nextBtn.disabled = currentPage === totalPages;
    }
            
    // Mostrar los elementos de la página actual
    function showCurrentPage() {
        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
                
        chapterBlocks.forEach((block, index) => {
            if (index >= startIndex && index < endIndex) {
                block.style.display = 'block';
            } else {
                block.style.display = 'none';
            }
        });
                
        updatePageInfo();
    }
            
    // Event listeners para los botones
    prevBtn.addEventListener('click', function() {
        if (currentPage > 1) {
            currentPage--;
            showCurrentPage();
        }
    });
            
    nextBtn.addEventListener('click', function() {
        if (currentPage < totalPages) {
            currentPage++;
            showCurrentPage();
        }
    });
            
    // Inicializar
    showCurrentPage();
            
    // Ocultar paginación si no es necesaria
    if (chapterBlocks.length <= itemsPerPage) {
        document.querySelector('.pagination').style.display = 'none';
        document.querySelector('.page-info').style.display = 'none';
    }
});