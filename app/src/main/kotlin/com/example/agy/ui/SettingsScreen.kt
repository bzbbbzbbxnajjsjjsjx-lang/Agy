package com.example.agy.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.example.agy.R
import com.example.agy.data.ThemeMode
import com.example.agy.ui.theme.AgyTheme
import com.example.agy.ui.theme.Dimens

/**
 * Ayarlar ekranı: Tema seçimi (Sistem / Açık / Koyu).
 * Seçim anında uygulanır ve SharedPreferences ile kalıcı hale getirilir.
 *
 * @param currentTheme Mevcut seçili tema modu.
 * @param onThemeSelected Tema tercihi değiştiğinde tetiklenen geri çağrı.
 * @param onNavigateBack Ana ekrana dönüş geri çağrısı.
 */
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // activity-compose BackHandler ile donanımsal/sistem geri tuşunu yakalar
    BackHandler(onBack = onNavigateBack)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spacingMedium)
    ) {
        // Üst Başlık ve Geri Butonu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onNavigateBack) {
                Text(
                    text = stringResource(R.string.back),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Tema Seçim Başlığı
        Text(
            text = stringResource(R.string.theme_selection_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = Dimens.spacingLarge, bottom = Dimens.spacingSmall)
        )

        // Tema Seçenekleri Listesi
        val themeOptions = listOf(
            ThemeMode.SYSTEM to R.string.theme_system,
            ThemeMode.LIGHT to R.string.theme_light,
            ThemeMode.DARK to R.string.theme_dark
        )

        themeOptions.forEach { (mode, labelRes) ->
            val isSelected = (currentTheme == mode)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = isSelected,
                        onClick = { onThemeSelected(mode) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = Dimens.spacingSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = null // Row tıklaması yönlendirir
                )
                Text(
                    text = stringResource(labelRes),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = Dimens.spacingSmall)
                )
            }
        }
    }
}

@Preview(name = "SettingsScreen Light", showBackground = true)
@Composable
private fun SettingsScreenPreviewLight() {
    AgyTheme(darkTheme = false) {
        Surface {
            SettingsScreen(
                currentTheme = ThemeMode.SYSTEM,
                onThemeSelected = {},
                onNavigateBack = {}
            )
        }
    }
}

@Preview(name = "SettingsScreen Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SettingsScreenPreviewDark() {
    AgyTheme(darkTheme = true) {
        Surface {
            SettingsScreen(
                currentTheme = ThemeMode.DARK,
                onThemeSelected = {},
                onNavigateBack = {}
            )
        }
    }
}
