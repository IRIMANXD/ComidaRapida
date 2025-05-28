// Datos de ejemplo de restaurantes
let restaurants = [
    {
        id: 1,
        name: "Sacha braza",
        description: "Local especialiciado en pollos a la brazas, hamburguesas, alitas..",
        logo: "🍗",
        status: "Activo",
        dateJoined: "2025-05-12",
        location: "Tarapoto",
        branches: 1,
        plan: "Estándar"
    },
    {
        id: 2,
        name: "Pizzamia",
        description: "Pizzería con tradición italiana. Conocida por sus pizzas artesanales y ambiente acogedor.",
        logo: "🍕",
        status: "Activo",
        dateJoined: "2025-05-25",
        location: "Tarapoto",
        branches: 2,
        plan: "Estándar"
    },
    {
        id: 3,
        name: "Alifuego",
        description: "Comida rapida. Especialistas en alitas y broaster.",
        logo: "🌮",
        status: "Inactivo",
        dateJoined: "2025-01-03",
        location: "Morales",
        branches: 1,
        plan: "Premium"
    },
];

// Variables globales
let currentEditingId = null;
let filteredRestaurants = [...restaurants];

// Elementos del DOM
const sidebar = document.getElementById('sidebar');
const sidebarToggle = document.getElementById('sidebarToggle');
const mobileToggle = document.getElementById('mobileToggle');
const searchInput = document.getElementById('searchInput');
const restaurantsGrid = document.getElementById('restaurantsGrid');
const emptyState = document.getElementById('emptyState');
const addRestaurantBtn = document.getElementById('addRestaurantBtn');
const modalOverlay = document.getElementById('modalOverlay');
const modalClose = document.getElementById('modalClose');
const modalCancel = document.getElementById('modalCancel');
const modalSave = document.getElementById('modalSave');
const modalTitle = document.getElementById('modalTitle');
const restaurantForm = document.getElementById('restaurantForm');
const deleteModalOverlay = document.getElementById('deleteModalOverlay');
const deleteModalClose = document.getElementById('deleteModalClose');
const deleteCancel = document.getElementById('deleteCancel');
const deleteConfirm = document.getElementById('deleteConfirm');

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

sidebarToggle.addEventListener('click', toggleSidebar);
mobileToggle.addEventListener('click', toggleMobileSidebar);
searchInput.addEventListener('input', handleSearch);
addRestaurantBtn.addEventListener('click', openAddModal);
modalClose.addEventListener('click', closeModal);
modalCancel.addEventListener('click', closeModal);
modalSave.addEventListener('click', saveRestaurant);
deleteModalClose.addEventListener('click', closeDeleteModal);
deleteCancel.addEventListener('click', closeDeleteModal);
deleteConfirm.addEventListener('click', confirmDelete);

// Cerrar modales al hacer clic fuera
modalOverlay.addEventListener('click', function(e) {
    if (e.target === modalOverlay) {
        closeModal();
    }
});

deleteModalOverlay.addEventListener('click', function(e) {
    if (e.target === deleteModalOverlay) {
        closeDeleteModal();
    }
});

// Cerrar dropdowns al hacer clic fuera
document.addEventListener('click', function(e) {
    const dropdowns = document.querySelectorAll('.dropdown-menu.show');
    dropdowns.forEach(dropdown => {
        if (!dropdown.closest('.dropdown').contains(e.target)) {
            dropdown.classList.remove('show');
        }
    });
});

// Funciones principales
function initializeApp() {
    updateStats();
    renderRestaurants();
}

function toggleSidebar() {
    sidebar.classList.toggle('collapsed');
}

function toggleMobileSidebar() {
    sidebar.classList.toggle('show');
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    filteredRestaurants = restaurants.filter(restaurant => 
        restaurant.name.toLowerCase().includes(searchTerm) ||
        restaurant.location.toLowerCase().includes(searchTerm)
    );
    renderRestaurants();
}

