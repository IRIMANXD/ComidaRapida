// Elementos del DOM
        const genresToggle = document.getElementById('genresToggle');
        const categoriesToggle = document.getElementById('categoriesToggle');
        const mainContent = document.getElementById('mainContent');
        const genresContent = document.getElementById('genresContent');
        const categoriesContent = document.getElementById('categoriesContent');
        
        // Función para alternar contenido
        function toggleContent(contentToToggle, button) {
            const isActive = button.classList.contains('active');
            
            // Ocultar todo primero
            mainContent.classList.remove('show');
            genresContent.classList.remove('show');
            genresToggle.classList.remove('active');
            categoriesContent.classList.remove('show');
            categoriesToggle.classList.remove('active');
            
            if (!isActive) {
                // Mostrar el contenido seleccionado
                setTimeout(() => {
                    mainContent.classList.add('show');
                    contentToToggle.classList.add('show');
                    button.classList.add('active');
                }, 10);
            }
        }
        
        // Event listeners
        genresToggle.addEventListener('click', () => toggleContent(genresContent, genresToggle));
        categoriesToggle.addEventListener('click', () => toggleContent(categoriesContent, categoriesToggle));

//CAMBIO DE CAPITULO
const chapters = [
  { number: 1, title: "CAPITULO 1" },
  { number: 2, title: "CAPITULO 2" }
];

// Seleccionar todos los bloques de paginación

document.querySelectorAll('.paginacion').forEach(function(paginacion) {
  const prevButton = paginacion.querySelector('.prev');
  const nextButton = paginacion.querySelector('.next');
  const chapterSelector = paginacion.querySelector('.chapter-selector1');

  // Llenar el selector de capítulos
  chapters.forEach(chapter => {
    const option = document.createElement('option');
    option.value = chapter.number;
    option.textContent = `Capítulo ${chapter.number} | ${chapter.title}`;
    chapterSelector.appendChild(option);
  });

  function loadChapter(chapterNumber) {
    if (chapterNumber >= 1 && chapterNumber <= chapters.length) {
      window.location.href = `capitulo${chapterNumber}.html`;
    }
  }

  prevButton.addEventListener('click', () => {
    const current = parseInt(chapterSelector.value);
    if (current > 1) loadChapter(current - 1);
  });

  nextButton.addEventListener('click', () => {
    const current = parseInt(chapterSelector.value);
    if (current < chapters.length) loadChapter(current + 1);
  });

  chapterSelector.addEventListener('change', () => {
    const selected = parseInt(chapterSelector.value);
    loadChapter(selected);
  });

  // Establecer el valor actual del selector según el nombre del archivo
  const currentChapterMatch = window.location.pathname.match(/capitulo(\d+)\.html$/);
  if (currentChapterMatch) {
    const currentChapter = parseInt(currentChapterMatch[1]);
    chapterSelector.value = currentChapter;
    prevButton.disabled = currentChapter <= 1;
    nextButton.disabled = currentChapter >= chapters.length || !chapters.some(c => c.number === currentChapter + 1);
  }
});

