package com.example.taskshmanager

import androidx.compose.ui.graphics.Color

data class AppThemeColor(
    val id: String,
    val name: String,
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color
)

object ThemePalette {
    val options = listOf(
        // --- COLORES ORIGINALES ---
        AppThemeColor(
            id = "default",
            name = "Azul Clásico",
            primary = Color(0xFF2C5EE3),
            onPrimary = Color.White,
            surface = Color(0xFFF6F8FC),
            onSurface = Color(0xFF191C1E)
        ),
        AppThemeColor(
            id = "rose",
            name = "Rosa Neón",
            primary = Color(0xFFF04364),
            onPrimary = Color.White,
            surface = Color(0xFFFFF0F3),
            onSurface = Color(0xFF2B000A)
        ),
        AppThemeColor(
            id = "emerald",
            name = "Verde Esmeralda",
            primary = Color(0xFF0F9D58),
            onPrimary = Color.White,
            surface = Color(0xFFF0FDF4),
            onSurface = Color(0xFF052E16)
        ),
        AppThemeColor(
            id = "dark",
            name = "Oscuro Puro",
            primary = Color(0xFFBB86FC),
            onPrimary = Color.Black,
            surface = Color(0xFF121212),
            onSurface = Color(0xFFE1E1E1)
        ),

        // --- PALETAS DE COLOR HUNT (ANTERIORES) ---
        AppThemeColor(
            id = "plum_cream",
            name = "Ciruela Cálido",
            primary = Color(0xFF601D49),
            onPrimary = Color(0xFFFFEBB8),
            surface = Color(0xFFFFEBB8),
            onSurface = Color(0xFF601D49)
        ),
        AppThemeColor(
            id = "olive_beige",
            name = "Verde Olivo",
            primary = Color(0xFF8B9A6E),
            onPrimary = Color(0xFFEEEEEE),
            surface = Color(0xFFF7F2EB),
            onSurface = Color(0xFF3A422D)
        ),
        AppThemeColor(
            id = "deep_ocean",
            name = "Océano Profundo",
            primary = Color(0xFF112D4E),
            onPrimary = Color(0xFFF9F7F7),
            surface = Color(0xFFDBE2EF),
            onSurface = Color(0xFF112D4E)
        ),
        AppThemeColor(
            id = "slate_steel",
            name = "Gris Pizarra",
            primary = Color(0xFF27374D),
            onPrimary = Color(0xFFDDE6ED),
            surface = Color(0xFF9DB2BF),
            onSurface = Color(0xFF27374D)
        ),

        // --- NUEVAS PALETAS (LAVANDA Y PASTEL) ---
        AppThemeColor(
            id = "lavender_dream",
            name = "Sueño Lavanda",
            primary = Color(0xFFBE9FE1),     // Morado principal vibrante
            onPrimary = Color(0xFFFFFFFF),   // Blanco para texto sobre el morado
            surface = Color(0xFFF1F1F6),     // Fondo grisáceo muy claro
            onSurface = Color(0xFF4A3266)    // Morado oscuro para buena lectura en el fondo claro
        ),
        AppThemeColor(
            id = "cotton_candy",
            name = "Algodón de Azúcar",
            primary = Color(0xFFC6DCE4),     // Azul pastel inferior
            onPrimary = Color(0xFF333333),   // Gris oscuro porque el azul principal es muy claro
            surface = Color(0xFFFFE6E6),     // Rosa claro para el fondo
            onSurface = Color(0xFF594545)    // Tono oscuro cálido para leer sobre el rosa
        )
    )
}