function updateStats() {
    const totalRestaurants = restaurants.length;
    const activeRestaurants = restaurants.filter(r => r.status === 'Activo').length;
    const totalBranches = restaurants.reduce((sum, r) => sum + r.branches, 0);
    
    document.getElementById('totalRestaurants').textContent = totalRestaurants;
    document.getElementById('activeRestaurants').textContent = activeRestaurants;
    document.getElementById('totalBranches').textContent = totalBranches;
}

function renderRestaurants() {
    if (filteredRestaurants.length === 0) {
        restaurantsGrid.style.display = 'none';
        emptyState.style.display = 'block';
        
        const emptyMessage = document.getElementById('emptyMessage');
        if (searchInput.value.trim()) {
            emptyMessage.textContent = 'Intenta con otros términos de búsqueda';
        } else {
            emptyMessage.textContent = 'Comienza agregando tu primer restaurante';
        }
        return;
    }

    restaurantsGrid.style.display = 'grid';
    emptyState.style.display = 'none';
    
    restaurantsGrid.innerHTML = filteredRestaurants.map(restaurant => `
        <div class="restaurant-card">
            <div class="card-header">
                <div class="card-header-top">
                    <div class="restaurant-info">
                        <div class="restaurant-logo">
                            ${restaurant.logo}
                        </div>
                        <div class="restaurant-details">
                            <h3>${restaurant.name}</h3>
                            <div class="badges">
                                <span class="badge ${restaurant.status.toLowerCase()}">${restaurant.status}</span>
                                <span class="badge ${restaurant.plan.toLowerCase()}">${restaurant.plan}</span>
                            </div>
                        </div>
                    </div>
                    <div class="dropdown">
                        <button class="dropdown-toggle" onclick="toggleDropdown(${restaurant.id})">
                            <i class="fas fa-ellipsis-h"></i>
                        </button>
                        <div class="dropdown-menu" id="dropdown-${restaurant.id}">
                            <button class="dropdown-item" onclick="editRestaurant(${restaurant.id})">
                                <i class="fas fa-edit"></i>
                                Editar
                            </button>
                            <button class="dropdown-item danger" onclick="deleteRestaurant(${restaurant.id})">
                                <i class="fas fa-trash"></i>
                                Eliminar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-content">
                <p class="restaurant-description">${restaurant.description}</p>
                <div class="restaurant-meta">
                    <div class="meta-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>${restaurant.location}</span>
                    </div>
                    <div class="meta-item">
                        <i class="fas fa-building"></i>
                        <span>${restaurant.branches} sucursales</span>
                    </div>
                    <div class="meta-item">
                        <i class="fas fa-calendar"></i>
                        <span>Desde ${formatDate(restaurant.dateJoined)}</span>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <button class="btn btn-outline view-dashboard-btn" onclick="viewDashboard(${restaurant.id})">
                    <i class="fas fa-eye"></i>
                    Ver Dashboard
                    <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    `).join('');
}

function toggleDropdown(id) {
    const dropdown = document.getElementById(`dropdown-${id}`);
    const isShown = dropdown.classList.contains('show');
    
    // Cerrar todos los dropdowns
    document.querySelectorAll('.dropdown-menu.show').forEach(menu => {
        menu.classList.remove('show');
    });
    
    // Abrir el dropdown actual si no estaba abierto
    if (!isShown) {
        dropdown.classList.add('show');
    }
}

function openAddModal() {
    currentEditingId = null;
    modalTitle.textContent = 'Agregar Nuevo Restaurante';
    modalSave.textContent = 'Agregar Restaurante';
    resetForm();
    showModal();
}

function editRestaurant(id) {
    const restaurant = restaurants.find(r => r.id === id);
    if (!restaurant) return;
    
    currentEditingId = id;
    modalTitle.textContent = 'Editar Restaurante';
    modalSave.textContent = 'Guardar Cambios';
    
    // Llenar el formulario con los datos del restaurante
    document.getElementById('restaurantName').value = restaurant.name;
    document.getElementById('restaurantDescription').value = restaurant.description;
    document.getElementById('restaurantLocation').value = restaurant.location;
    document.getElementById('restaurantBranches').value = restaurant.branches;
    document.getElementById('restaurantPlan').value = restaurant.plan;
    
    showModal();
}

