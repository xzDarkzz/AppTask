package com.example.taskshmanager.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.taskshmanager.AppThemeColor // Asegúrate de que esta ruta coincida con donde declaraste AppThemeColor

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

)

@Composable
fun TaskShManagerTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    customAppTheme: AppThemeColor? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Si tenemos un tema personalizado (el que se lee de DataStore), lo usamos:
        customAppTheme != null -> {
            if (darkTheme) {
                darkColorScheme(
                    primary = customAppTheme.primary,
                    onPrimary = customAppTheme.onPrimary,
                    surface = customAppTheme.surface,
                    onSurface = customAppTheme.onSurface,
                    primaryContainer = customAppTheme.primary, // Aplica a la TopAppBar
                    onPrimaryContainer = customAppTheme.onPrimary
                )
            } else {
                lightColorScheme(
                    primary = customAppTheme.primary,
                    onPrimary = customAppTheme.onPrimary,
                    surface = customAppTheme.surface,
                    onSurface = customAppTheme.onSurface,
                    primaryContainer = customAppTheme.primary, // Aplica a la TopAppBar
                    onPrimaryContainer = customAppTheme.onPrimary
                )
            }
        }

        // Si no hay tema seleccionado, usamos el comportamiento por defecto de Material 3
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}