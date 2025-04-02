package com.mdubovikov.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorState() {
    Toast.makeText(
        LocalContext.current,
        stringResource(R.string.error),
        Toast.LENGTH_LONG
    ).show()
}