function deleteRestaurant(id) {
    currentEditingId = id;
    showDeleteModal();
}

function confirmDelete() {
    if (currentEditingId) {
        restaurants = restaurants.filter(r => r.id !== currentEditingId);
        filteredRestaurants = restaurants.filter(restaurant => 
            restaurant.name.toLowerCase().includes(searchInput.value.toLowerCase()) ||
            restaurant.location.toLowerCase().includes(searchInput.value.toLowerCase())
        );
        updateStats();
        renderRestaurants();
        closeDeleteModal();
    }
}

function saveRestaurant() {
    const form = restaurantForm;
    const formData = new FormData(form);
    
    const name = document.getElementById('restaurantName').value.trim();
    const description = document.getElementById('restaurantDescription').value.trim();
    const location = document.getElementById('restaurantLocation').value.trim();
    const branches = parseInt(document.getElementById('restaurantBranches').value);
    const plan = document.getElementById('restaurantPlan').value;
    
    // Validación básica
    if (!name || !location || !branches || !plan) {
        alert('Por favor, completa todos los campos requeridos.');
        return;
    }
    
    const restaurantData = {
        name,
        description,
        location,
        branches,
        plan,
        status: 'Activo',
        dateJoined: new Date().toISOString().split('T')[0],
        logo: getRandomEmoji()
    };
    
    if (currentEditingId) {
        // Editar restaurante existente
        const index = restaurants.findIndex(r => r.id === currentEditingId);
        if (index !== -1) {
            restaurants[index] = { ...restaurants[index], ...restaurantData };
        }
    } else {
        // Agregar nuevo restaurante
        const newId = Math.max(...restaurants.map(r => r.id)) + 1;
        restaurants.push({ id: newId, ...restaurantData });
    }
    
    // Actualizar filtros y vista
    filteredRestaurants = restaurants.filter(restaurant => 
        restaurant.name.toLowerCase().includes(searchInput.value.toLowerCase()) ||
        restaurant.location.toLowerCase().includes(searchInput.value.toLowerCase())
    );
    
    updateStats();
    renderRestaurants();
    closeModal();
}

function viewDashboard(id) {
    const restaurant = restaurants.find(r => r.id === id);
    if (restaurant) {
        alert(`Redirigiendo al dashboard de ${restaurant.name}...`);
        // Aquí podrías redirigir a la página específica del restaurante
        // window.location.href = `/restaurant/${id}/dashboard`;
    }
}

function showModal() {
    modalOverlay.classList.add('show');
    document.body.style.overflow = 'hidden';
}

function closeModal() {
    modalOverlay.classList.remove('show');
    document.body.style.overflow = '';
    resetForm();
}

function showDeleteModal() {
    deleteModalOverlay.classList.add('show');
    document.body.style.overflow = 'hidden';
}

function closeDeleteModal() {
    deleteModalOverlay.classList.remove('show');
    document.body.style.overflow = '';
    currentEditingId = null;
}

function resetForm() {
    restaurantForm.reset();
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}

function getRandomEmoji() {
    const emojis = ['🍔', '🍕', '🌮', '🍗', '🍣', '☕', '🥗', '🍜', '🍝', '🥙'];
    return emojis[Math.floor(Math.random() * emojis.length)];
}

// Manejo de teclas de escape
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        if (modalOverlay.classList.contains('show')) {
            closeModal();
        }
        if (deleteModalOverlay.classList.contains('show')) {
            closeDeleteModal();
        }
    }
});

// Responsive: cerrar sidebar en móvil al hacer clic fuera
document.addEventListener('click', function(e) {
    if (window.innerWidth <= 768) {
        if (!sidebar.contains(e.target) && !mobileToggle.contains(e.target)) {
            sidebar.classList.remove('show');
        }
    }
});