package com.axiel7.moelist.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.data.repository.LoginRepository
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.login
import com.axiel7.moelist.ui.generated.resources.please_login_to_use_this_feature
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginView(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(UiRes.string.please_login_to_use_this_feature),
                modifier = Modifier.padding(8.dp),
            )

            Button(
                onClick = {
                    uriHandler.openUri(LoginRepository.loginUrl)
                },
                modifier = Modifier.padding(bottom = 24.dp),
                shapes = ButtonDefaults.shapes(),
            ) {
                Text(
                    text = stringResource(UiRes.string.login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MoeListTheme {
        LoginView()
    }
}