package com.yuriisurzhykov.translator.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yuriisurzhykov.translator.android.R
import com.yuriisurzhykov.translator.android.core.theme.AccentLight
import com.yuriisurzhykov.translator.android.core.theme.defaultPadding
import com.yuriisurzhykov.translator.language.presentation.UiLanguage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslateTextField(
    fromText: String,
    toText: String,
    isTranslating: Boolean,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onTextFieldClick)
            .padding(defaultPadding)
    ) {
        AnimatedContent(targetState = toText) { text ->
            if (text.isEmpty() || isTranslating) {
                IdleTranslateTextField(
                    fromText = fromText,
                    isTranslating = isTranslating,
                    onTextChange = onTextChange,
                    onTranslateClick = onTranslateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )
            } else {
                TranslatedTextField(
                    fromText = fromText,
                    toText = toText,
                    fromLanguage = fromLanguage,
                    toLanguage = toLanguage,
                    onCopyClick = onCopyClick,
                    onCloseClick = onCloseClick,
                    onSpeakerClick = onSpeakerClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TranslatedTextField(
    fromText: String,
    toText: String,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        LanguageDisplay(language = fromLanguage)
        Spacer(modifier = Modifier.height(defaultPadding))
        Text(text = fromText, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(defaultPadding))
        Row(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { onCopyClick(fromText) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy),
                    contentDescription = stringResource(id = R.string.button_copy_description),
                    tint = AccentLight
                )
            }
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.label_close),
                    tint = AccentLight
                )
            }
        }
        Spacer(modifier = Modifier.height(defaultPadding))
        Divider()
        Spacer(modifier = Modifier.height(defaultPadding))
        LanguageDisplay(language = toLanguage)
        Spacer(modifier = Modifier.height(defaultPadding))
        Text(text = toText, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(defaultPadding))
        Row(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { onCopyClick(toText) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy),
                    contentDescription = stringResource(id = R.string.button_copy_description),
                    tint = AccentLight
                )
            }
            IconButton(onClick = onSpeakerClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_speaker),
                    contentDescription = stringResource(id = R.string.label_play_translate),
                    tint = AccentLight
                )
            }
        }
    }

}

@Composable
private fun IdleTranslateTextField(
    fromText: String,
    isTranslating: Boolean,
    onTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isFocused by remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = fromText,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { isFocused = it.isFocused },
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
        )
        if (fromText.isEmpty() && !isFocused) {
            Text(
                text = stringResource(R.string.hist_enter_text_to_translate),
                color = AccentLight
            )
        }
        ProgressButton(
            text = stringResource(id = R.string.button_text_translate),
            isLoading = isTranslating,
            onClick = onTranslateClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}