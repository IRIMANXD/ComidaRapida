<?php
// Token de autorización
$token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbEBnbWFpbC5jb20iLCJ1c2VySWQiOjE1LCJlbXByZXNhSWQiOjEsInJvbGVzIjpbIlJPTEVfU1VQRVJBRE1JTklTVFJBRE9SIl0sImlzcyI6InNpc3RlbWEtdHVyaXN0aWNvLWJhY2tlbmQiLCJpYXQiOjE3NjQzMzk3MTYsImV4cCI6MTc2NDQyNjExNn0.H-geg1tf1JJI5i7aagghYZJ9NWtL7DQ2Cutz1uB3kqc';
$baseUrl = 'http://turistas.spring.informaticapp.com:2410/api/v1/clientes';

// Variables
$error = null;
$success = null;

// Procesar creación si se envió el formulario
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['crear'])) {
    // Validar DNI si se proporcionó
    $dni = $_POST['dni'] ?? '';
    if (!empty($dni)) {
        // Validar que el DNI tenga exactamente 8 dígitos
        $dni = preg_replace('/[^0-9]/', '', $dni); // Solo números
        if (strlen($dni) !== 8) {
            $error = "El DNI debe tener exactamente 8 dígitos";
        }
    }
    
    // Si no hay error, proceder con la creación
    if (!$error) {
        // Preparar datos para crear según la estructura de la API
        $datosCrear = [
            'empresa' => ['idEmpresa' => 1],
            'nombre' => $_POST['nombre'] ?? '',
            'apellido' => $_POST['apellido'] ?? '',
            'email' => $_POST['email'] ?? '',
            'telefono' => $_POST['telefono'] ?? '',
            'dni' => $dni,
            'fechaNacimiento' => $_POST['fechaNacimiento'] ?? '',
            'nacionalidad' => $_POST['nacionalidad'] ?? '',
            'preferenciasViaje' => $_POST['preferenciasViaje'] ?? ''
        ];
    
        // Realizar petición POST a la API
        $curl = curl_init();
        curl_setopt_array($curl, array(
            CURLOPT_URL => $baseUrl,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => 'POST',
            CURLOPT_POSTFIELDS => json_encode($datosCrear),
            CURLOPT_HTTPHEADER => array(
                'Authorization: ' . $token,
                'Content-Type: application/json'
            ),
        ));
        
        $response = curl_exec($curl);
        $httpCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
        $curlError = curl_error($curl);
        curl_close($curl);
        
        if ($curlError) {
            $error = "Error de conexión: " . $curlError;
        } elseif ($httpCode === 200 || $httpCode === 201) {
            // Redirigir a index.php con mensaje de éxito
            header('Location: index.php?success=1&mensaje=' . urlencode('Cliente creado correctamente'));
            exit();
        } else {
            $error = "Error al crear cliente: HTTP " . $httpCode;
            if ($response) {
                $errorData = json_decode($response, true);
                if (isset($errorData['message'])) {
                    $error .= " - " . $errorData['message'];
                } elseif (isset($errorData['error'])) {
                    $error .= " - " . $errorData['error'];
                }
            }
        }
    }
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Cliente - Sistema de Gestión</title>
    
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- CSS -->
    <link rel="stylesheet" href="web.css">
    <link rel="stylesheet" href="alertas.css">
</head>
<body class="admin-body">
    <!-- Sidebar -->
    <aside class="admin-sidebar" id="sidebar">
        <div class="sidebar-header">
            <h2 class="sidebar-title">Sistema</h2>
        </div>
        <nav class="sidebar-nav">
            <a href="index.php" class="sidebar-link">
                <i class="fas fa-users"></i>
                <span>Clientes</span>
            </a>
        </nav>
    </aside>

    <!-- Contenido Principal -->
    <main class="admin-main-content" id="mainContent">
        <!-- Header -->
        <header class="admin-header">
            <div class="header-left">
                <button class="sidebar-toggle" id="sidebarToggle">
                    <i class="fas fa-bars"></i>
                </button>
                <h1 class="page-title">Nuevo Cliente</h1>
            </div>
            <div class="header-right">
                <div class="user-profile">
                    <div class="profile-info">
                        <img src="https://via.placeholder.com/45" alt="Usuario" class="profile-image">
                        <div class="user-info">
                            <span class="user-name">Administrador</span>
                            <span class="user-role">Admin</span>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Contenido -->
        <div class="admin-content">
            <div class="content-header">
                <div class="card">
                    <div class="card-header">
                        <h2 class="section-title">Crear Nuevo Cliente</h2>
                        <div class="header-actions">
                            <a href="index.php" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i>
                                Volver
                            </a>
                        </div>
                    </div>
                    <div class="card-body">
                        <?php if ($error): ?>
                            <div style="padding: 15px; background: #f8d7da; color: #721c24; border-radius: 8px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
                                <i class="fas fa-exclamation-triangle"></i> <?php echo htmlspecialchars($error); ?>
                            </div>
                        <?php endif; ?>
                        
                        <form method="POST" action="" class="form-container">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label class="form-label" for="nombre">Nombre <span style="color: #dc3545;">*</span></label>
                                    <input 
                                        type="text" 
                                        id="nombre" 
                                        name="nombre" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['nombre'] ?? ''); ?>"
                                        required
                                        placeholder="Ingrese el nombre"
                                    >
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="apellido">Apellido</label>
                                    <input 
                                        type="text" 
                                        id="apellido" 
                                        name="apellido" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['apellido'] ?? ''); ?>"
                                        placeholder="Ingrese el apellido"
                                    >
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="email">Email <span style="color: #dc3545;">*</span></label>
                                    <input 
                                        type="email" 
                                        id="email" 
                                        name="email" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['email'] ?? ''); ?>"
                                        required
                                        placeholder="ejemplo@email.com"
                                    >
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="telefono">Teléfono</label>
                                    <input 
                                        type="tel" 
                                        id="telefono" 
                                        name="telefono" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['telefono'] ?? ''); ?>"
                                        placeholder="Ingrese el teléfono"
                                    >
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="dni">DNI</label>
                                    <input 
                                        type="text" 
                                        id="dni" 
                                        name="dni" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['dni'] ?? ''); ?>"
                                        maxlength="8"
                                        pattern="[0-9]{8}"
                                        placeholder="Ingrese 8 dígitos"
                                        title="El DNI debe tener exactamente 8 dígitos numéricos"
                                    >
                                    <small style="color: #6c757d; font-size: 0.85rem; margin-top: 5px; display: block;">
                                        Solo números, exactamente 8 dígitos
                                    </small>
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="fechaNacimiento">Fecha de Nacimiento</label>
                                    <input 
                                        type="date" 
                                        id="fechaNacimiento" 
                                        name="fechaNacimiento" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['fechaNacimiento'] ?? ''); ?>"
                                    >
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label" for="nacionalidad">Nacionalidad</label>
                                    <input 
                                        type="text" 
                                        id="nacionalidad" 
                                        name="nacionalidad" 
                                        class="form-input" 
                                        value="<?php echo htmlspecialchars($_POST['nacionalidad'] ?? ''); ?>"
                                        placeholder="Ej: Peruano"
                                    >
                                </div>
                                
                                <div class="form-group full-width">
                                    <label class="form-label" for="preferenciasViaje">Preferencias de Viaje</label>
                                    <textarea 
                                        id="preferenciasViaje" 
                                        name="preferenciasViaje" 
                                        class="form-input" 
                                        rows="3"
                                        placeholder="Ej: Aventura, cultura y gastronomía"
                                    ><?php echo htmlspecialchars($_POST['preferenciasViaje'] ?? ''); ?></textarea>
                                </div>
                            </div>
                            
                            <div class="form-actions">
                                <button type="submit" name="crear" class="btn btn-primary">
                                    <i class="fas fa-save"></i>
                                    Guardar Cliente
                                </button>
                                <a href="index.php" class="btn btn-secondary">
                                    <i class="fas fa-times"></i>
                                    Cancelar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Contenedor para alertas -->
    <div id="alertasContainer"></div>

    <!-- JavaScript -->
    <script>
        // Toggle sidebar en móviles
        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.getElementById('mainContent');

        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', function() {
                sidebar.classList.toggle('sidebar-collapsed');
                mainContent.classList.toggle('content-expanded');
            });
        }

        // Cerrar sidebar al hacer clic fuera en móviles
        document.addEventListener('click', function(event) {
            if (window.innerWidth <= 1024) {
                if (!sidebar.contains(event.target) && !sidebarToggle.contains(event.target)) {
                    sidebar.classList.remove('sidebar-collapsed');
                    mainContent.classList.remove('content-expanded');
                }
            }
        });

        // Función para mostrar alertas
        function mostrarAlerta(tipo, mensaje, duracion = 5000) {
            const alertasContainer = document.getElementById('alertasContainer');
            
            if (!alertasContainer) {
                const container = document.createElement('div');
                container.id = 'alertasContainer';
                document.body.appendChild(container);
            }
            
            const alerta = document.createElement('div');
            alerta.className = `alerta alerta-${tipo}`;
            
            const iconos = {
                success: '<i class="fas fa-check-circle"></i>',
                error: '<i class="fas fa-exclamation-circle"></i>',
                warning: '<i class="fas fa-exclamation-triangle"></i>',
                info: '<i class="fas fa-info-circle"></i>'
            };
            
            alerta.innerHTML = `
                <div style="display: flex; align-items: center; gap: 10px;">
                    ${iconos[tipo] || ''}
                    <span>${mensaje}</span>
                </div>
                <button class="cerrar-alerta" onclick="this.parentElement.remove()">
                    <i class="fas fa-times"></i>
                </button>
            `;
            
            alertasContainer.appendChild(alerta);
            
            setTimeout(() => {
                alerta.style.opacity = '1';
                alerta.style.transform = 'translateY(0)';
            }, 10);
            
            setTimeout(() => {
                alerta.style.opacity = '0';
                alerta.style.transform = 'translateY(-100%)';
                setTimeout(() => {
                    if (alerta.parentNode) {
                        alerta.remove();
                    }
                }, 300);
            }, duracion);
        }

        // Exportar función globalmente
        window.mostrarAlerta = mostrarAlerta;
        
        // Validación del DNI en tiempo real
        const dniInput = document.getElementById('dni');
        if (dniInput) {
            dniInput.addEventListener('input', function(e) {
                // Solo permitir números
                this.value = this.value.replace(/[^0-9]/g, '');
                
                // Limitar a 8 dígitos
                if (this.value.length > 8) {
                    this.value = this.value.slice(0, 8);
                }
            });
            
            dniInput.addEventListener('blur', function(e) {
                // Validar al salir del campo
                if (this.value.length > 0 && this.value.length !== 8) {
                    mostrarAlerta('warning', 'El DNI debe tener exactamente 8 dígitos');
                    this.focus();
                }
            });
        }
        
        // Validación del formulario antes de enviar
        const form = document.querySelector('form');
        if (form) {
            form.addEventListener('submit', function(e) {
                const dni = document.getElementById('dni').value;
                if (dni && dni.length !== 8) {
                    e.preventDefault();
                    mostrarAlerta('error', 'El DNI debe tener exactamente 8 dígitos');
                    document.getElementById('dni').focus();
                    return false;
                }
            });
        }
        
        // Mostrar alerta de error si existe
        <?php if ($error): ?>
            mostrarAlerta('error', '<?php echo addslashes($error); ?>');
        <?php endif; ?>
    </script>
</body>
</html>

